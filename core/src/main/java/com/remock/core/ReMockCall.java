package com.remock.core;

public class ReMockCall {
    private final ReMockRequest request;
    private final ReMockResponse response;
    public ReMockCall(ReMockRequest request, ReMockResponse response) {
        if (request == null || response == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
        this.request = request;
        this.response = response;
    }

    public ReMockRequest getRequest() {
        return request;
    }

    public ReMockResponse getResponse() {
        return response;
    }
}
