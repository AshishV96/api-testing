package com.example.apitesting;

import java.util.Iterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class ApiTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTestingApplication.class, args);
	}
	
	@GetMapping("getApiKey")
	public String getApiHeader(HttpServletRequest request)
	{
		Iterator<String> headers = request.getHeaderNames().asIterator();
		System.out.println("----------------HEADERS----------------");
		while(headers.hasNext())
		{
			String headerName = headers.next();
			System.out.println(headerName+" -> "+request.getHeader(headerName));
		}
		System.out.println("---------------------------------------");
		String header = request.getHeader("X-RapidAPI-Key");
		return ObjectUtils.isEmpty(header)?"No header found":header;
	}
}
