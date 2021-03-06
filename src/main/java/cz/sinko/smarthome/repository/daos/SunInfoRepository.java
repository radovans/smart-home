package cz.sinko.smarthome.repository.daos;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.SunInfo;

@Repository
public interface SunInfoRepository extends JpaRepository<SunInfo, Long> {

	Optional<SunInfo> findByDate(LocalDate date);

}
