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
		SensorManager sm = ipe.createSensorManager("TemperatureSensor");
		ipe.createPlatform(sm.getId(), new Platform("Home_Test", T_Room.BEDROOM));
		ipe.addSensor(sm.getId(), "Home_Test", new TemperatureSensor("chambreTest", T_Room.BEDROOM));
		
		System.out.println("Number of platforms : "+sm.getAllPlatforms().size());
		for(Platform p : sm.getAllPlatforms()){
			System.out.println("Pour "+p.getName()+" : "+p.getAllSensor().size()+" sensors");
		}
		
		return "sensors succeffuly set";
	}
	
	@GetMapping("/students")
	public int studentNumber() {
		return 200;
	}
	
}