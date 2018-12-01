package cz.sinko.smarthome.service.services;

import static java.time.temporal.ChronoUnit.HOURS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import cz.sinko.smarthome.repository.daos.LightInfoDao;
import cz.sinko.smarthome.repository.entities.LightInfo;
import cz.sinko.smarthome.repository.entities.enums.State;

@RunWith(MockitoJUnitRunner.class)
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
				spyLightingCalculationsService.getPowerSavingsByDate(new Date()));
	}

	@Ignore
	@Test
	public void getLightingDurationByDateEmpty() {
		when(lightInfoDao.findAllByTimestamp(Mockito.any())).thenReturn(new ArrayList<>());
		assertEquals(Duration.ZERO, lightingCalculationsServiceImpl.getLightingDurationByDate(new Date()));
	}

	@Ignore
	@Test
	public void getLightingDurationByDateOneClosedRecord() {
		LightInfo lightInfoOn = new LightInfo();
		lightInfoOn.setLightId("1");
		lightInfoOn.setOldState(State.OFF);
		lightInfoOn.setNewState(State.ON);
		lightInfoOn.setOldReachableState(State.ON);
		lightInfoOn.setNewReachableState(State.ON);
		lightInfoOn.setTimestamp(new Date(2018, 1, 1, 10, 0, 0));

		LightInfo lightInfoOff = new LightInfo();
		lightInfoOff.setLightId("1");
		lightInfoOff.setOldState(State.ON);
		lightInfoOff.setNewState(State.OFF);
		lightInfoOff.setOldReachableState(State.ON);
		lightInfoOff.setNewReachableState(State.ON);
		lightInfoOff.setTimestamp(new Date(2018, 1, 1, 11, 0, 0));

		ArrayList<LightInfo> lightInfos = new ArrayList<>();
		lightInfos.add(lightInfoOn);
		lightInfos.add(lightInfoOff);

		when(lightInfoDao.findAllByTimestamp(Mockito.any())).thenReturn(lightInfos);
		assertEquals(Duration.of(1, HOURS), lightingCalculationsServiceImpl.getLightingDurationByDate(new Date(2018, 1, 1)));
	}

	@Ignore
	@Test
	public void getLightingDurationByDateOneOpenedRecordWithoutStart() {
		LightInfo lightInfoOff = new LightInfo();
		lightInfoOff.setLightId("1");
		lightInfoOff.setOldState(State.ON);
		lightInfoOff.setNewState(State.OFF);
		lightInfoOff.setOldReachableState(State.ON);
		lightInfoOff.setNewReachableState(State.ON);
		lightInfoOff.setTimestamp(new Date(2018, 1, 1, 2, 0, 0));

		ArrayList<LightInfo> lightInfos = new ArrayList<>();
		lightInfos.add(lightInfoOff);

		when(lightInfoDao.findAllByTimestamp(Mockito.any())).thenReturn(lightInfos);
		assertEquals(Duration.of(2, HOURS), lightingCalculationsServiceImpl.getLightingDurationByDate(new Date(2018, 1, 1)));
	}

	@Ignore
	@Test
	public void getLightingDurationByDateOneOpenedRecordWithoutEnd() {
		LightInfo lightInfoOn = new LightInfo();
		lightInfoOn.setLightId("1");
		lightInfoOn.setOldState(State.OFF);
		lightInfoOn.setNewState(State.ON);
		lightInfoOn.setOldReachableState(State.ON);
		lightInfoOn.setNewReachableState(State.ON);
		lightInfoOn.setTimestamp(new Date(2018, 1, 1, 21, 0, 0));

		ArrayList<LightInfo> lightInfos = new ArrayList<>();
		lightInfos.add(lightInfoOn);

		when(lightInfoDao.findAllByTimestamp(Mockito.any())).thenReturn(lightInfos);
		assertEquals(Duration.of(3, HOURS), lightingCalculationsServiceImpl.getLightingDurationByDate(new Date(2018, 1, 1)));
	}
}