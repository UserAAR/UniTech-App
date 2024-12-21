package az.edu.turing.bff.service;

import az.edu.turing.bff.client.AuthClient;
import az.edu.turing.bff.model.dto.request.auth.LoginUserRequest;
import az.edu.turing.bff.model.dto.request.auth.RegisterUserRequest;
import az.edu.turing.bff.model.dto.response.auth.JwtResponse;
import az.edu.turing.bff.model.dto.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;

    public void register(RegisterUserRequest registerRequest) {
        authClient.register(registerRequest);
    }

    public RestResponse<JwtResponse> login(LoginUserRequest loginRequest) {
        return authClient.login(loginRequest);
    }

    public String logout(String token) {
        return authClient.logout(token);
    }

    public RestResponse<JwtResponse> refresh(String refreshToken) {
        return authClient.refreshToken(refreshToken);
    }
}
