package pl.polishstation.polishstationbackend.exception.controlleradvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.polishstation.polishstationbackend.exception.ApiErrorConverter;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.exception.SimpleException;

@RestControllerAdvice
public class EntityControllerAdvice {
    @ExceptionHandler(SimpleException.class)
    private ResponseEntity<?> handleEntityDoesNotExists(SimpleException e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiErrorConverter.convertSimpleExceptionIntoApiError(e));
    }
}
