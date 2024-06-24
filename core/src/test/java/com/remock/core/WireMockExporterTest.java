package com.remock.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class WireMockExporterTest {

  @Test
  void exportJsonEmpty() {
    ReMockCallsPerHost perHostStore = new ReMockCallsPerHost();
    WireMockExporter exporter = new WireMockExporter(perHostStore);
    List<String> json = exporter.exportJson();
    assertThat(json).isEmpty();
  }

  @Test
  void exportJsonOneHost(){
    ReMockCallsPerHost perHostStore = new ReMockCallsPerHost();
    ReMockCall call = new ReMockCall(
        ReMockRequest.ReMockRequestBuilder.aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
        ReMockResponse.ReMockResponseBuilder.aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(
            Map.of("header", "value")).withStatus(200).build()
    );
    perHostStore.add(call);
    WireMockExporter exporter = new WireMockExporter(perHostStore);
    List<String> json = exporter.exportJson();
    assertThat(json).hasSize(1);
    assertThat(json.getFirst()).contains("example.com");
  }

  @Test
  void exportJsonTwoHosts(){
    ReMockCallsPerHost perHostStore = new ReMockCallsPerHost();
    ReMockCall call1 = new ReMockCall(
        ReMockRequest.ReMockRequestBuilder.aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
        ReMockResponse.ReMockResponseBuilder.aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(
            Map.of("header", "value")).withStatus(200).build()
    );
    ReMockCall call2 = new ReMockCall(
        ReMockRequest.ReMockRequestBuilder.aReMockRequest().withHost("example.org").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
        ReMockResponse.ReMockResponseBuilder.aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(
            Map.of("header", "value")).withStatus(200).build()
    );
    perHostStore.add(call1);
    perHostStore.add(call2);
    WireMockExporter exporter = new WireMockExporter(perHostStore);
    List<String> json = exporter.exportJson();
    assertThat(json).hasSize(2);
  }
}