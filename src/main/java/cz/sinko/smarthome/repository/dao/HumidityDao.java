package cz.sinko.smarthome.repository.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.sinko.smarthome.repository.entity.esp.Humidity;

/**
 * @author radovan.sinko@direct.cz
 */
public interface HumidityDao extends JpaRepository<Humidity, Long> {

	List<Humidity> findAllByTimestamp(Date timestamp);

	List<Humidity> findAllByTimestampAfter(Date timestamp);

}
