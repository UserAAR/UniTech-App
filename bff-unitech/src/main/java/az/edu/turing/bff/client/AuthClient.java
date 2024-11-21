package az.edu.turing.bff.client;

import az.edu.turing.bff.config.FeignConfig;
import az.edu.turing.bff.model.dto.response.auth.JwtResponseDto;
import az.edu.turing.bff.model.dto.request.auth.LoginRequestDto;
import az.edu.turing.bff.model.dto.request.auth.RegisterRequestDto;
import az.edu.turing.bff.model.dto.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-client", url = "${auth-service.url}", configuration = FeignConfig.class)
public interface AuthClient {

    @PostMapping("/register")
    Void register(@RequestBody RegisterRequestDto request);

    @PostMapping("/login")
    RestResponse<JwtResponseDto> login(@RequestBody LoginRequestDto request);

    @PostMapping("/logout")
    String logout(@RequestHeader("Authorization") String token);

    @PostMapping("/refresh")
    RestResponse<JwtResponseDto> refreshToken(@RequestHeader("Authorization") String refreshToken);
}
