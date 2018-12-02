package cz.sinko.smarthome.repository.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.RoomInfo;

@Repository
public interface RoomInfoDao extends JpaRepository<RoomInfo, Long> {

	List<RoomInfo> findAllBySensorIdAndTimestamp(long sensorId, LocalDate timestamp);

	List<RoomInfo> findAllBySensorIdAndTimestampAfter(long sensorId, LocalDateTime timestamp);

}
