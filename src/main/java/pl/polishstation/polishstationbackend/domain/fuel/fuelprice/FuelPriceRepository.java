package pl.polishstation.polishstationbackend.domain.fuel.fuelprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polishstation.polishstationbackend.domain.fuel.fueltype.FuelType;

@Repository
public interface FuelPriceRepository extends JpaRepository<FuelPrice, Long> {
}
