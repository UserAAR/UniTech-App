package az.edu.turing.bff.controller;

import az.edu.turing.bff.model.dto.request.account.CreateAccountRequest;
import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.model.dto.response.account.RetrieveAccountResponse;
import az.edu.turing.bff.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bff/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService transferService;

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestHeader("Authorization") String auth, @RequestBody CreateAccountRequest request) {
        transferService.createAccount(auth, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@RequestHeader("Authorization") String auth, @PathVariable Long id) {
        transferService.deleteAccount(auth, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllAccounts(@RequestHeader("Authorization") String auth) {
        transferService.deleteAllAccounts(auth);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<RetrieveAccountResponse>>> getAccounts(@RequestHeader("Authorization") String auth) {
        RestResponse<List<RetrieveAccountResponse>> accounts = transferService.getAccounts(auth);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<RetrieveAccountResponse>> getAccount(@RequestHeader("Authorization") String auth, @PathVariable Long id) {
        RestResponse<RetrieveAccountResponse> account = transferService.getAccount(auth, id);
        return ResponseEntity.ok(account);
    }
}