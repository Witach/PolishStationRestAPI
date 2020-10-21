package pl.polishstation.polishstationbackend.domain.localization;


import lombok.*;
import pl.polishstation.polishstationbackend.entity.BasicEntity;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.utils.CloneableEntity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Localization extends BasicEntity implements CloneableEntity<Localization> {

    @Size(max= 64)
    @Column(length = 32)
    private String name;

    @Size(min = 3, max = 64)
    @Column(length = 32)
    private String street;

    @Size(max = 32)
    @Column(length = 32)
    private String number;

//    @Pattern(regexp = "^[0-9][0-9]-[0-9][0-9][0-9]$", flags = Pattern.Flag.UNICODE_CASE)
    @Column(length = 24)
    private String postalCode;

    @DecimalMin("-90")
    @DecimalMax("90")
    @Column(length = 8)
    private String lat;

    @DecimalMin("-180")
    @DecimalMax("180")
    @Column(name = "long", length = 8)
    private String _long;

    @Size(min = 3, max= 64)
    @Column(length = 32)
    private String province;

    //revert

    @NotNull
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
