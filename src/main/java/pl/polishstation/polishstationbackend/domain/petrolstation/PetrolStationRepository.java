package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polishstation.polishstationbackend.domain.fuel.fuelprice.FuelPrice;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.List;

@Repository
public interface PetrolStationRepository extends JpaRepository<PetrolStation, Long>, JpaSpecificationExecutor<PetrolStation> {
    @Query(value="SELECT * FROM PETROL_STATION ORDER BY AVG_OPINION_RATE DESC Limit 0, 5", nativeQuery=true)
    List<PetrolStation> getTopSortByOpinion(Long top);
}
