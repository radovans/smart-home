package cz.sinko.smarthome.repository.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.smarthome.repository.entities.WeatherInfo;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
}
