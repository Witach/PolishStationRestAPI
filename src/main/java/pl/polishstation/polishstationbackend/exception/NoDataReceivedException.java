package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

public class NoDataReceivedException extends SimpleException {
    public static final String MESSAGE = "Data was not received";

    public NoDataReceivedException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
