package az.edu.turing.bff.model.dto.request.auth;

import lombok.Builder;


@Builder
public record LoginRequestDto(String email, String password) {
}
