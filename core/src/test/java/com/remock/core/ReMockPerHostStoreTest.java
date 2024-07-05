package com.remock.core;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;
import static org.junit.jupiter.api.Assertions.*;

import com.remock.core.ReMockCallsPerHost.HostPathKey;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReMockPerHostStoreTest {

  @Test
  void add() {
    ReMockCallsPerHost store = new ReMockCallsPerHost();
    ReMockCall call = new ReMockCall(
        aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
        aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
    );
    store.add(call);
    assertEquals(1, store.perHostEvents().size());
    assertEquals(1, store.perHostEvents().get("example.com").size());
    assertEquals(1, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/")).size());
  }

  @Test
  void addMoreThan5(){
    ReMockCallsPerHost store = new ReMockCallsPerHost();
    for (int i = 0; i < 10; i++) {
      ReMockCall call = new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      );

      ReMockCall call2 = new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      );
      store.add(call);
      store.add(call2);
    }

    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/")).size());
    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/other")).size());
    assertEquals(1, store.perHostEvents().size());
  }

  @Test
  void addMoreThan5TwoHosts(){
    ReMockCallsPerHost store = new ReMockCallsPerHost();
    for (int i = 0; i < 10; i++) {

      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
    }

    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/")).size());
    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/other")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/other")).size());
    assertEquals(2, store.perHostEvents().size());
  }
}