package pl.polishstation.polishstationbackend.domain.petrolstation.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum SpecFields {
    FUEL_TYPE(
            "fuelType",
            (resolver) ->
                    (root, criteriaQuery, criteriaBuilder) ->
                        root.join("fuelTypes", JoinType.LEFT)
                                .get("name")
                                .in(resolver.getFieldValue())
    ),
    AVG_OPINION(
            "avgOpinion",
            (resolver) ->
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("petrolStationStats").get("avgOpinion"), resolver.getIntFieldValue())
    );

    String fieldName;
    Function<SpecFieldResolver, Specification<PetrolStation>> fieldStrategy;

    SpecFields(String fieldName, Function<SpecFieldResolver, Specification<PetrolStation>> fieldStrategy) {
        this.fieldName = fieldName;
        this.fieldStrategy = fieldStrategy;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Function<SpecFieldResolver, Specification<PetrolStation>> getFieldStrategy() {
        return fieldStrategy;
    }

    public void setFieldStrategy(Function<SpecFieldResolver, Specification<PetrolStation>> fieldStrategy) {
        this.fieldStrategy = fieldStrategy;
    }

    public static SpecFields getSpecFieldOfParamName(String paramName) {
        return Arrays.stream(SpecFields.values())
                .filter(name -> name.toString().equals(paramName))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String toString() {
        return fieldName;
    }
}
