package web.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class UserObjectValidationException extends RuntimeException{

    private final List<ObjectError> errors;

    public UserObjectValidationException(List<ObjectError> errors) {
        super("Validation errors occurred");
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

}
