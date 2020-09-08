package pl.polishstation.polishstationbackend.exception;

public class WrongTokenData extends RuntimeException {
    public static final String MESSEGE = "Wrong token data";
    public WrongTokenData() {
        super(MESSEGE);
    }
}
