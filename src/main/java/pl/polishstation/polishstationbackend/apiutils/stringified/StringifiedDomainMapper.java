package pl.polishstation.polishstationbackend.apiutils.stringified;

import pl.polishstation.polishstationbackend.apiutils.basic.BasicDomainDTOMapper;

public interface StringifiedDomainMapper<Domain, DomainDTO> extends BasicDomainDTOMapper<Domain, DomainDTO> {
    String convertIntoString(Domain domain);
}
