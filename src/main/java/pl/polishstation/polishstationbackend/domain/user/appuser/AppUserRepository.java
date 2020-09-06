package pl.polishstation.polishstationbackend.domain.user.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByEmailEquals(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
