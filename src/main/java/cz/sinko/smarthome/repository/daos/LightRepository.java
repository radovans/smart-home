package cz.sinko.smarthome.repository.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.Light;

@Repository
public interface LightRepository extends JpaRepository<Light, Long> {

	Optional<Light> findByLightId(String lightId);

}
