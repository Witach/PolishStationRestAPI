package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

public abstract class SimpleException extends RuntimeException {
    public SimpleException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
