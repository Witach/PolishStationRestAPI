package pl.polishstation.polishstationbackend.exception;

public class EntityDoesNotExists extends RuntimeException {
    public static final String MESSAGE = "Entity with this id does not exists";

    public EntityDoesNotExists() {
        super(MESSAGE);
    }
}
