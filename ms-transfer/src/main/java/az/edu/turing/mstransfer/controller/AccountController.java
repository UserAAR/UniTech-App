package az.edu.turing.mstransfer.controller;

import az.edu.turing.mstransfer.auth.AuthorizationHelperService;
import az.edu.turing.mstransfer.model.RestResponse;
import az.edu.turing.mstransfer.model.request.CreateAccountRequest;
import az.edu.turing.mstransfer.model.response.RetrieveAccountResponse;
import az.edu.turing.mstransfer.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Account Controller API", description = "account controller")
public class AccountController {

    private final AccountService accountService;
    private final AuthorizationHelperService authorizationHelperService;

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestHeader("Authorization") String auth, @RequestBody @Valid CreateAccountRequest request) {
        String token = authorizationHelperService.extractToken(auth);
        accountService.createAccount(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@RequestHeader("Authorization") String auth, @PathVariable Long id) {
        String token = authorizationHelperService.extractToken(auth);
        accountService.deleteAccount(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllAccountsByUser(@RequestHeader("Authorization") String auth) {
        String token = authorizationHelperService.extractToken(auth);
        accountService.deleteAccountsById(token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<RetrieveAccountResponse>>> getAccounts(@RequestHeader("Authorization") String auth) {
        String token = authorizationHelperService.extractToken(auth);
        List<RetrieveAccountResponse> accounts = accountService.getAccounts(token);

        RestResponse<List<RetrieveAccountResponse>> restResponse = RestResponse.<List<RetrieveAccountResponse>>builder()
                .data(accounts)
                .status("SUCCESS")
                .build();

        return ResponseEntity.ok(restResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<RetrieveAccountResponse>> getAccount(@RequestHeader("Authorization") String auth, @PathVariable Long id) {
        String token = authorizationHelperService.extractToken(auth);
        RetrieveAccountResponse account = accountService.getAccount(token, id);

        RestResponse<RetrieveAccountResponse> restResponse = RestResponse.<RetrieveAccountResponse>builder()
                .data(account)
                .status("SUCCESS")
                .build();

        return ResponseEntity.ok(restResponse);
    }
}
