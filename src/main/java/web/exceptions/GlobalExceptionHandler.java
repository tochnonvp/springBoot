package web.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlerUserNotFoundException(UserNotFoundException e){
        Map<String, String> map = new HashMap<>();
        map.put("message хз что за оно", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlerUserAlreadyExistsException(UserAlreadyExistsException e){
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    @ExceptionHandler(UserObjectValidationException.class)
    public ResponseEntity<Map<String, Object>> handlerUserObjectValidationException(UserObjectValidationException e){
        Map<String, Object> response = new HashMap<>();

        List<ObjectError> errors = e.getErrors();
        if (errors.size() == 1) {
            // Если одна ошибка, выведем дефолтное сообщение
            response.put("message", errors.get(0).getDefaultMessage());
        } else {
            // Если ошибок несколько, добавим их в список
            List<String> errorMessages = errors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            response.put("messages", errorMessages);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}