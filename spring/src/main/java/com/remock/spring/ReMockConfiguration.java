package com.remock.spring;

import com.remock.core.CallStorage;
import com.remock.core.ReMockCallsPerHostMethod;
import com.remock.core.WireMockExporter;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ReMockConfiguration implements WebMvcConfigurer {

  private final int callsPerHost;
  private final String pathsToIntercept;
  private final String pathsToIgnore;
  private final List<String> includeHeaders;

  public ReMockConfiguration(@Value("${remock.callsPerHost:5}") int callsPerHost,
      @Value("${remock.pathsToIntercept:/api/}") String pathsToIntercept,
      @Value("${remock.pathsToIgnore:/remock/stubs}") String pathsToIgnore,
      @Value("${remock.includeHeaders:Content-Type,Authorization,Accept,Cookie,Set-Cookie}") String includeHeaders) {
    this.callsPerHost = callsPerHost;
    this.pathsToIntercept = pathsToIntercept;
    this.pathsToIgnore = pathsToIgnore;
    this.includeHeaders = List.of(includeHeaders.split(","));
  }

  @Bean
  public RemockFilter remockFilter(CallStorage callStorage) {
    return new RemockFilter(callStorage, pathsToIntercept, pathsToIgnore, includeHeaders);
  }

  @Bean
  public CallStorage callStorage() {
    return new ReMockCallsPerHostMethod(callsPerHost, callsPerHost);
  }

  @Bean
  public StubsController stubsController(CallStorage callStorage) {
    return new StubsController(new WireMockExporter(callStorage));
  }
}
