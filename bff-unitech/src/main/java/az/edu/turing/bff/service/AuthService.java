package az.edu.turing.bff.service;

import az.edu.turing.bff.client.AuthClient;
import az.edu.turing.bff.model.dto.response.auth.JwtResponseDto;
import az.edu.turing.bff.model.dto.request.auth.LoginRequestDto;
import az.edu.turing.bff.model.dto.request.auth.RegisterRequestDto;
import az.edu.turing.bff.model.dto.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;

    public void register(RegisterRequestDto registerRequest) {
        authClient.register(registerRequest);
    }

    public RestResponse<JwtResponseDto> login(LoginRequestDto loginRequest) {
        return authClient.login(loginRequest);
    }

    public String logout(String token) {
        return authClient.logout(token);
    }

    public RestResponse<JwtResponseDto> refresh(String refreshToken) {
        return authClient.refreshToken(refreshToken);
    }
}
