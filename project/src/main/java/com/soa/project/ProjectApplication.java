package com.soa.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProjectApplication {

	protected IPE ipe = new IPE();

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@GetMapping("/Ressources")
	public String GetRessources() 
	{	
		String TemperatureSensorIn = this.ipe.getSensor("SensorManager","Sensor_Platform","TemperatureSensorIn");
		String TemperatureSensorOut = this.ipe.getSensor("SensorManager","Sensor_Platform","TemperatureSensorOut");
		String MovementSensor = this.ipe.getSensor("SensorManager","Sensor_Platform","MovementSensor");
		String HourSensor = this.ipe.getSensor("SensorManager","Sensor_Platform","HourSensor");
		String Door = this.ipe.getSensor("SensorManager","Object_Platform","Door");
		String Window = this.ipe.getSensor("SensorManager","Object_Platform","Window");
		String Light = this.ipe.getSensor("SensorManager","Object_Platform","Light");
		String Heater = this.ipe.getSensor("SensorManager","Object_Platform","Heater");
		String Alarm = this.ipe.getSensor("SensorManager","Object_Platform","Alarm");
		String output = "___SENSORS___<br/>";
		output += "TemperatureSensorIn : " + TemperatureSensorIn + "<br/>";
		output += "TemperatureSensorOut : " + TemperatureSensorOut + "<br/>";
		output += "MovementSensor : " + MovementSensor + "<br/>";
		output += "HourSensor : " + HourSensor + "<br/>";
		output += "___ACTUATORS___<br/>";
		output += "Door : " + Door + "<br/>";
		output += "Window : " + Window + "<br/>";
		output += "Light : " + Light + "<br/>";
		output += "Heater : " + Heater + "<br/>";
		output += "Alarm : " + Alarm + "<br/>";
		return output;
	}

	@GetMapping("/TemperatureSensorIn")
	public String GetTemperatureIn() 
	{
		return this.ipe.getSensor("SensorManager","Sensor_Platform","TemperatureSensorIn");
	}

	@GetMapping("/TemperatureSensorOut")
	public String GetTemperatureOut() 
	{
		return this.ipe.getSensor("SensorManager","Sensor_Platform","TemperatureSensorOut");
	}

	@GetMapping("/MovementSensor")
	public String GetMovementSensor() 
	{
		return this.ipe.getSensor("SensorManager","Sensor_Platform","MovementSensor");
	}

	@GetMapping("/HourSensor")
	public String GetHourSensor() 
	{
		return this.ipe.getSensor("SensorManager","Sensor_Platform","HourSensor");
	}

	@GetMapping("/Door")
	public String GetDoor() 
	{
		return this.ipe.getSensor("SensorManager","Object_Platform","Door");
	}

	@GetMapping("/Window")
	public String GetWindow() 
	{
		return this.ipe.getSensor("SensorManager","Object_Platform","Window");
	}

	@GetMapping("/Light")
	public String GetLight() 
	{
		return this.ipe.getSensor("SensorManager","Object_Platform","Light");
	}

	@GetMapping("/Heater")
	public String GetHeater() 
	{
		return this.ipe.getSensor("SensorManager","Object_Platform","Heater");
	}

	@GetMapping("/Alarm")
	public String GetAlarm() 
	{
		return this.ipe.getSensor("SensorManager","Object_Platform","Alarm");
	}

	@GetMapping(value="/TemperatureSensorIn/{tpt}")
	public void SetTemperatureIn(@PathVariable String tpt)
	{
		System.out.println("yo");
		ipe.pushSensor("SensorManager", "Sensor_Platform", "TemperatureSensorIn", tpt);

		boolean heaterBool = Boolean.parseBoolean(this.GetHeater());

		if(Integer.parseInt(tpt) < 12) {
			System.out.println(heaterBool);
			if(heaterBool == false) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Heater", "true");
			}
		}else{
			if(heaterBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Heater", "false");
			}
		}
	}

	@GetMapping(value="/TemperatureSensorOut/{tpt}")
	public void SetTemperatureOut(@PathVariable String tpt)
	{
		ipe.pushSensor("SensorManager", "Sensor_Platform", "TemperatureSensorOut", tpt);

		boolean WindowBool = Boolean.parseBoolean(this.GetWindow());
		boolean DoorBool = Boolean.parseBoolean(this.GetDoor());

		System.out.println(Integer.parseInt(tpt) < 27 && Integer.parseInt(tpt) > 18);

		if(Integer.parseInt(tpt) < 27 && Integer.parseInt(tpt) > 18) {
			if(WindowBool == false) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Window", "true");
			}
			if(DoorBool == false) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Door", "true");
			}
		}else{
			System.out.println(WindowBool == true);
			if(WindowBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Window", "false");
			}
			if(DoorBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Door", "false");
			}
		}
	}

	@GetMapping(value="/MovementSensor/{mvt}")
	public void SetMovementSensor(@PathVariable String mvt)
	{
		ipe.pushSensor("SensorManager", "Sensor_Platform", "MovementSensor", mvt);

		boolean LightBool = Boolean.parseBoolean(this.GetLight());
		boolean AlarmBool = Boolean.parseBoolean(this.GetAlarm());
		boolean DoorBool = Boolean.parseBoolean(this.GetDoor());
		int HourSensor = Integer.parseInt(this.GetHourSensor());

		int movement = Integer.parseInt(mvt);
		
		if(HourSensor >= 20 || HourSensor < 8) {
			if(movement == 1) {
				if(AlarmBool == false) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Alarm", "true");
				}
			}else{
				if(AlarmBool == true) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Alarm", "false");
				}
			}
			if(DoorBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Door", "false");
			}
			if(LightBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Light", "false");
			}
		}else{
			if(movement == 1) {
				if(LightBool == false) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Light", "true");
				}
			}else{
				if(LightBool == true) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Light", "false");
				}
			}
			if(DoorBool == false) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Door", "true");
			}
			if(AlarmBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Alarm", "false");
			}
		}
	}

	@GetMapping(value="/HourSensor/{hour}")
	public void SetHourSensor(@PathVariable String hour)
	{
		ipe.pushSensor("SensorManager", "Sensor_Platform", "HourSensor", hour);
		
		boolean DoorBool = Boolean.parseBoolean(this.GetDoor());
		boolean AlarmBool = Boolean.parseBoolean(this.GetAlarm());
		boolean LightBool = Boolean.parseBoolean(this.GetLight());
		int movement = Integer.parseInt(this.GetMovementSensor());

		if(Integer.parseInt(hour) >= 20 || Integer.parseInt(hour) < 8) {
			if(movement == 1) {
				if(AlarmBool == false) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Alarm", "true");
				}
			}else{
				if(AlarmBool == true) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Alarm", "false");
				}
			}
			if(DoorBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Door", "false");
			}
			if(LightBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Light", "false");
			}
		}else{
			if(movement == 1) {
				if(LightBool == false) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Light", "true");
				}
			}else{
				if(LightBool == true) {
					ipe.pushSensor("SensorManager", "Object_Platform", "Light", "false");
				}
			}
			if(DoorBool == false) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Door", "true");
			}
			if(AlarmBool == true) {
				ipe.pushSensor("SensorManager", "Object_Platform", "Alarm", "false");
			}
		}
	}
}
