package az.edu.turing.bff.service;

import az.edu.turing.bff.client.AccountClient;
import az.edu.turing.bff.model.dto.request.account.CreateAccountRequest;
import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.model.dto.response.account.RetrieveAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountClient transferClient;

    public void createAccount(String auth, CreateAccountRequest request) {
        transferClient.createAccount(auth, request);
    }

    public void deleteAccount(String auth, Long id) {
        transferClient.deleteAccount(auth, id);
    }

    public void deleteAllAccounts(String auth) {
        transferClient.deleteAllAccountsByUser(auth);
    }

    public RestResponse<List<RetrieveAccountResponse>> getAccounts(String auth) {
        return transferClient.getAccounts(auth);
    }

    public RestResponse<RetrieveAccountResponse> getAccount(String auth, Long id) {
        return transferClient.getAccount(auth, id);
    }
}
