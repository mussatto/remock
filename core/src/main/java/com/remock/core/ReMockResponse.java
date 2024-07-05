package com.remock.core;

import java.util.Map;

public class ReMockResponse {

  private String body;
  private String contentType;
  private Map<String, String> headers;

  public ReMockResponse() {
    this.body = null;
    this.contentType = null;
    this.headers = null;
  }

  public ReMockResponse(String body, String contentType, Map<String, String> headers, int status) {
    if (body == null || contentType == null || headers == null) {
      throw new IllegalArgumentException("All fields must be non-null");
    }
    this.body = body;
    this.contentType = contentType;
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
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