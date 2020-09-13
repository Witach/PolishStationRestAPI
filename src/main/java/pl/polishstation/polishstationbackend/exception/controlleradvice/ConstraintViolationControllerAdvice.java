package pl.polishstation.polishstationbackend.exception.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.polishstation.polishstationbackend.exception.ApiErrorConverter;
import pl.polishstation.polishstationbackend.exception.SimpleException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ConstraintViolationControllerAdvice {
    @ExceptionHandler(SimpleException.class)
    private ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorConverter.convertConstraintViolationException(e));
    }
}
