package SensorManager.SensorManager.SensorController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureSensor {

	@GetMapping("temperature")
	public int getTemperature() {
		return 200;
	}
	
	@GetMapping("/students")
	public int studentNumber() {
		return 200;
	}
	
}
