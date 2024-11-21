package az.edu.turing.bff.config;

import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ErrorDecoder implements feign.codec.ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.resolve(response.status());
        String errorMessage = "An unexpected error occurred";

        if (status != null) {
            errorMessage = switch (status) {
                case NOT_FOUND -> "The requested resource was not found.";
                case BAD_REQUEST -> "Invalid request sent to the server.";
                case UNAUTHORIZED -> "You are not authorized to access this resource.";
                case FORBIDDEN -> "Access to the requested resource is forbidden.";
                case INTERNAL_SERVER_ERROR -> "Server encountered an internal error.";
                default -> "Error: " + status.getReasonPhrase();
            };
        }
        return new ResponseStatusException(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
}
