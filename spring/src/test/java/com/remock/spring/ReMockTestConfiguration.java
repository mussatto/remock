package com.remock.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReMockTestConfiguration {

  @Bean
  public ReMockTestController reMockTestController() {
    return new ReMockTestController();
  }
}
