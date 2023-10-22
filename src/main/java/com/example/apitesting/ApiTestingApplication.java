package com.example.apitesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class ApiTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTestingApplication.class, args);
	}

}
