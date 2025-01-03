package com.remock.core;

import java.util.Map;

public class ReMockRequest{

    private String host;
    private String url;
    private String method;
    private String body;
    private String contentType;
    private Map<String, String> headers;
    private String query;

    public ReMockRequest() {
        this.host = null;
        this.url = null;
        this.method = null;
        this.body = null;
        this.contentType = null;
        this.headers = null;
        this.query = null;
    }
    public ReMockRequest(String host, String url, String method, String body, String contentType, Map<String, String> headers, String query)  {
        if (host == null || url == null || method == null || body == null || contentType == null || headers == null || query == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
        this.host = host;
        this.url = url;
        this.method = method;
        this.body = body;
        this.contentType = contentType;
        this.headers = headers;
        this.query = query;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        public ReMockRequestBuilder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public ReMockRequestBuilder withQuery(String query) {
            this.query = query;
            return this;
        }

        public ReMockRequest build() {
            return new ReMockRequest(host, path, method, body, contentType, headers, query);
        }
    }
}

