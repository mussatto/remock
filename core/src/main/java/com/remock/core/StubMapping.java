package com.remock.core;

public class StubMapping {
  private final RequestDefinition request;
  private final ResponseDefinition response;

  public StubMapping(RequestDefinition request, ResponseDefinition response){
    this.request = request;
    this.response = response;
  }

  public RequestDefinition getRequest() {
    return request;
  }

  public ResponseDefinition getResponse() {
    return response;
  }

}
