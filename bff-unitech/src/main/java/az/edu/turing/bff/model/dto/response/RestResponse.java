package az.edu.turing.bff.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestResponse<T> {
    private String status;
    private T data;


    @Override
    public String toString() {
        return "RestResponse{" +
                "data=" + data +
                ", status='" + status + '\'' +
                '}';
    }
}