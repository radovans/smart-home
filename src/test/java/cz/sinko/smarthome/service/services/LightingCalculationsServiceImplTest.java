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

import cz.sinko.smarthome.repository.daos.LightInfoDao;
import cz.sinko.smarthome.repository.entities.LightInfo;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LightingCalculationsServiceImplTest {

	@Mock
	LightInfoDao lightInfoDao;

	@InjectMocks
	LightingCalculationsServiceImpl lightingCalculationsServiceImpl;

	@Test
	public void getPowerSavingsByDate() {
		LightingCalculationsServiceImpl spyLightingCalculationsService =
				Mockito.spy(new LightingCalculationsServiceImpl());
		Mockito.doReturn(Duration.of(3, HOURS)).when(spyLightingCalculationsService).getLightingDurationByDate(Mockito.any());
		assertEquals(new BigDecimal(0.65).setScale(2, RoundingMode.HALF_UP),
				spyLightingCalculationsService.getPowerSavingsByDate(LocalDate.now()));
	}

	@Test
	public void getLightingDurationByDateEmpty() {
		when(lightInfoDao.findAllByDate(Mockito.any())).thenReturn(new ArrayList<>());
		assertEquals(Duration.ZERO, lightingCalculationsServiceImpl.getLightingDurationByDate(LocalDate.now()));
	}

	@Test
	public void getLightingDurationByDateOneClosedRecord() {
		LightInfo lightInfo = new LightInfo();
		lightInfo.setLightId("1");
		lightInfo.setDurationOfLightingInSeconds(3600l);

		ArrayList<LightInfo> lightInfos = new ArrayList<>();
		lightInfos.add(lightInfo);

		when(lightInfoDao.findAllByDateWithLightingDuration(Mockito.any())).thenReturn(lightInfos);
		assertEquals(Duration.of(1, HOURS),
				lightingCalculationsServiceImpl.getLightingDurationByDate(LocalDate.of(2018, 1, 1)));
	}
}