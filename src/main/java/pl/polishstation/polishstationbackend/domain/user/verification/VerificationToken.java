package pl.polishstation.polishstationbackend.domain.user.verification;

import lombok.*;
import pl.polishstation.polishstationbackend.domain.user.appuser.AppUser;
import pl.polishstation.polishstationbackend.entity.BasicEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationToken extends BasicEntity {
    @Column(nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @Column(name = "expiraction_date")
    private LocalDateTime expiryDate;
}
