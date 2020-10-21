package pl.polishstation.polishstationbackend.domain.petrolstation.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.localization.Localization;
import pl.polishstation.polishstationbackend.domain.opinion.Opinion;
import pl.polishstation.polishstationbackend.entity.BasicEntity;
import pl.polishstation.polishstationbackend.utils.CloneableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;


@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class PetrolStation extends BasicEntity implements CloneableEntity<PetrolStation> {

    @NotBlank
    @Size(max = 32)
    @Column(nullable = false, length = 128)
    private String name;

    private Long dkn;

    @NotNull
    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Localization localization;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "avgOpinion", column = @Column(name = "avg_opinion_rate")),
            @AttributeOverride( name = "amountOfOpinion", column = @Column(name = "amount_of_opinion"))
    })
    PetrolStationStats petrolStationStats;

    //revert

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "petrolStation", cascade = {CascadeType.REMOVE})
    private List<FuelPrice> fuelPrice = new LinkedList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "fuel_type_petrol_station",
            joinColumns = { @JoinColumn(name = "fuel_type_id") },
            inverseJoinColumns = { @JoinColumn(name = "petrol_station_id")}
    )
    private List<FuelType> fuelTypes = new LinkedList<>();

    @OneToMany(mappedBy = "petrolStation", cascade = {CascadeType.REMOVE})
    private List<Opinion> opinions = new LinkedList<>();

    @Override
    public PetrolStation cloneEntity() {
        return PetrolStation.builder()
                .name(name)
                .dkn(dkn)
                .petrolStationStats(petrolStationStats.cloneEntity())
                .build();
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
        localization.setPetrolStation(this);
    }

    public void addFuelType(FuelType fuelType) {
        fuelTypes.add(fuelType);
    }

    @Override
    public String toString() {
        return "PetrolStation{" +
                "name='" + name + '\'' +
                ", dkn=" + dkn +
                ", localization=" + localization +
                ", petrolStationStats=" + petrolStationStats +
                ", fuelTypes=" + fuelTypes +
                '}';
    }
}
