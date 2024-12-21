package az.edu.turing.bff.model.dto.response.auth;

import lombok.Builder;

@Builder
public record JwtResponse(
        String accessToken,
        String refreshToken) {
}
