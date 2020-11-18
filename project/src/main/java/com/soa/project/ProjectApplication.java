package com.soa.project;
import fr.laas.mooc.helper.virtual.Platform;
import fr.laas.mooc.helper.virtual.SensorManager;
import fr.laas.mooc.helper.virtual.T_Room;
import fr.laas.mooc.helper.virtual.TemperatureSensor;

import org.eclipse.om2m.commons.resource.AE;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.laas.mooc.helper.http.HTTPPost;
import fr.laas.mooc.helper.om2m.ResourceCreator;
import fr.laas.mooc.helper.om2m.Serializer;

@SpringBootApplication
@RestController
public class ProjectApplication {
	protected static float temperature ; 
	protected static boolean closedFenetre = false;
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		IPE ipe = new IPE();
		SensorManager sm = ipe.createSensorManager("SensorManager");
		
		//float value = ipe.PushSensor(sm.getId(), "Room_Platform", "TemperatureSensor",19.0f);
		//System.out.println(value);
		float maxValue = 18.0f;
		while(true)
		{
			float value = ipe.getSensor("SensorManager","Room_Platform","TemperatureSensor");
			temperature = value;
			System.out.println(temperature);
			if(temperature < 27.0f && temperature>18.0f)
			{
				closedFenetre=false;
			}
			else
			{
				closedFenetre=true;
			}
		}
	}
	
	@GetMapping("/students")
	public int studentNumber() {
	
		return 200;
	}
	@GetMapping("/temperature")
	public float GetTemperature() {
	
		return this.temperature;
	}
	
	public void SetTemperature(float temp)
	{
		this.temperature = temp;
	}
	
	@GetMapping("/fenetre")
	public boolean fenetre()
	{
		return this.closedFenetre;
	}
}
