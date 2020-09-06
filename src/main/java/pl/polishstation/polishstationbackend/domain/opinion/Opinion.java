package pl.polishstation.polishstationbackend.domain.opinion;

import lombok.*;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.entity.BasicEntity;
import pl.polishstation.polishstationbackend.domain.petrolstation.entity.PetrolStation;
import pl.polishstation.polishstationbackend.utils.CloneableEntity;

import javax.persistence.*;
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

    @Column(precision = 1, nullable = false)
    private Integer mark;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "petrol_station_id")
    private PetrolStation petrolStation;

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
