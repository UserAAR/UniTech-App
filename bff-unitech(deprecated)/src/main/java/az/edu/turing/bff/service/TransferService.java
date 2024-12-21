package az.edu.turing.bff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import az.edu.turing.bff.client.TransferClient;
import az.edu.turing.bff.model.dto.request.account.BankTransferRequest;
import az.edu.turing.bff.model.dto.request.account.TopUpRequest;
@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferClient transferClient;

    public void makeBankTransfer(String auth, Long accountId, BankTransferRequest request) {
        transferClient.makeBankTransfer(auth, accountId, request);
    }

    public void topUp(String auth, Long accountId, TopUpRequest request) {
        transferClient.topUp(auth, accountId, request);
    }
}
