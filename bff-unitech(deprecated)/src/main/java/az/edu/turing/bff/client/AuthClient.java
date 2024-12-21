package az.edu.turing.bff.client;

import az.edu.turing.bff.config.FeignConfig;
import az.edu.turing.bff.model.dto.request.auth.LoginUserRequest;
import az.edu.turing.bff.model.dto.request.auth.RegisterUserRequest;
import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.model.dto.response.auth.JwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-client", url = "${auth-service.url}", configuration = FeignConfig.class)
public interface AuthClient {

    @PostMapping("/register")
    Void register(@RequestBody RegisterUserRequest request);

    @PostMapping("/login")
    RestResponse<JwtResponse> login(@RequestBody LoginUserRequest request);

    @PostMapping("/logout")
    String logout(@RequestHeader("Authorization") String token);

    @PostMapping("/refresh")
    RestResponse<JwtResponse> refreshToken(@RequestHeader("Authorization") String refreshToken);
}
