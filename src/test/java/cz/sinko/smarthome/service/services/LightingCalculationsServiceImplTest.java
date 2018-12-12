package cz.sinko.smarthome.service.services;

import static java.time.temporal.ChronoUnit.HOURS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import cz.sinko.smarthome.repository.daos.LightingDurationDao;
import cz.sinko.smarthome.repository.daos.SunInfoDao;
import cz.sinko.smarthome.repository.entities.Light;
import cz.sinko.smarthome.repository.entities.LightingDuration;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LightingCalculationsServiceImplTest {

	@Mock
	LightingDurationDao lightingDurationDao;

	@Mock
	SunInfoDao sunInfoDao;

	@InjectMocks
	LightingCalculationsServiceImpl lightingCalculationsServiceImpl;

	@Test
	public void getPowerSavingsByDate() {
		LightingCalculationsServiceImpl spyLightingCalculationsService =
				Mockito.spy(new LightingCalculationsServiceImpl(lightingDurationDao, sunInfoDao));
		Mockito.doReturn(Duration.of(3, HOURS)).when(spyLightingCalculationsService).getLightingDurationByDate(Mockito.any());
		assertEquals(new BigDecimal(0.65).setScale(2, RoundingMode.HALF_UP),
				spyLightingCalculationsService.getPowerSavingsByDate(LocalDate.now()));
	}

	@Test
	public void getLightingDurationByDateEmpty() {
		when(lightingDurationDao.findAllByDate(Mockito.any())).thenReturn(new ArrayList<>());
		assertEquals(Duration.ZERO, lightingCalculationsServiceImpl.getLightingDurationByDate(LocalDate.now()));
	}

	@Test
	public void getLightingDurationByDateOneClosedRecord() {
		Light light = new Light();
		light.setLightId("1");
		LightingDuration lightingDuration = new LightingDuration();
		lightingDuration.setLight(light);
		lightingDuration.setDurationOfLightingInSeconds(3600L);

		ArrayList<LightingDuration> lightDurations = new ArrayList<>();
		lightDurations.add(lightingDuration);

		when(lightingDurationDao.findAllByDate(Mockito.any())).thenReturn(lightDurations);
		assertEquals(Duration.of(1, HOURS),
				lightingCalculationsServiceImpl.getLightingDurationByDate(LocalDate.of(2018, 1, 1)));
	}
}