package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FuelPriceRepository extends JpaRepository<FuelPrice, Long> {

    @Query( nativeQuery = true, value = "SELECT * FROM FUEL_PRICE WHERE (FUEL_TYPE_ID , DATE )  IN (SELECT FUEL_TYPE_ID, max(DATE) FROM FUEL_PRICE WHERE PETROL_STATION_ID  = :petrolStationId GROUP BY FUEL_TYPE_ID)")
    List<FuelPrice> findLastPricesOfTypeOfPetrolStation(@Param("petrolStationId") Long petrolStationId);

    List<FuelPrice> findAllByPetrolStationIdAndDateBetweenAndFuelTypeOrderByDate(Long petrolStation_id, @PastOrPresent LocalDateTime date, @PastOrPresent LocalDateTime date2, @NotNull FuelType fuelType);

    @Query(value = "SELECT FUEL_PRICE.ID, DATE, PRICE, FUEL_TYPE_ID, PETROL_STATION_ID, USER_ID FROM FUEL_PRICE JOIN PETROL_STATION ON PETROL_STATION.ID = FUEL_PRICE.PETROL_STATION_ID WHERE (DATE, PETROL_STATION_ID) IN ( SELECT max(DATE), PETROL_STATION_ID  FROM FUEL_PRICE JOIN PETROL_STATION ON PETROL_STATION.ID = FUEL_PRICE.PETROL_STATION_ID WHERE FUEL_PRICE.FUEL_TYPE_ID = :fuelTypeId GROUP BY PETROL_STATION_ID ) ORDER BY PRICE ASC" , nativeQuery = true)
    List<FuelPrice> getRankOfPetrolStationPrices(Long fuelTypeId);
}