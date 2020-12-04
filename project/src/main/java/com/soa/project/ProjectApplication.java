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

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class ProjectApplication {
	protected static float temperature ; 
	protected static int presence=0;
	protected static boolean closedFenetre = false;
	protected static boolean closedDoor = false;
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		IPE ipe = new IPE();
		SensorManager sm = ipe.createSensorManager("SensorManager");
		
		//float value = ipe.PushSensor(sm.getId(), "Room_Platform", "TemperatureSensor",19.0f);
		//System.out.println(value);
		float maxValue = 18.0f;
		ipe.pushSensor("SensorManager", "Room_Platform", "MovementSensor", String.valueOf(0));
		while(true)
		{
			float valueFentre =Float.parseFloat(ipe.getSensor("SensorManager","Room_Platform","TemperatureSensor"));
			temperature = valueFentre;
			//System.out.println(temperature);
			if(temperature < 27.0f && temperature>18.0f)
			{
				closedFenetre=false;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(closedFenetre));
			}
			else
			{
				closedFenetre=true;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(closedFenetre));
			}
			int valuePresence = Integer.parseInt(ipe.getSensor("SensorManager","Room_Platform", "MovementSensor"));
			int time =  GetCurrentTime();
			if((valuePresence == 0) && ( (time >= 18) || (time < 8) ))
			{
				closedDoor = true;
				ipe.pushSensor("SensorManager", "Room_Platform", "DoorSensor", String.valueOf(closedDoor));
				closedFenetre = true;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(closedFenetre));
				
			}
			else
			{
				closedDoor = false;
				ipe.pushSensor("SensorManager", "Room_Platform", "DoorSensor", String.valueOf(closedDoor));
				closedFenetre = false;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(closedFenetre));
			}
				
			
		}
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
	@GetMapping("/door")
	public boolean door()
	{
		return this.closedDoor;
	}
	
	
	private static int GetCurrentTime()
	{
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");  
		 LocalDateTime now = LocalDateTime.now();  
		 return Integer.parseInt(dtf.format(now));
	}
}
