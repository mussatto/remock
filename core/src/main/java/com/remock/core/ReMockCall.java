package com.remock.core;

public record ReMockCall(ReMockRequest request, ReMockResponse response) {
    public ReMockCall {
        if (request == null || response == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
    }
}
