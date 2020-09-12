package pl.polishstation.polishstationbackend.apiutils;

public interface StringifiedDomainMapper<Domain, DomainDTO> extends BasicDomainDTOMapper<Domain, DomainDTO> {
    String convertIntoString(Domain domain);
}
