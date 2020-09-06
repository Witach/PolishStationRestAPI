package pl.polishstation.polishstationbackend.exception;

import java.util.function.BiFunction;

public class ExcpetionFactory {
    public static <T> BiFunction<String, String, UniqueDataArleadyExists> uniqueDataExceptionOfClassResolver(Class<T> type) {
        return (property, value) -> new UniqueDataArleadyExists(type.getName(), property, value);
    }
}
