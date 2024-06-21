package com.remock.core;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ReMockPerHostStoreTest {

  @Test
  void add() {
    ReMockPerHostStore store = new ReMockPerHostStore();
    ReMockCall call = new ReMockCall(
        aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
        aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
    );
    store.add(call);
    assertEquals(1, store.perHostEvents().size());
    assertEquals(1, store.perHostEvents().get("example.com").size());
  }

  @Test
  void addMoreThan5(){
    ReMockPerHostStore store = new ReMockPerHostStore();
    for (int i = 0; i < 10; i++) {
      ReMockCall call = new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      );
      store.add(call);
    }
    assertEquals(5, store.perHostEvents().get("example.com").size());
  }
}