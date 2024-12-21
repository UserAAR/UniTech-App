package az.edu.turing.bff.controller;

import az.edu.turing.bff.model.dto.request.auth.LoginUserRequest;
import az.edu.turing.bff.model.dto.request.auth.RegisterUserRequest;
import az.edu.turing.bff.model.dto.response.auth.JwtResponse;
import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/bff/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<JwtResponse>> login(@RequestBody LoginUserRequest loginRequest) {
        RestResponse<JwtResponse> jwtResponseDto = authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String message = authService.logout(token);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RestResponse<JwtResponse>> refresh(@RequestHeader("Authorization") String refreshToken) {
        RestResponse<JwtResponse> jwtResponseDtoRestResponse = authService.refresh(refreshToken);
        return ResponseEntity.ok(jwtResponseDtoRestResponse);
    }
}
