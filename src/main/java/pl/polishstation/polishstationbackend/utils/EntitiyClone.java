package pl.polishstation.polishstationbackend.utils;

import java.util.List;
import java.util.stream.Collectors;

public class EntitiyClone {
    public static <E extends CloneableEntity<E>> List<E> cloneableEntityList(List<E> cloneableEntities) {
        return cloneableEntities.stream()
                .map(CloneableEntity::cloneEntity)
                .collect(Collectors.toList());
    }
}
