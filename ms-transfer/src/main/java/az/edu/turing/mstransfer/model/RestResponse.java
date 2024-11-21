package az.edu.turing.mstransfer.model;

import lombok.Builder;

@Builder
public record RestResponse<T>(

        String status,
        T data) {
}
