package az.edu.turing.bff.client;

import az.edu.turing.bff.config.FeignConfig;
import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.model.dto.request.user.UpdateUserRequest;
import az.edu.turing.bff.model.dto.response.user.RetrieveUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-client", url = "${user-service.url}", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping()
    RestResponse<RetrieveUserResponse> getUserById(@RequestHeader("Authorization") String authHeader);

    @PutMapping
    RestResponse<RetrieveUserResponse> updateUserPassword(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateUserRequest updateUserRequest);

    @DeleteMapping()
    void deleteUser(@RequestHeader("Authorization") String token);
}
