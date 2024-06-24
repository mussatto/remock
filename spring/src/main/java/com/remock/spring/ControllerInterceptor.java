package com.remock.spring;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;

import com.remock.core.ReMockCall;
import com.remock.core.ReMockCallsPerHost;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.servlet.HandlerInterceptor;

public class ControllerInterceptor implements HandlerInterceptor {

  public static final String MYMOCK_CALL = "mymock-call";
  private final ReMockCallsPerHost reMockPerHostStore;

  public ControllerInterceptor(ReMockCallsPerHost reMockPerHostStore) {
    this.reMockPerHostStore = reMockPerHostStore;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    reMockPerHostStore.add(createReMockCall(request, response));
    return true;
  }

  private ReMockCall createReMockCall(HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {

    Map<String, String> requestHeaders = Collections.list(request.getHeaderNames())
        .stream()
        .collect(Collectors.toMap(h -> h, request::getHeader));

    var remockRequest = aReMockRequest()
        .withHost(
            request.getRemoteHost() != null ? request.getRemoteHost() : request.getRemoteAddr())
        .withMethod(request.getMethod() != null ? request.getMethod() : "")
        .withPath(request.getRequestURI() != null ? request.getRequestURI() : "")
        .withQuery(request.getQueryString() != null ? request.getQueryString() : "")
        .withAccept(request.getHeader("Accept") == null ? "" : request.getHeader("Accept"))
        .withContentType(request.getContentType() != null ? request.getContentType() : "")
        .withHeaders(requestHeaders)
        .withBody("")
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
