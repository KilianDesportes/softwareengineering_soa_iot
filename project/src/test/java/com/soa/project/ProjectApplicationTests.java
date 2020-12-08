package com.soa.project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@Test
	void contextLoads() {
		
	}
	
	@Test
	void testTemperatureOut() {
		IPE ipe = new IPE();
		ProjectApplication app = new ProjectApplication();
		
		Float tptOut = 19.0f;
		
		ipe.pushSensor("SensorManager", "Room_Platform", "TemperatureSensorOut", String.valueOf(tptOut));

		String valueTemperatureSensorOut = ipe.getSensor("SensorManager","Room_Platform","TemperatureSensorOut");
		assertTrue(valueTemperatureSensorOut.compareTo(tptOut.toString())==0, "Innacurate temperature");

		boolean valueWindow = app.window();
		assertTrue(valueWindow	==	true, "Innacurate window state");
		
		tptOut = 30.0f;
		
		ipe.pushSensor("SensorManager", "Room_Platform", "TemperatureSensorOut", String.valueOf(tptOut));
		
		valueTemperatureSensorOut = ipe.getSensor("SensorManager","Room_Platform","TemperatureSensorOut");
		assertTrue(valueTemperatureSensorOut.compareTo(tptOut.toString())==0, "Innacurate temperature");

		valueWindow = app.window();
		assertTrue(valueWindow	==	false, "Innacurate window state");
	}
	
	@Test
	void testTemperatureIn() {
		IPE ipe = new IPE();
		ProjectApplication app = new ProjectApplication();
		
		Float tptOut = 17.0f;
		
		ipe.pushSensor("SensorManager", "Room_Platform", "TemperatureSensorIn", String.valueOf(tptOut));

		String valueTemperatureSensorOut = ipe.getSensor("SensorManager","Room_Platform","TemperatureSensorIn");
		assertTrue(valueTemperatureSensorOut.compareTo(tptOut.toString())==0, "Innacurate temperature");

		boolean valueHeater = app.heat();
		assertTrue(valueHeater	==	false, "Innacurate heater state");
		
		tptOut = 10.0f;
		
		ipe.pushSensor("SensorManager", "Room_Platform", "TemperatureSensorOut", String.valueOf(tptOut));
		
		valueTemperatureSensorOut = ipe.getSensor("SensorManager","Room_Platform","TemperatureSensorOut");
		assertTrue(valueTemperatureSensorOut.compareTo(tptOut.toString())==0, "Innacurate temperature");

		valueHeater = app.heat();
		assertTrue(valueHeater	==	false, "Innacurate heater state");
		
	}
	
	@Test
	void testPresenceHour() {
		IPE ipe = new IPE();
		ProjectApplication app = new ProjectApplication();
		
		app.setHourTestMode(true,10);
		int presence = 0;
		
		ipe.pushSensor("SensorManager", "Room_Platform", "MovementSensor", String.valueOf(presence));
		int valuePresence = Integer.parseInt(ipe.getSensor("SensorManager","Room_Platform", "MovementSensor"));

		boolean valueWindow = app.window();
		boolean valueDoor = app.door();
		boolean valueLight = app.lights();

		assertTrue(valuePresence == presence, "Innacurate presence value");
		assertTrue(valueWindow	==	true, "Innacurate window state");
		assertTrue(valueDoor	==	true, "Innacurate door state");
		assertTrue(valueLight	==	true, "Innacurate light state");
		
		app.setHourTestMode(true,20);
		
		valueWindow = app.window();
		valueDoor = app.door();
		valueLight = app.lights();

		assertTrue(valuePresence == presence, "Innacurate presence value");
		assertTrue(valueWindow	==	false, "Innacurate window state");
		assertTrue(valueDoor	==	false, "Innacurate door state");
		assertTrue(valueLight	==	false, "Innacurate light state");
		
		presence = 1;
		
		ipe.pushSensor("SensorManager", "Room_Platform", "MovementSensor", String.valueOf(presence));
		valuePresence = Integer.parseInt(ipe.getSensor("SensorManager","Room_Platform", "MovementSensor"));

		valueWindow = app.window();
		valueDoor = app.door();
		valueLight = app.lights();

		assertTrue(valuePresence == presence, "Innacurate presence value");
		assertTrue(valueWindow	==	true, "Innacurate window state");
		assertTrue(valueDoor	==	true, "Innacurate door state");
		assertTrue(valueLight	==	true, "Innacurate light state");
		
		app.setHourTestMode(false,0);
	}
	
	@Test
	void testAlarm() {
		IPE ipe = new IPE();
		ProjectApplication app = new ProjectApplication();
		
		app.setHourTestMode(true,20);
		int presence = 1;
		
		ipe.pushSensor("SensorManager", "Room_Platform", "MovementSensor", String.valueOf(presence));
		int valuePresence = Integer.parseInt(ipe.getSensor("SensorManager","Room_Platform", "MovementSensor"));

		boolean valueAlarm = app.alarm();
				
		assertTrue(valuePresence == presence, "Innacurate presence value");
		assertTrue(valueAlarm	==	true, "Innacurate window state");
		
		app.setHourTestMode(true,10);
		valueAlarm = app.alarm();
		
		assertTrue(valuePresence == presence, "Innacurate presence value");
		assertTrue(valueAlarm	==	false, "Innacurate window state");
		
		presence = 0;
		app.setHourTestMode(true,20);

		ipe.pushSensor("SensorManager", "Room_Platform", "MovementSensor", String.valueOf(presence));
		valuePresence = Integer.parseInt(ipe.getSensor("SensorManager","Room_Platform", "MovementSensor"));

		valueAlarm = app.alarm();
				
		assertTrue(valuePresence == presence, "Innacurate presence value");
		assertTrue(valueAlarm	==	false, "Innacurate window state");
		
		app.setHourTestMode(false,0);
	}
	
}
