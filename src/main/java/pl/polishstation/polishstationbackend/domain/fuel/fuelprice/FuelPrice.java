package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import lombok.*;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.validation.Precision;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.validation.Verified;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.entity.BasicEntity;
import pl.polishstation.polishstationbackend.utils.CloneableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "fuel_price")
@Entity
public class FuelPrice extends BasicEntity implements CloneableEntity<FuelPrice> {

    @Precision()
    @Column(precision = 2, scale = 2, nullable = false)
    private Double price;

    @PastOrPresent
    private LocalDateTime date;

    @Verified
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private AppUser user;

    private Boolean verified = false;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fuel_type_id")
    private FuelType fuelType;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "petrol_station_id")
    private PetrolStation petrolStation;

    @Override
    public FuelPrice cloneEntity() {
        return FuelPrice.builder()
                .price(this.price)
                .fuelType(this.fuelType)
                .date(this.date)
                .build();
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
        fuelType.getFuelPrices().add(this);
    }
}
