package com.soa.project;

import fr.laas.mooc.helper.virtual.SensorManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class ProjectApplication {
	protected static float temperatureIn ; 
	protected static float temperatureOut ; 
	protected static int presence=0;
	protected static boolean window = false;
	protected static boolean door = false;
	protected static boolean lights = false;
	protected static boolean alarm = false;
	protected static boolean heat = false;
	protected static int givenTemperature = 15;
	
	protected static boolean testHourMode = false;
	protected static int testHour;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		IPE ipe = new IPE();
		
		ipe.pushSensor("SensorManager", "Room_Platform", "MovementSensor", String.valueOf(0));
		
		while(true)
		{
			float valueTemperatureIn =Float.parseFloat(ipe.getSensor("SensorManager","Room_Platform","TemperatureSensorIn"));
			temperatureIn = valueTemperatureIn;
			
			float valueTemperatureOut =Float.parseFloat(ipe.getSensor("SensorManager","Room_Platform","TemperatureSensorOut"));
			temperatureOut = valueTemperatureOut;
			
			//System.out.println(temperature);
			if(temperatureOut < 27.0f && temperatureOut>18.0f)
			{
				window=true;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(window));
			}
			else
			{
				window=false;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(window));
			}
			int valuePresence = Integer.parseInt(ipe.getSensor("SensorManager","Room_Platform", "MovementSensor"));
			
			int time = getHour();
			
			if((valuePresence == 0) && ( (time >= 18) || (time < 8) ))
			{
				door = false;
				ipe.pushSensor("SensorManager", "Room_Platform", "DoorSensor", String.valueOf(door));
				window = false;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(window));
				lights =false;
				
			}
			else
			{
				door = true;
				ipe.pushSensor("SensorManager", "Room_Platform", "DoorSensor", String.valueOf(door));
				window = true;
				ipe.pushSensor("SensorManager", "Room_Platform", "WindowSensor", String.valueOf(window));
				lights =true;
			}
			
			if((valuePresence == 1) && ( (time >= 18) || (time < 8) ))
			{
				alarm=false;
			}
			else
			{
				alarm=true;
			}
			
			if(temperatureIn < givenTemperature)
			{
				heat=true;
			}
			else
			{
				heat=false;
			}
				
			
		}
	}
	
	public void setHourTestMode(boolean testMode, int hour) {
		testHourMode = testMode;
		testHour = hour;
	}
	
	public static int getHour() {
		if(testHourMode) {
			return testHour;
		}else {
			return GetCurrentTime();
		}
	}
	
	@GetMapping("/temperatureIn")
	public float GetTemperatureIn() {
	
		return temperatureIn;
	}
	
	public void SetTemperatureIn(float temp)
	{
		temperatureIn = temp;
	}
	
	@GetMapping("/temperatureOut")
	public float GetTemperatureOut() {
	
		return temperatureOut;
	}
	
	public void SetTemperatureOut(float temp)
	{
		temperatureOut = temp;
	}
	
	@GetMapping("/windows")
	public boolean window()
	{
		return window;
	}
	@GetMapping("/door")
	public boolean door()
	{
		return door;
	}
	
	@GetMapping("/lights")
	public boolean lights()
	{
		return lights;
	}
	@GetMapping("/heat")
	public boolean heat()
	{
		return heat;
	}
	@GetMapping("/alarm")
	public boolean alarm()
	{
		return alarm;
	}
	private static int GetCurrentTime()
	{
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");  
		 LocalDateTime now = LocalDateTime.now();  
		 return Integer.parseInt(dtf.format(now));
	}
}
