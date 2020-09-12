package pl.polishstation.polishstationbackend.apiutils;

public interface BasicDomainDTOMapper<Domain, DomainDTO> {
    DomainDTO convertIntoDTO(Domain domain);
    Domain convertIntoObject(DomainDTO domainDTO);
}
