package pl.polishstation.polishstationbackend.exception.controlleradvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.polishstation.polishstationbackend.aspect.Loggable;
import pl.polishstation.polishstationbackend.exception.ApiError;
import pl.polishstation.polishstationbackend.exception.ApiErrorConverter;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.exception.SimpleException;
@Loggable
@RestControllerAdvice
public class EntityControllerAdvice {
    @ExceptionHandler(SimpleException.class)
    private ResponseEntity<ApiError> handleEntityDoesNotExists(SimpleException e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiErrorConverter.convertSimpleExceptionIntoApiError(e));
    }
}
