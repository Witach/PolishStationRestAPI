package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    void deleteFuelTypeByNameEquals(String name);
    Optional<FuelType> findByName(String name);
}
