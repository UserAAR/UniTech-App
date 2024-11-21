package az.edu.turing.bff.model.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RetrieveUserResponse(
        Long id,
        String name,
        String surname,
        String email,
        String password,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {
}