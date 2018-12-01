package cz.sinko.smarthome.service.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public interface LightingCalculationsService {

	Duration getLightingDurationByDate(Date date);

	BigDecimal getPowerSavingsByDate(Date date);

}
