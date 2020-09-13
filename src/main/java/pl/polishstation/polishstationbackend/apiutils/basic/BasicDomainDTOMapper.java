package pl.polishstation.polishstationbackend.apiutils.basic;

public interface BasicDomainDTOMapper<Domain, DomainDTO> {
    DomainDTO convertIntoDTO(Domain domain);
    Domain convertIntoObject(DomainDTO domainDTO);
}
