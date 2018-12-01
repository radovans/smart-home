package cz.sinko.smarthome.repository.daos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.sinko.smarthome.repository.entities.esp.Temperature;

/**
 * @author radovan.sinko@direct.cz
 */
public interface TemperatureDao extends JpaRepository<Temperature, Long> {

	List<Temperature> findAllByTimestamp(Date timestamp);

	List<Temperature> findAllByTimestampAfter(Date timestamp);

}