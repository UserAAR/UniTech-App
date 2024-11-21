package az.edu.turing.mstransfer.service;

import az.edu.turing.mstransfer.auth.AuthorizationHelperService;
import az.edu.turing.mstransfer.dao.entity.AccountEntity;
import az.edu.turing.mstransfer.dao.reposiitory.AccountRepository;
import az.edu.turing.mstransfer.dao.reposiitory.BankTransferRepository;
import az.edu.turing.mstransfer.exceptions.BadRequestException;
import az.edu.turing.mstransfer.exceptions.NotFoundException;
import az.edu.turing.mstransfer.mapper.AccountMapper;
import az.edu.turing.mstransfer.model.enums.AccountStatus;
import az.edu.turing.mstransfer.model.request.CreateAccountRequest;
import az.edu.turing.mstransfer.model.response.RetrieveAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static az.edu.turing.mstransfer.model.enums.Error.*;

@Service
@RequiredArgsConstructor
public class AccountService{
    private final AccountRepository accountRepository;
    private final BankTransferRepository bankTransferRepository;
    private final AccountMapper accountMapper;
    private final AuthorizationHelperService authorizationHelperService;

    public void createAccount(String token, final CreateAccountRequest request) {
        authorizationHelperService.validateAccessToken(token);

        Long userId = authorizationHelperService.getUserId(token);
        boolean exists = accountRepository.existsByUserIdAndPassword(userId, request.password());

        if (exists) {
            throw new BadRequestException(ERR_08.getErrorCode(), ERR_08.getErrorDescription());
        }
        AccountEntity accountEntity = AccountEntity.builder()
                .bank(request.bank())
                .password(request.password())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .currency(request.currency())
                .userId(userId)
                .build();

        accountRepository.save(accountEntity);
    }

    @Transactional
    public void deleteAccount(String token, Long accountId) {
        authorizationHelperService.validateAccessToken(token);

        Long userId = authorizationHelperService.getUserId(token);
        AccountEntity accountEntity = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new NotFoundException(ERR_01.getErrorCode(), ERR_01.getErrorDescription()));
        accountRepository.delete(accountEntity);

        bankTransferRepository.deleteAllByAccountId(accountId);
    }

    @Transactional
    public void deleteAccountsById(String token) {
        authorizationHelperService.validateAccessToken(token);

        Long userId = authorizationHelperService.getUserId(token);
        List<AccountEntity> accountEntities = accountRepository.findByUserId(userId);

        if (accountEntities.isEmpty()) {
            throw new NotFoundException(ERR_01.getErrorCode(), ERR_01.getErrorDescription());
        }
        accountRepository.deleteAll(accountEntities);

        accountEntities.forEach(account ->
                bankTransferRepository.deleteAllByAccountId(account.getId())
        );
    }

    public List<RetrieveAccountResponse> getAccounts(String token) {
        authorizationHelperService.validateAccessToken(token);

        Long userId = authorizationHelperService.getUserId(token);
        return accountRepository.findByUserId(userId).stream().map(accountMapper::mapToDto).toList();
    }

    public RetrieveAccountResponse getAccount(String token, Long accountId) {
        authorizationHelperService.validateAccessToken(token);

        Long userId = authorizationHelperService.getUserId(token);
        AccountEntity accountEntity = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new NotFoundException(ERR_01.getErrorCode(), ERR_01.getErrorDescription()));
        return accountMapper.mapToDto(accountEntity);
    }

    public AccountEntity getActiveAccount(Long userId, Long accountId) {
        return accountRepository.findByUserIdAndIdAndStatus(userId, accountId, AccountStatus.ACTIVE)
                .orElseThrow(() -> new BadRequestException(ERR_04.getErrorCode(), ERR_04.getErrorDescription()));
    }

    public AccountEntity getAccountEntityByIban(String iban) {
        return accountRepository.findByIban(iban)
                .orElseThrow(() -> new NotFoundException(ERR_01.getErrorCode(), ERR_01.getErrorCode()));
    }

    public AccountEntity getAccountEntityByIbanAndAccountNumber(String iban, String accountNumber) {
        return accountRepository.findByIbanAndAccountNumber(iban, accountNumber)
                .orElseThrow(() -> new NotFoundException(ERR_01.getErrorCode(), ERR_01.getErrorCode()));
    }
}
