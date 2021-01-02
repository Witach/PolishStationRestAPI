package pl.polishstation.polishstationbackend.apiutils.basic;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BasicDomainService<Domain, DomainDTO, DomainPostDTO> {

    @Autowired
    protected BasicDomainDTOMapper<Domain, DomainDTO> mapper;

    @Autowired
    protected BasicDomainPostDTOMapper<Domain, DomainPostDTO> postDTOMapper;

    @Autowired
    protected JpaRepository<Domain, Long> repository;

    public DomainDTO addEntity(DomainPostDTO dto) throws FirebaseMessagingException {
        var newEntity = postDTOMapper.convertIntoObject(dto);
        return mapper.convertIntoDTO(
                repository.save(newEntity)
        );
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExists();
        repository.deleteById(id);
    }

    public void updateEntity(DomainPostDTO dto, Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExists();
        var entity = postDTOMapper.convertIntoObject(dto);
        repository.save(entity);
    }

    public DomainDTO getEntityById(Long id) {
        return repository.findById(id)
                .map(mapper::convertIntoDTO)
                .orElseThrow(EntityDoesNotExists::new);
    }

    public List<DomainDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(mapper::convertIntoDTO)
                .collect(Collectors.toList());
    }
}
