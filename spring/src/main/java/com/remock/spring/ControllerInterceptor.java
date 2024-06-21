package com.remock.spring;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;

import com.remock.core.ReMockCall;
import com.remock.core.ReMockPerHostStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ControllerInterceptor implements HandlerInterceptor {

  private ReMockPerHostStore reMockPerHostStore;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {

    reMockPerHostStore.add(createReMockCall(request, response));
  }

  private ReMockCall createReMockCall(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    Map<String, String> requestHeaders = Collections.list(request.getHeaderNames())
        .stream()
        .collect(Collectors.toMap(h -> h, request::getHeader));

    var remockRequest = aReMockRequest()
        .withHost(request.getRemoteHost())
        .withMethod(request.getMethod())
        .withPath(request.getContextPath())
        .withQuery(request.getQueryString())
        .withAccept(request.getHeader("Accept") == null ? "" : request.getHeader("Accept"))
        .withContentType(request.getContentType())
        .withHeaders(requestHeaders)
        .withBody(request.getReader().lines().collect(Collectors.joining()))
        .build();

    Map<String, String> responseHeaders = response.getHeaderNames()
        .stream()
        .collect(Collectors.toMap(h -> h, request::getHeader));
    var remockResponse = aReMockResponse()
        .withStatus(response.getStatus())
        .withHeaders(responseHeaders)
        .withBody(response.getWriter().toString())
        .withContentType(response.getContentType())
        .build();

    return new ReMockCall(remockRequest, remockResponse);
  }

}
