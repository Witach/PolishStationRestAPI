package pl.polishstation.polishstationbackend.exception.controlleradvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;

@RestControllerAdvice
public class EntityControllerAdvice {
    @ExceptionHandler(EntityDoesNotExists.class)
    private ResponseEntity<?> handleEntityDoesNotExists(EntityDoesNotExists e){
        return ResponseEntity.notFound().build();
    }
}
