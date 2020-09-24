package pl.polishstation.polishstationbackend.apiutils.filtring;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

public interface SpecificationFactory<Domain> {
    Specification<Domain> specificationFrom(MultiValueMap<String, String> filterParams);
}
