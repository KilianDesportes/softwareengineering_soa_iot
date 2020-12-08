package com.soa.project;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.laas.mooc.helper.virtual.Platform;
import fr.laas.mooc.helper.virtual.SensorManager;
import fr.laas.mooc.helper.virtual.T_Room;
import fr.laas.mooc.helper.virtual.TemperatureSensor;

@EnableAutoConfiguration
@RestController
public class ControlerSensor {

	@GetMapping("/setupsensors")
	public String setUpsensors() {
		IPE ipe = new IPE();
		
		SensorManager sm = ipe.createSensorManager("SensorManager");
		
		ipe.createPlatform(sm.getId(), new Platform("Sensor_Platform", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Sensor_Platform", new TemperatureSensor("TemperatureSensorIn", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Sensor_Platform", new TemperatureSensor("TemperatureSensorOut", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Sensor_Platform", new TemperatureSensor("MovementSensor", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Sensor_Platform", new TemperatureSensor("HourSensor", T_Room.BEDROOM));
		
		ipe.pushSensor("SensorManager", "Sensor_Platform", "TemperatureSensorIn", "20");
		ipe.pushSensor("SensorManager", "Sensor_Platform", "TemperatureSensorOut", "10");
		ipe.pushSensor("SensorManager", "Sensor_Platform", "MovementSensor", "1");
		ipe.pushSensor("SensorManager", "Sensor_Platform", "HourSensor", "17");

		ipe.createPlatform(sm.getId(), new Platform("Object_Platform", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Object_Platform", new TemperatureSensor("Door", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Object_Platform", new TemperatureSensor("Window", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Object_Platform", new TemperatureSensor("Light", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Object_Platform", new TemperatureSensor("Heater", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Object_Platform", new TemperatureSensor("Alarm", T_Room.BEDROOM));
		
		ipe.pushSensor("SensorManager", "Object_Platform", "Door", "false");
		ipe.pushSensor("SensorManager", "Object_Platform", "Window", "false");
		ipe.pushSensor("SensorManager", "Object_Platform", "Light", "false");
		ipe.pushSensor("SensorManager", "Object_Platform", "Heater", "false");
		ipe.pushSensor("SensorManager", "Object_Platform", "Alarm", "false");

		return "sensors succeffuly set";
	}
	
	
	
}