package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

public class ApiErrorConverter {
    static public ApiError convertSimpleExceptionIntoApiError(SimpleException simpleException) {
        return ApiError.builder()
                .localDateTime(LocalDateTime.now())
                .message(simpleException.getMessage())
                .status(simpleException.getHttpStatus())
                .build();
    }

    static public ApiError convertConstraintViolationException(ConstraintViolationException constrainViolationException) {
        return ApiError.builder()
                .localDateTime(LocalDateTime.now())
                .message(constrainViolationException.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
