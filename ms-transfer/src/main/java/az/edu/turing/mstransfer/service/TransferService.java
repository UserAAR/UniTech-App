package az.edu.turing.mstransfer.service;

import az.edu.turing.mstransfer.client.MsAuthClient;
import az.edu.turing.mstransfer.dao.entity.AccountEntity;
import az.edu.turing.mstransfer.dao.entity.TransactionEntity;
import az.edu.turing.mstransfer.dao.reposiitory.AccountRepository;
import az.edu.turing.mstransfer.dao.reposiitory.BankTransferRepository;
import az.edu.turing.mstransfer.exceptions.BadRequestException;
import az.edu.turing.mstransfer.model.enums.Currency;
import az.edu.turing.mstransfer.model.enums.Direction;
import az.edu.turing.mstransfer.model.request.BankTransferRequest;
import az.edu.turing.mstransfer.model.request.TopUpRequest;
import az.edu.turing.mstransfer.util.CurrencyRateFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static az.edu.turing.mstransfer.model.enums.AccountStatus.ACTIVE;
import static az.edu.turing.mstransfer.model.enums.Error.*;
import static az.edu.turing.mstransfer.util.TransactionMessageConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final BankTransferRepository bankTransferRepository;
    private final CurrencyRateFetcher fetcher;
    private final MsAuthClient authClient;

    @Transactional
    public void makeBankTransfer(String token, Long accountId, BankTransferRequest transferDto) {
        authClient.validateAccessToken(token);

        Long userId = authClient.getUserId(token);
        AccountEntity fromAccount = accountService.getActiveAccount(userId, accountId);
        if (fromAccount.getIban().equals(transferDto.toIban())) {
            throw new BadRequestException(ERR_05.getErrorCode(), ERR_05.getErrorDescription());
        }

        AccountEntity toAccount = accountService.getAccountEntityByIban(transferDto.toIban());
        log.info("status {}", toAccount.getStatus());
        if (!ACTIVE.equals(toAccount.getStatus())) {
            throw new BadRequestException(ERR_04.getErrorCode(), ERR_04.getErrorDescription());
        }

        BigDecimal commission = calculateCommission(fromAccount, toAccount, transferDto.amount());
        BigDecimal totalAmount = transferDto.amount().add(commission);


        if (bankTransferIsPossible(fromAccount.getBalance(), totalAmount)) {
            BigDecimal addedAmount = convert(transferDto.amount(), fromAccount.getCurrency(), toAccount.getCurrency());

            fromAccount.setBalance(fromAccount.getBalance().subtract(totalAmount));
            accountRepository.save(fromAccount);

            toAccount.setBalance(toAccount.getBalance().add(addedAmount));
            accountRepository.save(toAccount);

            saveTransaction(fromAccount, Direction.OUTGOING, transferDto.amount(), commission.toString(), getTransactionMessage(fromAccount, toAccount));
            saveTransaction(toAccount, Direction.INCOMING, transferDto.amount(), "UNKNOWN", getTransactionMessage(fromAccount, toAccount));
        }
    }

    private String getTransactionMessage(AccountEntity fromAccount, AccountEntity toAccount) {
        return fromAccount.getUserId().equals(toAccount.getUserId()) ? BETWEEN_MY_CARDS : ACCOUNT_TO_ANOTHER_ACCOUNT;
    }

    private BigDecimal calculateCommission(AccountEntity fromAccount, AccountEntity toAccount, BigDecimal amount) {
        return fromAccount.getBank().equals(toAccount.getBank()) ? BigDecimal.ZERO : amount.multiply(BigDecimal.valueOf(0.02));
    }

    private void saveTransaction(AccountEntity account, Direction direction, BigDecimal amount, String commission, String message) {
        TransactionEntity transaction = TransactionEntity.builder()
                .direction(direction)
                .comission(commission)
                .message(message)
                .amount(amount)
                .accountId(account.getId()).build();
        bankTransferRepository.save(transaction);
    }

    @Transactional
    public void topUp(String token, Long accountId, TopUpRequest request) {
        authClient.validateAccessToken(token);

        Long userId = authClient.getUserId(token);
        AccountEntity toAccount = accountService.getActiveAccount(userId, accountId);
        if (toAccount.getIban().equals(request.fromIban())) {
            throw new BadRequestException(ERR_05.getErrorCode(), ERR_05.getErrorDescription());
        }
        AccountEntity fromAccount = accountService.getAccountEntityByIbanAndAccountNumber(request.fromIban(), request.fromAccountNumber());

        log.info("status {}", fromAccount.getStatus());

        if (!ACTIVE.equals(fromAccount.getStatus())) {
            throw new BadRequestException(ERR_04.getErrorCode(), ERR_04.getErrorDescription());
        }

        BigDecimal commission = calculateCommission(fromAccount, toAccount, request.amount());
        BigDecimal totalAmount = request.amount().add(commission);


        if (bankTransferIsPossible(fromAccount.getBalance(), totalAmount)) {

            if (!fromAccount.getUserId().equals(userId)) {
                throw new BadRequestException(ERR_07.getErrorCode(), ERR_07.getErrorDescription());
            }

            BigDecimal addedAmount = convert(request.amount(), fromAccount.getCurrency(), toAccount.getCurrency());

            fromAccount.setBalance(fromAccount.getBalance().subtract(totalAmount));
            accountRepository.save(fromAccount);

            toAccount.setBalance(toAccount.getBalance().add(addedAmount));
            accountRepository.save(toAccount);

            saveTransaction(fromAccount, Direction.OUTGOING, request.amount(), commission.toString(), TOP_UP);
            saveTransaction(toAccount, Direction.INCOMING, request.amount(), "UNKNOWN", TOP_UP);
        }
    }

    public BigDecimal convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        try {
            fetcher.fetchExchangeRates();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch exchange rates", e);
        }

        if (fromCurrency == toCurrency) {
            return amount;
        }

        BigDecimal fromRate = fetcher.getRate(fromCurrency);
        BigDecimal toRate = fetcher.getRate(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new BadRequestException(ERR_08.getErrorCode(), ERR_08.getErrorDescription());
        }

        BigDecimal amountInBaseCurrency = amount.multiply(fromRate);

        return amountInBaseCurrency.divide(toRate, 10, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
    }


    private boolean bankTransferIsPossible(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new BadRequestException(ERR_06.getErrorCode(), ERR_06.getErrorDescription());
        }
        return true;
    }
}
