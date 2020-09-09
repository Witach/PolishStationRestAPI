package pl.polishstation.polishstationbackend.exception;

import org.springframework.http.HttpStatus;

public class UniqueDataArleadyExists extends SimpleException {
    private static final String TEMPLATE  = "%s with property %s = %s arleady exists";
    public UniqueDataArleadyExists(String entityName, String propertyName, String value) {
        super(String.format(TEMPLATE, entityName, propertyName, value));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
