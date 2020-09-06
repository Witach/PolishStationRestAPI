package pl.polishstation.polishstationbackend.exception;

public class UniqueDataArleadyExists extends RuntimeException {
    private static final String TEMPLATE  = "%s with property %s = %s arleady exists";
    public UniqueDataArleadyExists(String entityName, String propertyName, String value) {
        super(String.format(TEMPLATE, entityName, propertyName, value));
    }
}
