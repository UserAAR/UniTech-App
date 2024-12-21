package az.edu.turing.bff.exceptions;

public class NotFoundException extends CustomValidationException{
    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
