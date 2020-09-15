package pl.polishstation.polishstationbackend.apiutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

public abstract class PagedDomainController<Domain, DomainDTO, DomainPostDTO> {
    @Autowired
    PagedDomainService<Domain, DomainDTO, DomainPostDTO> service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public DomainDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public Page<DomainDTO> get(Pageable pageable) {
        return service.getAllEntities(pageable);
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@Valid @RequestBody DomainPostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public DomainDTO post(@Valid @RequestBody DomainPostDTO dto) {
        return service.addEntity(dto);
    }
}
