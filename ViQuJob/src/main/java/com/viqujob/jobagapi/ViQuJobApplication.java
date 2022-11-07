package com.viqujob.jobagapi;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ViQuJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViQuJobApplication.class, args);
	}

}
