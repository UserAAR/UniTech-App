package az.edu.turing.handler;

import az.edu.turing.exceptions.BadRequestException;
import az.edu.turing.exceptions.NotFoundException;
import az.edu.turing.model.dto.ErrorResponse;
import az.edu.turing.model.enums.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static az.edu.turing.model.enums.Error.ERR_08;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private static final String ERROR_LOG = "Error: ";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e,
                                                                   WebRequest request) {
        log.error(ERROR_LOG, e);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getCode(), e.getMessage(), getPath(request));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e,
                                                                   WebRequest request) {
        log.error(ERROR_LOG, e);

        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getCode(), e.getMessage(), getPath(request));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(MethodArgumentNotValidException e,
                                                                 WebRequest request) {
        log.error(ERROR_LOG, e);

        return buildErrorResponse(HttpStatus.BAD_REQUEST, ERR_08.getErrorCode(), e.getMessage(), getPath(request));
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status,
                                                             String errorCode,
                                                             String errorMessage,
                                                             String path) {
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .error(errorCode)
                        .errorDetail(errorMessage)
                        .path(path)
                        .status(status.value())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

}
