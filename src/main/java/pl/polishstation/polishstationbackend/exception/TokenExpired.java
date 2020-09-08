package pl.polishstation.polishstationbackend.exception;

public class TokenExpired extends RuntimeException {
    public static final String MESSAGE = "Token has expired. Resend token.";

    public TokenExpired() {
        super(MESSAGE);
    }
}
