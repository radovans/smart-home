package cz.sinko.smarthome.service.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

public interface LightingCalculationsService {

	Duration getLightingDurationByDate(LocalDate date);

	BigDecimal getPowerSavingsByDate(LocalDate date);

}
