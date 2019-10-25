package cz.sinko.smarthome;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import cz.sinko.smarthome.repository.daos.UserDao;
import cz.sinko.smarthome.repository.entities.User;
import cz.sinko.smarthome.repository.entities.enums.Role;
import cz.sinko.smarthome.service.dtos.UserCreateUpdateDto;
import cz.sinko.smarthome.service.services.UserService;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class SmartHomeApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeApplication.class, args);
		log.info("Application: http://localhost:8080/actuator/health");
		log.info("Swagger doc: http://localhost:8080/swagger-ui.html");
	}

	@PostConstruct
	void afterStart() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		log.warn("Spring boot application running in UTC timezone: " + new Date());
	}

	@PostConstruct
	private void postConstruct() {
		createUserIfNotExists("admin", "admin", Role.ADMIN);
		createUserIfNotExists("user", "user", Role.USER);
	}

	private void createUserIfNotExists(String username, String password, Role role) {
		if (userDao.findByUsername(username).isEmpty()) {
			UserCreateUpdateDto user = new UserCreateUpdateDto(username, password, role);
			userService.createUser(user);
			log.info("User was created: " + username);
		} else {
			log.info("User already exists: " + username);
		}
	}

}
