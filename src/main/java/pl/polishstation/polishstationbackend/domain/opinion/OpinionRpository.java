package pl.polishstation.polishstationbackend.domain.opinion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRpository extends JpaRepository<Opinion, Long> {
}
