package com.remock.core;

import java.util.Map;

public class ReMockRequest{

    private String host;
    private String path;
    private String method;
    private String body;
    private String contentType;
    private String accept;
    private Map<String, String> headers;
    private String query;

    public ReMockRequest(String host, String path, String method, String body, String contentType, String accept, Map<String, String> headers, String query)  {
        if (host == null || path == null || method == null || body == null || contentType == null || accept == null || headers == null || query == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
        this.host = host;
        this.path = path;
        this.method = method;
        this.body = body;
        this.contentType = contentType;
        this.accept = accept;
        this.headers = headers;
        this.query = query;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public static final class ReMockRequestBuilder {

        private String host;
        private String path;
        private String method;
        private String body;
        private String contentType;
        private String accept;
        private Map<String, String> headers;
        private String query;

        private ReMockRequestBuilder() {
        }

        public static ReMockRequestBuilder aReMockRequest() {
            return new ReMockRequestBuilder();
        }

        public ReMockRequestBuilder withHost(String host) {
            this.host = host;
            return this;
        }

        public ReMockRequestBuilder withPath(String path) {
            this.path = path;
            return this;
        }

        public ReMockRequestBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

        public ReMockRequestBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public ReMockRequestBuilder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public ReMockRequestBuilder withAccept(String accept) {
            this.accept = accept;
            return this;
        }

        public ReMockRequestBuilder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public ReMockRequestBuilder withQuery(String query) {
            this.query = query;
            return this;
        }

        public ReMockRequest build() {
            return new ReMockRequest(host, path, method, body, contentType, accept, headers, query);
        }
    }
}

