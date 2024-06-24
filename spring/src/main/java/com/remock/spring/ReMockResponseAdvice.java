package com.remock.spring;

import static com.remock.spring.ControllerInterceptor.MYMOCK_CALL;

import com.remock.core.ReMockCall;
import com.remock.core.ReMockCallsPerHost;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ReMockResponseAdvice implements ResponseBodyAdvice<Object> {

  public ReMockResponseAdvice(ReMockCallsPerHost reMockPerHostStore) {
    this.reMockPerHostStore = reMockPerHostStore;
  }

  private final ReMockCallsPerHost reMockPerHostStore;

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {

    if (request instanceof ServletServerHttpRequest servletServerHttpRequest) {
      ReMockCall call = (ReMockCall) servletServerHttpRequest.getServletRequest()
          .getAttribute(MYMOCK_CALL);
      call.getResponse().setBody(body.toString());
      reMockPerHostStore.add(call);
    }

    return body;
  }

}
