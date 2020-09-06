package pl.polishstation.polishstationbackend.domain.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polishstation.polishstationbackend.exception.EntityDoesNotExists;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTO;
import pl.polishstation.polishstationbackend.domain.localization.dto.LocalizationDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizationService {

    @Autowired
    LocalizationDTOMapper mapper;

    @Autowired
    LocalizationRepository repository;

    public LocalizationDTO addEntity(LocalizationDTO dto) {
        var localization = mapper.convertIntoObject(dto);
        return mapper.convertIntoDTO(
                repository.save(localization)
        );
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExists();
        repository.deleteById(id);
    }

    public void updateEntity(LocalizationDTO dto, Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExists();
        var localization = mapper.convertIntoObject(dto);
        repository.save(localization);
    }

    public LocalizationDTO getEntityById(Long id) {
        return repository.findById(id)
                .map(mapper::convertIntoDTO)
                .orElseThrow(EntityDoesNotExists::new);
    }

    public List<LocalizationDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(mapper::convertIntoDTO)
                .collect(Collectors.toList());
    }
}
