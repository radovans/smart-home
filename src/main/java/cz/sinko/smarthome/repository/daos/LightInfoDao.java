package cz.sinko.smarthome.repository.daos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.LightInfo;

@Repository
public interface LightInfoDao extends JpaRepository<LightInfo, Long> {

	Optional<LightInfo> findFirstByLightIdOrderByTimestampDesc(String lightId);

	List<LightInfo> findAllByTimestamp(Date date);

}
