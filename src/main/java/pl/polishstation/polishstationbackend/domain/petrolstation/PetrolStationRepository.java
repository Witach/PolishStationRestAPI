package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

@Repository
public interface PetrolStationRepository extends JpaRepository<PetrolStation, Long> {
}
