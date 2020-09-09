package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

public class TokenExpired extends SimpleException {
    public static final String MESSAGE = "Token has expired. Resend token.";

    public TokenExpired() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
