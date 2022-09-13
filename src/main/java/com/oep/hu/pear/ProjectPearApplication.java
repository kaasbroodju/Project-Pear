package com.oep.hu.pear;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Project Pear",
				version = "1.0.0",
				description = """
						Project Pear is developed for Open-Ict of University of Applied Sciences Utrecht (HU).
						This project is intended to replace the current ecosystem that Open-ict is currently using.
						Students & teachers can contribute to this project, by suggesting features and developing them.
						Students can also learn how their role looks like in an open-source project.
						To contribute go to: https://github.com/kaasbroodju/Project-Pear""",
				contact = @Contact(name = "Github", url = "https://github.com/kaasbroodju/Project-Pear")

		))
public class ProjectPearApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectPearApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
