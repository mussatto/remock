package com.remock.core;

import static com.remock.core.ReMockRequest.ReMockRequestBuilder.aReMockRequest;
import static com.remock.core.ReMockResponse.ReMockResponseBuilder.aReMockResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.remock.core.ReMockCallsPerHostMethod.HostPathKey;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReMockPerHostStoreMethodTest {

  @Test
  void add() {
    ReMockCallsPerHostMethod store = new ReMockCallsPerHostMethod();
    ReMockCall call = new ReMockCall(
        aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
        aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
    );
    store.add(call);
    assertEquals(1, store.perHostEvents().size());
    assertEquals(1, store.perHostEvents().get("example.com").size());
    assertEquals(1, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/", "GET")).size());
  }

  @Test
  void addMoreThan5(){
    ReMockCallsPerHostMethod store = new ReMockCallsPerHostMethod();
    for (int i = 0; i < 10; i++) {
      ReMockCall call = new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      );

      ReMockCall call2 = new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      );
      store.add(call);
      store.add(call2);
    }

    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/", "GET")).size());
    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/other", "POST")).size());
    assertEquals(1, store.perHostEvents().size());
  }

  @Test
  void addMoreThan5TwoHosts(){
    ReMockCallsPerHostMethod store = new ReMockCallsPerHostMethod();
    for (int i = 0; i < 10; i++) {

      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
    }

    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/", "GET")).size());
    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/other", "POST")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/", "GET")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/other", "POST")).size());
    assertEquals(2, store.perHostEvents().size());
  }

  @Test
  void addMoreThan5TwoHostsSamePathDifferentMethod(){
    ReMockCallsPerHostMethod store = new ReMockCallsPerHostMethod();
    for (int i = 0; i < 10; i++) {

      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/").withMethod("POST").withBody("body").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/other").withMethod("GET").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.com").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/").withMethod("POST").withBody("").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/other").withMethod("GET").withBody("body").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));
      store.add(new ReMockCall(
          aReMockRequest().withHost("example.org").withPath("/other").withMethod("POST").withBody("body").withContentType("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
          aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(Map.of("header", "value")).withStatus(200).build()
      ));

    }

    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/", "GET")).size());
    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/", "POST")).size());
    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/other", "GET")).size());
    assertEquals(5, store.perHostEvents().get("example.com").get(new HostPathKey("example.com", "/other", "POST")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/", "GET")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/", "POST")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/other", "GET")).size());
    assertEquals(5, store.perHostEvents().get("example.org").get(new HostPathKey("example.org", "/other", "POST")).size());
    assertEquals(2, store.perHostEvents().size());
  }
}