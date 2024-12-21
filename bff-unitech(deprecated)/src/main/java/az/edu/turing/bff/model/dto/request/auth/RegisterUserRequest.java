package az.edu.turing.bff.model.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(

        @NotBlank(message = "Name must not be empty")
        String name,

        @NotBlank(message = "Surname must not be empty")
        String surname,

        @Email(message = "Email is not valid")
        String email,

        @NotBlank
        String password){
}