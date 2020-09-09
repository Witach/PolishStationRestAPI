package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import lombok.*;
import pl.polishstation.polishstationbackend.entity.BasicEntity;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fuel_type")
@Entity
@Builder
public class FuelType extends BasicEntity {

    @Size(min = 1, max = 16)
    @Column(unique = true, nullable = false)
    private String name;

    //revert

    @ManyToMany(mappedBy = "fuelTypes")
    @Singular private List<PetrolStation> petrolStations = new LinkedList<>();

    @OneToMany(mappedBy = "fuelType")
    @Singular private List<FuelPrice> fuelPrices = new LinkedList<>();

    public FuelType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FuelType{" +
                "name='" + name + '\'' +
                '}';
    }
}
