package az.edu.turing.bff.model.dto.request.auth;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String name;
    private String surname;
    private String password;
    private String email;
}
