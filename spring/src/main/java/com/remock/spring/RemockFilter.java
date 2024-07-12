package com.remock.spring;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;

import com.remock.core.CallStorage;
import com.remock.core.ReMockCall;
import com.remock.core.ReMockCallsPerHost;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class RemockFilter extends OncePerRequestFilter {

  private final CallStorage callStorage;
  private final String pathsToIntercept;
  private final String pathsToIgnore;

  public RemockFilter(CallStorage callStorage, String pathsToIntercept,
      String pathsToIgnore) {
    this.callStorage = callStorage;
    this.pathsToIntercept = pathsToIntercept;
    this.pathsToIgnore = pathsToIgnore;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getRequestURI().contains(pathsToIgnore)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getRequestURI().contains(pathsToIntercept)) {
      ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
      ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
      filterChain.doFilter(requestWrapper, responseWrapper);

      callStorage.add(createReMockCall(requestWrapper, responseWrapper));
      responseWrapper.copyBodyToResponse();
      return;
    }

    filterChain.doFilter(request, response);
  }

  private ReMockCall createReMockCall(ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response) {

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
        .withBody(request.getContentAsString())
        .build();

    Map<String, String> responseHeaders = response.getHeaderNames()
        .stream()
        .collect(Collectors.toMap(h -> h, request::getHeader));
    var remockResponse = aReMockResponse()
        .withStatus(response.getStatus())
        .withHeaders(responseHeaders)
        .withBody(new String(response.getContentAsByteArray()))
        .withContentType(response.getContentType() != null ? response.getContentType() : "")
        .build();

    return new ReMockCall(remockRequest, remockResponse);
  }
}
