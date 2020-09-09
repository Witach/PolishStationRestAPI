package pl.polishstation.polishstationbackend.domain.opinion;

import lombok.*;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.entity.BasicEntity;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.utils.CloneableEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
public class Opinion extends BasicEntity implements CloneableEntity<Opinion> {

    private LocalDateTime date;

    @Min(0)
    @Max(5)
    @Column(precision = 1, nullable = false)
    private Integer mark;

    @NotBlank
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "petrol_station_id")
    private PetrolStation petrolStation;

    @NotBlank
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Override
    public Opinion cloneEntity() {
        return Opinion.builder()
                .date(date)
                .mark(mark)
                .build();
    }
}
