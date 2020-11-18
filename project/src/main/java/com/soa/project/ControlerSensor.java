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
		ipe.createPlatform(sm.getId(), new Platform("Room_Platform", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Room_Platform", new TemperatureSensor("TemperatureSensorIn", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Room_Platform", new TemperatureSensor("TemperatureSensorOut", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Room_Platform", new TemperatureSensor("DoorSensor", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Room_Platform", new TemperatureSensor("WindowSensor", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Room_Platform", new TemperatureSensor("MovementSensor", T_Room.BEDROOM));
		return "sensors succeffuly set";
	}
	
	
	
}