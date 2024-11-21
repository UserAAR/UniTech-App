package az.edu.turing.bff.client;

import az.edu.turing.bff.config.FeignConfig;
import az.edu.turing.bff.model.dto.request.account.CreateAccountRequest;
import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.model.dto.response.account.RetrieveAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-client", url = "${account/transfer-service.url}", configuration = FeignConfig.class)
public interface AccountClient {

    @PostMapping()
    void createAccount(@RequestHeader("Authorization") String auth, @RequestBody CreateAccountRequest request);

    @DeleteMapping("/{id}")
    void deleteAccount(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id);

    @DeleteMapping()
    void deleteAllAccountsByUser(@RequestHeader("Authorization") String auth);

    @GetMapping()
    RestResponse<List<RetrieveAccountResponse>> getAccounts(@RequestHeader("Authorization") String auth);

    @GetMapping("/{id}")
    RestResponse<RetrieveAccountResponse> getAccount(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id);
}
