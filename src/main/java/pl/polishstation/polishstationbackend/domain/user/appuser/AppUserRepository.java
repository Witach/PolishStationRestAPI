package pl.polishstation.polishstationbackend.domain.user.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmailEquals(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
