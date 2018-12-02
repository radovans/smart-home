package cz.sinko.smarthome.repository.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.LightInfo;

@Repository
public interface LightInfoDao extends JpaRepository<LightInfo, Long> {

	Optional<LightInfo> findFirstByLightIdOrderByTimestampDesc(String lightId);

	default List<LightInfo> findAllByDate(LocalDate date) {
		return findAllByTimestampBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
	}

	List<LightInfo> findAllByTimestampBetween(LocalDateTime from, LocalDateTime to);

	default List<LightInfo> findAllByDateWithLightingDuration(LocalDate date) {
		return findAllByTimestampBetweenAndDurationOfLightingInSecondsIsNotNull(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
	}

	List<LightInfo> findAllByTimestampBetweenAndDurationOfLightingInSecondsIsNotNull(LocalDateTime from, LocalDateTime to);

}
