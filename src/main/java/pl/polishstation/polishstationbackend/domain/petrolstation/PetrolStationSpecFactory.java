package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import pl.polishstation.polishstationbackend.apiutils.filtring.SpecificationFactory;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

@Component
public class PetrolStationSpecFactory implements SpecificationFactory<PetrolStation> {
    @Override
    public Specification<PetrolStation> specificationFrom(MultiValueMap<String, Object> filterParams) {
        return null;
    }
}
