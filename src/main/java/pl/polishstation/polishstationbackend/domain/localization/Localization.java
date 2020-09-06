package pl.polishstation.polishstationbackend.domain.localization;


import lombok.*;
import pl.polishstation.polishstationbackend.entity.BasicEntity;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.utils.CloneableEntity;

import javax.persistence.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Localization extends BasicEntity implements CloneableEntity<Localization> {

    @Column(length = 32)
    private String name;

    @Column(length = 32)
    private String street;

    @Column(length = 8)
    private String number;

    @Column(length = 6)
    private String postalCode;

    @Column(length = 8)
    private String lat;

    @Column(name = "long", length = 8)
    private String _long;

    @Column(length = 32)
    private String province;

    //revert

    @OneToOne(optional = false, mappedBy = "localization")
    private PetrolStation petrolStation;

    @Override
    public Localization cloneEntity() {
        return Localization.builder()
                ._long(_long)
                .lat(lat)
                .name(name)
                .number(number)
                .postalCode(postalCode)
                .province(province)
                .street(street)
                .build();
    }

    @Override
    public String toString() {
        return "Localization{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", lat='" + lat + '\'' +
                ", _long='" + _long + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
