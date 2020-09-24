package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;

import java.util.List;

@Repository
public interface FuelPriceRepository extends JpaRepository<FuelPrice, Long> {

    @Query( nativeQuery = true, value = "SELECT * FROM FUEL_PRICE WHERE (FUEL_TYPE_ID , DATE )  IN (SELECT FUEL_TYPE_ID, max(DATE) FROM FUEL_PRICE WHERE PETROL_STATION_ID  = :petrolStationId GROUP BY FUEL_TYPE_ID)")
    List<FuelPrice> findLastPricesOfTypeOfPetrolStation(@Param("petrolStationId") Long petrolStationId);
}