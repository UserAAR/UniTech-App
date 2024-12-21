package az.edu.turing.bff.model.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateUserRequest(

        @NotBlank
        String password) {
}
