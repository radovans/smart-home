package cz.sinko.smarthome.repository.daos;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.SunInfo;

@Repository
public interface SunInfoDao extends JpaRepository<SunInfo, Long> {

	SunInfo findByDate(LocalDate date);

}
