package pl.polishstation.polishstationbackend.exception;

import java.time.LocalDateTime;

public class ApiErrorConverter {
    static public ApiError convertSimpleExceptionIntoApiError(SimpleException simpleException) {
        return ApiError.builder()
                .localDateTime(LocalDateTime.now())
                .message(simpleException.getMessage())
                .status(simpleException.getHttpStatus())
                .build();
    }
}
