package com.remock.spring;

import com.remock.core.ReMockCallsPerHost;
import com.remock.core.WireMockExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReMockConfiguration implements WebMvcConfigurer {

  private final boolean enabled;
  private final int callsPerHost;
  private final String pathsToIntercept;
  private final String pathsToIgnore;

  public ReMockConfiguration(@Value("${remock.enabled:true}") boolean enabled,
      @Value("${remock.callsPerHost:5}") int callsPerHost,
      @Value("${remock.pathsToIntercept:/api/**}") String pathsToIntercept,
      @Value("${remock.pathsToIgnore:/remock/stubs}") String pathsToIgnore) {
    this.enabled = enabled;
    this.callsPerHost = callsPerHost;
    this.pathsToIntercept = pathsToIntercept;
    this.pathsToIgnore = pathsToIgnore;
  }

  @Bean
  public ReMockConfigurer reMockConfigurer(ControllerInterceptor controllerInterceptor) {
    return new ReMockConfigurer(controllerInterceptor, pathsToIntercept, pathsToIgnore);
  }

  @Bean
  public ControllerInterceptor controllerInterceptor() {
    return new ControllerInterceptor();
  }

  @Bean
  public ReMockResponseAdvice reMockResponseAdvice(ReMockCallsPerHost reMockCallsPerHost) {
    return new ReMockResponseAdvice(reMockCallsPerHost);
  }

  @Bean
  public ReMockCallsPerHost reMockCallsPerHost() {
    return new ReMockCallsPerHost(callsPerHost);
  }

  @Bean
  public WireMockExporter wireMockExporter() {
    return new WireMockExporter(new ReMockCallsPerHost(callsPerHost));
  }

  @Bean
  public StubsController stubsController(WireMockExporter wireMockExporter) {
    return new StubsController(wireMockExporter);
  }
}
