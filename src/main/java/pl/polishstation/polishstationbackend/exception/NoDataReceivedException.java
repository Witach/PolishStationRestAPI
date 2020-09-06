package pl.polishstation.polishstationbackend.exception;

public class NoDataReceivedException extends RuntimeException {
    public static final String MESSAGE = "Data was not received";

    public NoDataReceivedException() {
        super(MESSAGE);
    }
}
