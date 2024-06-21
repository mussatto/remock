package com.remock.core;

import java.util.Map;

public record ReMockRequest(String host, String path, String method, String body, String contentType, String accept, Map<String, String> headers, String query) {
    public ReMockRequest {
        if (host == null || path == null || method == null || body == null || contentType == null || accept == null || headers == null || query == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
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

