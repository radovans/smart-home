package cz.sinko.smarthome.service.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import cz.sinko.smarthome.service.dtos.lighting.LightingInfoDto;

public interface LightingCalculationsService {

	Duration getLightingDurationByDate(LocalDate date);

	BigDecimal getPowerSavingsByDate(LocalDate date);

	Duration getLightingDurationDuringDayByDate(LocalDate date);

	LightingInfoDto getLightingInfo(LocalDate date);

}
