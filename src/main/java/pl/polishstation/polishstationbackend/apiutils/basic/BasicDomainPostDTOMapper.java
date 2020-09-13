package pl.polishstation.polishstationbackend.apiutils.basic;

public interface BasicDomainPostDTOMapper<Domain, DomainPostDTO> {
    Domain convertIntoObject(DomainPostDTO domainPostDTO);
}
