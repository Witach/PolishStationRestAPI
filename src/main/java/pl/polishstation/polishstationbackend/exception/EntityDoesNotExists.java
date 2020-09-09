package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

public class EntityDoesNotExists extends SimpleException {
    public static final String MESSAGE = "Entity with this id does not exists";

    public EntityDoesNotExists() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
