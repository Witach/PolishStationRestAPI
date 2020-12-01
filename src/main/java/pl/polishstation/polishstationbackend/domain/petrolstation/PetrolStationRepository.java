package pl.polishstation.polishstationbackend.domain.petrolstation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.util.List;

@Repository
public interface PetrolStationRepository extends JpaRepository<PetrolStation, Long>, JpaSpecificationExecutor<PetrolStation> {
    @Query(value="SELECT * FROM PETROL_STATION ORDER BY AVG_OPINION_RATE DESC Limit 0, 5", nativeQuery=true)
    List<PetrolStation> getTopSortByOpinion(Long top);

    @Query(value = "SELECT * FROM FUEL_PRICE \n" +
            "JOIN PETROL_STATION ON PETROL_STATION.ID = FUEL_PRICE.PETROL_STATION_ID\n" +
            "WHERE (DATE, PETROL_STATION_ID) IN\n" +
            "(\n" +
            "SELECT max(DATE), PETROL_STATION_ID  FROM FUEL_PRICE \n" +
            "JOIN PETROL_STATION ON PETROL_STATION.ID = FUEL_PRICE.PETROL_STATION_ID \n" +
            "WHERE FUEL_PRICE.FUEL_TYPE_ID = :fuelTypeId \n" +
            "GROUP BY PETROL_STATION_ID\n" +
            ") \n" +
            "ORDER BY PRICE ASC" , nativeQuery = true)
    List<PetrolStation> getRankOfPetrolStationPrices(Long fuelTypeId);
}
