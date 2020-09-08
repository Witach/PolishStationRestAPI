package pl.polishstation.polishstationbackend.exception;

public class UserArleadyVerified extends RuntimeException {
    public static final String MESSAGE = "User was verified";

    public UserArleadyVerified() {
        super(MESSAGE);
    }
}
