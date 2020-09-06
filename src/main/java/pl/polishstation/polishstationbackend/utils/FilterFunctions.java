package pl.polishstation.polishstationbackend.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterFunctions {
    public static <E> List<E> filterAgainstNull(List<E> objectsWithPetrolStation, Function<E, Object> getter) {
        return objectsWithPetrolStation.stream()
                .filter(o -> Objects.nonNull(getter.apply(o)))
                .collect(Collectors.toList());
    }
}
