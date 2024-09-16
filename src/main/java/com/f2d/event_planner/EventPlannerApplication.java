package com.f2d.event_planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventPlannerApplication.class, args);
	}

}
