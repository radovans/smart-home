package cz.sinko.smarthome.repository.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.Light;
import cz.sinko.smarthome.repository.entities.LightState;

@Repository
public interface LightStateDao extends JpaRepository<LightState, Long> {

	Optional<LightState> findFirstByLightOrderByTimestampDesc(Light light);

}