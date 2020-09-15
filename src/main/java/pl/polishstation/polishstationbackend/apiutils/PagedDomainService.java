package pl.polishstation.polishstationbackend.apiutils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainService;

public abstract class PagedDomainService<Domain, DomainDTO, DomainPostDTO> extends BasicDomainService<Domain, DomainDTO, DomainPostDTO> {

    public Page<DomainDTO> getAllEntities(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::convertIntoDTO);
    }
}
