package com.remock.core;

import java.util.Map;

public record ReMockResponse(String body, String contentType, Map<String, String> headers, int status) {
  public ReMockResponse {
    if (body == null || contentType == null || headers == null) {
      throw new IllegalArgumentException("All fields must be non-null");
    }
  }

  public static final class ReMockResponseBuilder {

    private String body;
    private String contentType;
    private Map<String, String> headers;
    private int status;

    private ReMockResponseBuilder() {
    }

    public static ReMockResponseBuilder aReMockResponse() {
      return new ReMockResponseBuilder();
    }

    public ReMockResponseBuilder withBody(String body) {
      this.body = body;
      return this;
    }

    public ReMockResponseBuilder withContentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    public ReMockResponseBuilder withHeaders(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public ReMockResponseBuilder withStatus(int status) {
      this.status = status;
      return this;
    }

    public ReMockResponse build() {
      return new ReMockResponse(body, contentType, headers, status);
    }
  }
}