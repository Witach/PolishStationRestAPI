package pl.polishstation.polishstationbackend.apiutils;

public interface BasicDomainPostDTOMapper<Domain, DomainPostDTO> {
    Domain convertIntoObject(DomainPostDTO domainPostDTO);
}
