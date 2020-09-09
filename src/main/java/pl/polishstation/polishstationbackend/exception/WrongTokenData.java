package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

public class WrongTokenData extends SimpleException {
    public static final String MESSEGE = "Wrong token data";
    public WrongTokenData() {
        super(MESSEGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
