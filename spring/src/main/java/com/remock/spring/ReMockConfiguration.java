package com.remock.spring;

import com.remock.core.CallStorage;
import com.remock.core.ReMockCallsPerHost;
import com.remock.core.ReMockCallsPerHostMethod;
import com.remock.core.WireMockExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ReMockConfiguration implements WebMvcConfigurer {

  private final int callsPerHost;
  private final String pathsToIntercept;
  private final String pathsToIgnore;

  public ReMockConfiguration(@Value("${remock.callsPerHost:5}") int callsPerHost,
      @Value("${remock.pathsToIntercept:/api/}") String pathsToIntercept,
      @Value("${remock.pathsToIgnore:/remock/stubs}") String pathsToIgnore) {
    this.callsPerHost = callsPerHost;
    this.pathsToIntercept = pathsToIntercept;
    this.pathsToIgnore = pathsToIgnore;
  }

  @ConditionalOnProperty(value = "remock.enabled", havingValue = "true", matchIfMissing = true)
  @Bean
  public RemockFilter remockFilter(CallStorage callStorage) {
    return new RemockFilter(callStorage, pathsToIntercept, pathsToIgnore);
  }

  @Bean
  public CallStorage callStorage() {
    return new ReMockCallsPerHostMethod(callsPerHost, callsPerHost);
  }

  @Bean
  public WireMockExporter wireMockExporter(ReMockCallsPerHostMethod reMockCallsPerHostMethod) {
    return new WireMockExporter(reMockCallsPerHostMethod);
  }

  @Bean
  public StubsController stubsController(WireMockExporter wireMockExporter) {
    return new StubsController(wireMockExporter);
  }
}
