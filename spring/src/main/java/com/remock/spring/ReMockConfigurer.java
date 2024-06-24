package com.remock.spring;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ReMockConfigurer implements WebMvcConfigurer {

  private final ControllerInterceptor controllerInterceptor;
  private final String includePaths;
  private final String excludePaths;

  public ReMockConfigurer(ControllerInterceptor controllerInterceptor, String includePaths,
      String excludePaths) {
    this.controllerInterceptor = controllerInterceptor;
    this.includePaths = includePaths;
    this.excludePaths = excludePaths;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(controllerInterceptor)
        .addPathPatterns(includePaths)
        .excludePathPatterns(excludePaths);
  }

}
