package pl.polishstation.polishstationbackend.domain.petrolstation.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import pl.polishstation.polishstationbackend.apiutils.filtring.SpecificationFactory;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;

import static pl.polishstation.polishstationbackend.domain.petrolstation.spec.SpecFields.*;

@Component
public class PetrolStationSpecFactory implements SpecificationFactory<PetrolStation> {

    @Override
    public Specification<PetrolStation> specificationFrom(MultiValueMap<String, String> filterParams) {
        var fields = Arrays.asList(SpecFields.values());
        Specification<PetrolStation> spec = Specification.where(null);
        for (String specField : filterParams.keySet()) {
            SpecFields paramName = getSpecFieldOfParamName(specField);
            var paramResolver = new SpecFieldResolver(filterParams, paramName);
            var paramSpec = paramName.getFieldStrategy().apply(paramResolver);
            spec = spec.and(paramSpec);
        }
        return spec;
    }


}
