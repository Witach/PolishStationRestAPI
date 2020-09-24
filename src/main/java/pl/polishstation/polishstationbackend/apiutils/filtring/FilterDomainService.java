package pl.polishstation.polishstationbackend.apiutils.filtring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.MultiValueMap;
import pl.polishstation.polishstationbackend.apiutils.paged.PagedDomainService;

public abstract class FilterDomainService<Domain, DomainDTO, DomainPostDTO> extends PagedDomainService<Domain, DomainDTO, DomainPostDTO> {

    @Autowired
    protected SpecificationFactory<Domain> specFactory;

    @Autowired
    protected JpaSpecificationExecutor<Domain> specificationExecutor;

    Page<DomainDTO> searchForEntity(Pageable pageable, MultiValueMap<String, String> filterParams) {
        var filterSpec = specFactory.specificationFrom(filterParams);
        return specificationExecutor.findAll(filterSpec, pageable).map(mapper::convertIntoDTO);
    }


}
