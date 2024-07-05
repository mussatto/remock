package com.remock.spring;

import com.remock.core.ReMockCallsPerHost;
import com.remock.core.WireMockExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ReMockConfiguration implements WebMvcConfigurer {

  private final boolean enabled;
  private final int callsPerHost;
  private final String pathsToIntercept;
  private final String pathsToIgnore;

  public ReMockConfiguration(@Value("${remock.enabled:true}") boolean enabled,
      @Value("${remock.callsPerHost:5}") int callsPerHost,
      @Value("${remock.pathsToIntercept:/api/}") String pathsToIntercept,
      @Value("${remock.pathsToIgnore:/remock/stubs}") String pathsToIgnore) {
    this.enabled = enabled;
    this.callsPerHost = callsPerHost;
    this.pathsToIntercept = pathsToIntercept;
    this.pathsToIgnore = pathsToIgnore;
  }
  @Bean
  public RemockFilter remockFilter(ReMockCallsPerHost reMockCallsPerHost) {
    return new RemockFilter(reMockCallsPerHost, pathsToIntercept, pathsToIgnore);
  }

  @Bean
  public ReMockCallsPerHost reMockCallsPerHost() {
    return new ReMockCallsPerHost(callsPerHost, callsPerHost);
  }

  @Bean
  public WireMockExporter wireMockExporter(ReMockCallsPerHost reMockCallsPerHost) {
    return new WireMockExporter(reMockCallsPerHost);
  }

  @Bean
  public StubsController stubsController(WireMockExporter wireMockExporter) {
    return new StubsController(wireMockExporter);
  }
}
