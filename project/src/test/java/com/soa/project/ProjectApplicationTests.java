package com.soa.project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ProjectApplicationTests {

	RestTemplate restTemplate = new RestTemplate();
	String localURL = "http://localhost:8081/";
	
	@Test
	void testSetTemperatureSensorIn() throws InterruptedException {
		restTemplate.getForObject(localURL+"TemperatureSensorIn/15", String.class);
		String TemperatureSensorIn = restTemplate.getForObject(localURL+"TemperatureSensorIn", String.class);
		Thread.sleep(1000);
		assertTrue(TemperatureSensorIn.equals("15"),"TemperatureSensorIn is not properly set");
	}
	
	@Test
	void testSetTemperatureSensorOut() throws InterruptedException {
		restTemplate.getForObject(localURL+"TemperatureSensorOut/25", String.class);
		String TemperatureSensorOut = restTemplate.getForObject(localURL+"TemperatureSensorOut", String.class);
		Thread.sleep(1000);
		assertTrue(TemperatureSensorOut.equals("25"),"TemperatureSensorOut is not properly set");
	}
	
	@Test
	void testSetMovementSensor() throws InterruptedException {
		restTemplate.getForObject(localURL+"MovementSensor/1", String.class);
		String MovementSensor = restTemplate.getForObject(localURL+"MovementSensor", String.class);
		Thread.sleep(1000);
		assertTrue(MovementSensor.equals("1"),"MovementSensor is not properly set");
	}
	
	@Test
	void testSetHourSensor() throws InterruptedException {
		restTemplate.getForObject(localURL+"HourSensor/10", String.class);
		String HourSensor = restTemplate.getForObject(localURL+"HourSensor", String.class);
		Thread.sleep(1000);
		assertTrue(HourSensor.equals("10"),"HourSensor is not properly set");
	}
	
	@Test
	void testHeater() throws InterruptedException {
		restTemplate.getForObject(localURL + "TemperatureSensorIn/10", String.class);
		String heater = restTemplate.getForObject(localURL+"Heater", String.class);
		Thread.sleep(1000);
		assertTrue(heater.equals("true"),"Heater not ON with low temperatureIn");
		
		restTemplate.getForObject(localURL + "TemperatureSensorIn/20", String.class);
		heater = restTemplate.getForObject(localURL+"Heater", String.class);
		Thread.sleep(1000);
		assertTrue(heater.equals("false"),"Heater not OFF with high temperatureIn");
	}
	
	@Test
	void testLight() throws InterruptedException {
		restTemplate.getForObject(localURL + "MovementSensor/1", String.class);
		restTemplate.getForObject(localURL + "HourSensor/15", String.class);
		String light = restTemplate.getForObject(localURL+"Light", String.class);
		Thread.sleep(1000);
		assertTrue(light.equals("true"),"Light not ON with movement and good hours");
		
		restTemplate.getForObject(localURL + "MovementSensor/1", String.class);
		restTemplate.getForObject(localURL + "HourSensor/20", String.class);
		light = restTemplate.getForObject(localURL+"Light", String.class);
		Thread.sleep(1000);
		assertTrue(light.equals("false"),"Light not OFF with movement and bad hours");
		
		restTemplate.getForObject(localURL + "MovementSensor/0", String.class);
		restTemplate.getForObject(localURL + "HourSensor/15", String.class);
		light = restTemplate.getForObject(localURL+"Light", String.class);
		Thread.sleep(1000);
		assertTrue(light.equals("false"),"Light not OFF with no movement and good hours");
		
		restTemplate.getForObject(localURL + "MovementSensor/0", String.class);
		restTemplate.getForObject(localURL + "HourSensor/20", String.class);
		light = restTemplate.getForObject(localURL+"Light", String.class);
		Thread.sleep(1000);
		assertTrue(light.equals("false"),"Light not OFF with no movement and bad hours");
	}
	
	@Test
	void testAlarm() throws InterruptedException {
		restTemplate.getForObject(localURL + "MovementSensor/1", String.class);
		restTemplate.getForObject(localURL + "HourSensor/15", String.class);
		String alarm = restTemplate.getForObject(localURL+"Alarm", String.class);
		Thread.sleep(1000);
		assertTrue(alarm.equals("false"),"Alarm not OFF with movement and good hours");
		
		restTemplate.getForObject(localURL + "MovementSensor/1", String.class);
		restTemplate.getForObject(localURL + "HourSensor/20", String.class);
		alarm = restTemplate.getForObject(localURL+"Alarm", String.class);
		Thread.sleep(1000);
		assertTrue(alarm.equals("true"),"Alarm not ON with movement and bad hours");
		
		restTemplate.getForObject(localURL + "MovementSensor/0", String.class);
		restTemplate.getForObject(localURL + "HourSensor/15", String.class);
		alarm = restTemplate.getForObject(localURL+"Alarm", String.class);
		Thread.sleep(1000);
		assertTrue(alarm.equals("false"),"Alarm not OFF with no movement and good hours");
		
		restTemplate.getForObject(localURL + "MovementSensor/0", String.class);
		restTemplate.getForObject(localURL + "HourSensor/20", String.class);
		alarm = restTemplate.getForObject(localURL+"Alarm", String.class);
		Thread.sleep(1000);
		assertTrue(alarm.equals("false"),"Alarm not OFF with no movement and bad hours");
	}
	
	@Test
	void testWindow() throws InterruptedException {
		restTemplate.getForObject(localURL + "TemperatureSensorOut/10", String.class);
		String window = restTemplate.getForObject(localURL+"Window", String.class);
		Thread.sleep(1000);
		assertTrue(window.equals("false"),"Window not OFF with low temperatureIn");
		
		restTemplate.getForObject(localURL + "TemperatureSensorOut/20", String.class);
		window = restTemplate.getForObject(localURL+"Window", String.class);
		Thread.sleep(1000);
		assertTrue(window.equals("true"),"Window not OFF with good temperatureIn");
		
		restTemplate.getForObject(localURL + "TemperatureSensorOut/30", String.class);
		window = restTemplate.getForObject(localURL+"Window", String.class);
		Thread.sleep(1000);
		assertTrue(window.equals("false"),"Window not OFF with high temperatureIn");
	}
	
	@Test
	void testDoor() throws InterruptedException {
		restTemplate.getForObject(localURL + "HourSensor/10", String.class);
		String door = restTemplate.getForObject(localURL+"Door", String.class);
		Thread.sleep(1000);
		assertTrue(door.equals("true"),"Door not ON with good hour");
		
		restTemplate.getForObject(localURL + "HourSensor/20", String.class);
		door = restTemplate.getForObject(localURL+"Door", String.class);
		Thread.sleep(1000);
		assertTrue(door.equals("false"),"Door not OFF with bad hour");
	}
	
}
