package az.edu.turing.bff.model.dto.response;

import lombok.Builder;

@Builder
public record RestResponse<T>(

        String status,
        T data) {
}
