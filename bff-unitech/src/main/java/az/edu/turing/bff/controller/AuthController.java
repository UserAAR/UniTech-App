package az.edu.turing.bff.controller;

import az.edu.turing.bff.model.dto.response.auth.JwtResponseDto;
import az.edu.turing.bff.model.dto.request.auth.LoginRequestDto;
import az.edu.turing.bff.model.dto.request.auth.RegisterRequestDto;
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
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDto registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<JwtResponseDto>> login(@RequestBody LoginRequestDto loginRequest) {
        RestResponse<JwtResponseDto> jwtResponseDto = authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String message = authService.logout(token);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RestResponse<JwtResponseDto>> refresh(@RequestHeader("Authorization") String refreshToken) {
        RestResponse<JwtResponseDto> jwtResponseDtoRestResponse = authService.refresh(refreshToken);
        return ResponseEntity.ok(jwtResponseDtoRestResponse);
    }
}
