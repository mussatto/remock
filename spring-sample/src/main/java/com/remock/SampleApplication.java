package com.remock;

import com.remock.spring.ReMockConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ReMockConfiguration.class)
public class SampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SampleApplication.class, args);
  }
}
