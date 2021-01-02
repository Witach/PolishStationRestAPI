package pl.polishstation.polishstationbackend.apiutils.basic;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin
public abstract class BasicDomainController<Domain, DomainDTO, DomainPostDTO> {

    @Autowired
    protected BasicDomainService<Domain, DomainDTO, DomainPostDTO> service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public DomainDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<DomainDTO> get() {
        return service.getAllEntities();
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
