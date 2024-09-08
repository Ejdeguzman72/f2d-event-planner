package com.f2d.event_planner.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.f2d.event_planner.feign")
public class FeignConfig {
}
