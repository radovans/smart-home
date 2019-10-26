package cz.sinko.smarthome;

import static java.util.Collections.singleton;
import static org.zalando.logbook.BodyFilter.merge;
import static org.zalando.logbook.BodyFilters.defaultValue;
import static org.zalando.logbook.Conditions.exclude;
import static org.zalando.logbook.Conditions.requestTo;
import static org.zalando.logbook.json.JsonBodyFilters.replaceJsonStringProperty;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.autoconfigure.LogbookAutoConfiguration;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import cz.sinko.smarthome.repository.daos.UserRepository;
import cz.sinko.smarthome.repository.entities.enums.Role;
import cz.sinko.smarthome.service.dtos.user.UserCreateUpdateDto;
import cz.sinko.smarthome.service.services.UserService;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class SmartHomeApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

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
		if (userRepository.findByUsername(username).isEmpty()) {
			UserCreateUpdateDto user = new UserCreateUpdateDto(username, password, role);
			userService.createUser(user);
			log.info("User was created: " + username);
		} else {
			log.info("User already exists: " + username);
		}
	}

	@Bean
	public Logbook logbook() {
		HttpLogFormatter formatter = new JsonHttpLogFormatter();
		LogstashLogbackSink sink = new LogstashLogbackSink(formatter);

		return Logbook.builder()
				.sink(sink)
				.condition(exclude(
						requestTo("/actuator"),
						requestTo("/actuator/*"),
						requestTo("/v2/api-doce"),
						requestTo("/swagger-ui.html"),
						requestTo("/swagger-resources"),
						requestTo("/csrf"),
						requestTo("/webjars/springfox-swagger-ui")))
				.bodyFilter(merge(defaultValue(),
						replaceJsonStringProperty(singleton("password"), "hidden-value")))
				.build();
	}
}
