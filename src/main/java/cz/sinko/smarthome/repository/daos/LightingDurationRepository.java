package cz.sinko.smarthome.repository.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.LightingDuration;

@Repository
public interface LightingDurationRepository extends JpaRepository<LightingDuration, Long> {

	default List<LightingDuration> findAllByDate(LocalDate date) {
		return findAllByLightingFromAfterAndLightingToBefore(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
	}

	List<LightingDuration> findAllByLightingFromAfterAndLightingToBefore(LocalDateTime from, LocalDateTime to);

}