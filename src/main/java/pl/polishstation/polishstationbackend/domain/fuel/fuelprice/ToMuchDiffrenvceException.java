package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

public class ToMuchDiffrenvceException extends RuntimeException {
    public ToMuchDiffrenvceException(String message) {
        super(message);
    }
}
