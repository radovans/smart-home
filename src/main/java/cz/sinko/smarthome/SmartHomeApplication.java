package cz.sinko.smarthome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartHomeApplication {

	private static final Logger logger = LoggerFactory.getLogger(SmartHomeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeApplication.class, args);
		logger.info("Application: http://localhost:8080/actuator/health");
		logger.info("Swagger doc: http://localhost:8080/swagger-ui.html");
	}

}
