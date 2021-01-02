package pl.polishstation.polishstationbackend.apiutils.filtring;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.polishstation.polishstationbackend.apiutils.paged.PagedDomainService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;
@CrossOrigin
public abstract class FilterDomainController<Domain, DomainDTO, DomainPostDTO> {
    @Autowired
    FilterDomainService<Domain, DomainDTO, DomainPostDTO> service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public DomainDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public Page<DomainDTO> get(Pageable pageable, MultiValueMap<String, String> filtringParams) {
        return service.searchForEntity(pageable, filtringParams);
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
    public DomainDTO post(@Valid @RequestBody DomainPostDTO dto) throws FirebaseMessagingException {
        return service.addEntity(dto);
    }
}
