package com.remock.spring;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;

import com.remock.core.ReMockCall;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class ControllerInterceptor implements HandlerInterceptor {

  public static final String MYMOCK_CALL = "mymock-call";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    request.setAttribute(MYMOCK_CALL, createReMockCall(request, response));
    return true;
  }

  private ReMockCall createReMockCall(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    Map<String, String> requestHeaders = Collections.list(request.getHeaderNames())
        .stream()
        .collect(Collectors.toMap(h -> h, request::getHeader));
    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
    var remockRequest = aReMockRequest()
        .withHost(request.getRemoteHost() != null ? request.getRemoteHost() : request.getRemoteAddr())
        .withMethod(request.getMethod() != null ? request.getMethod() : "")
        .withPath(request.getContextPath() != null ? request.getContextPath() : "")
        .withQuery(request.getQueryString() != null ? request.getQueryString() : "")
        .withAccept(request.getHeader("Accept") == null ? "" : request.getHeader("Accept"))
        .withContentType(request.getContentType() != null ? request.getContentType() : "")
        .withHeaders(requestHeaders)
        .withBody(requestWrapper.getReader().lines().collect(Collectors.joining()))
        .build();



    Map<String, String> responseHeaders = response.getHeaderNames()
        .stream()
        .collect(Collectors.toMap(h -> h, request::getHeader));
    var remockResponse = aReMockResponse()
        .withStatus(response.getStatus())
        .withHeaders(responseHeaders)
        .withBody("")
        .withContentType(response.getContentType() != null ? response.getContentType() : "")
        .build();

    return new ReMockCall(remockRequest, remockResponse);
  }

}
