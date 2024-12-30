package com.remock.spring;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;

import com.remock.core.CallStorage;
import com.remock.core.ReMockCall;
import com.remock.core.ReMockRequest;
import com.remock.core.ReMockResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class RemockFilter extends OncePerRequestFilter {

  private final CallStorage callStorage;
  private final String pathsToIntercept;
  private final String pathsToIgnore;
  private final List<String> includeHeaders;

  public RemockFilter(CallStorage callStorage, String pathsToIntercept,
      String pathsToIgnore,
      List<String> includeHeaders) {
    this.callStorage = callStorage;
    this.pathsToIntercept = pathsToIntercept;
    this.pathsToIgnore = pathsToIgnore;
    this.includeHeaders = includeHeaders;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getRequestURI().contains(pathsToIgnore)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getRequestURI().contains(pathsToIntercept)) {
      var requestWrapper = new ContentCachingRequestWrapper(request);
      var responseWrapper = new ContentCachingResponseWrapper(response);
      filterChain.doFilter(requestWrapper, responseWrapper);

      callStorage.add(createReMockCall(requestWrapper, responseWrapper));
      responseWrapper.copyBodyToResponse();
      return;
    }

    filterChain.doFilter(request, response);
  }

  private ReMockCall createReMockCall(ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response) {

    var remockRequest = getReMockRequest(request);
    var remockResponse = getReMockResponse(response);

    return new ReMockCall(remockRequest, remockResponse);
  }

  private ReMockRequest getReMockRequest(ContentCachingRequestWrapper request) {
    Map<String, String> requestHeaders = Collections.list(request.getHeaderNames())
        .stream()
        .filter(h -> includeHeaders.isEmpty() || includeHeaders.contains(h))
        .collect(Collectors.toMap(h -> h, request::getHeader));

    return aReMockRequest()
        .withHost(
            request.getRemoteHost() != null ? request.getRemoteHost() : request.getRemoteAddr())
        .withMethod(request.getMethod() != null ? request.getMethod() : "")
        .withPath(request.getRequestURI() != null ? request.getRequestURI() : "")
        .withQuery(request.getQueryString() != null ? request.getQueryString() : "")
        .withContentType(request.getContentType() != null ? request.getContentType() : "")
        .withHeaders(requestHeaders)
        .withBody(request.getContentAsString())
        .build();
  }

  private ReMockResponse getReMockResponse(ContentCachingResponseWrapper response) {
    Map<String, String> responseHeaders = !response.getHeaderNames().isEmpty() ? response.getHeaderNames()
        .stream()
        .filter(h -> includeHeaders.isEmpty() || includeHeaders.contains(h))
        .collect(Collectors.toMap(h -> h, response::getHeader)) : Collections.emptyMap();

    return aReMockResponse()
        .withStatus(response.getStatus())
        .withHeaders(responseHeaders)
        .withBody(new String(response.getContentAsByteArray()))
        .withContentType(response.getContentType() != null ? response.getContentType() : "")
        .build();
  }
}
