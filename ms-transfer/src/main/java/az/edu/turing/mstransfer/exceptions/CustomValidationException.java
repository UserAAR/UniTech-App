package az.edu.turing.mstransfer.exceptions;

import lombok.Getter;

@Getter
public class CustomValidationException extends RuntimeException{
    private final String code;
    public CustomValidationException(String code, String message) {
        super(message);
        this.code = code;
    }
}
