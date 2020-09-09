package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

public class UserArleadyVerified extends SimpleException {
    public static final String MESSAGE = "User was verified";

    public UserArleadyVerified() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
