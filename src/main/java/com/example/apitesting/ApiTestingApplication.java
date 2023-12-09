package com.example.apitesting;

import com.example.apitesting.domain.NewClass;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ApiTestingApplication {

	@Autowired
	NewClass newClass;

	public static void main(String[] args) {
		SpringApplication.run(ApiTestingApplication.class, args);
	}

	@Bean
	public Docket createDocket() {
		//create empty Docket
		return new Docket(DocumentationType.SWAGGER_2)
				.select()  //find controller classes
				.apis(RequestHandlerSelectors.basePackage("com.example.apitesting.api.RapidApiController"))    //provide all controller common package name
				.paths(PathSelectors.any())
				.build(); //create Docket with details

	}

	@Scheduled(fixedRate = 60000)
	public void runParallelTask() throws InterruptedException {

		System.out.println("-------------Parent Thread :"+Thread.currentThread().getId()+" started--------------" );
		CountDownLatch latch = new CountDownLatch(6);
		for(int i=0; i<5; i++)
			newClass.parallelTask(latch);
		newClass.exceptionThrower(latch);
		latch.await();
		System.out.println("-------------Parent Thread ended--------------" );

	}

}
