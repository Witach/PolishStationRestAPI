package pl.polishstation.polishstationbackend.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polishstation.polishstationbackend.domain.petrolstation.dto.PetrolStationDTO;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsDTO {
    LocalDateTime day;
    List<PetrolStationDTO> opinionRank;
    Map<String,List<PetrolStationDTO>> fuelTypePriceRank;
}
