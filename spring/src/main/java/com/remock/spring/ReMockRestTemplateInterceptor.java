package com.remock.spring;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;

import com.remock.core.ReMockCall;
import com.remock.core.ReMockCallsPerHost;
import com.remock.core.ReMockRequest;
import com.remock.core.ReMockResponse;
import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class ReMockRestTemplateInterceptor implements ClientHttpRequestInterceptor {

  private final ReMockCallsPerHost reMockCallsPerHost;

  public ReMockRestTemplateInterceptor(ReMockCallsPerHost reMockCallsPerHost){
    this.reMockCallsPerHost = reMockCallsPerHost;
  }
  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {

    var response = execution.execute(request, body);

    reMockCallsPerHost.add(createReMockCall(request, response, body));
    return response;
  }

  private ReMockCall createReMockCall(HttpRequest request, ClientHttpResponse response, byte[] requestBody)
      throws IOException {
    ReMockRequest remockRequest = aReMockRequest()
        .withPath(request.getURI().getPath())
        .withMethod(request.getMethod().name())
        .withHeaders(request.getHeaders().toSingleValueMap())
        .withQuery(request.getURI().getQuery())
        .withBody(new String(requestBody))
        .build();

    ReMockResponse remockResponse = aReMockResponse()
        .withStatus(response.getStatusCode().value())
        .withHeaders(response.getHeaders().toSingleValueMap())
        .withBody(new String(response.getBody().readAllBytes()))
        .build();

    return new ReMockCall(remockRequest, remockResponse);
  }
}
