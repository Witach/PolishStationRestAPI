package pl.polishstation.polishstationbackend.apiutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StringifiedDomainService<Domain, DomainDTO> {

    @Autowired
    protected StringifiedDomainMapper<Domain, DomainDTO> mapper;

    @Autowired
    protected JpaRepository<Domain, Long> repository;

    public DomainDTO addEntity(DomainDTO dto) {
        var fuelType = mapper.convertIntoObject(dto);
        return mapper.convertIntoDTO(
                repository.save(fuelType)
        );
    }

    public abstract void deleteEntity(String name);

    public List<String> getAllEntities() {
        return repository.findAll().stream()
                .map(mapper::convertIntoString)
                .collect(Collectors.toList());
    }
}
