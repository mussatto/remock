package com.remock.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class WireMockExporterTest {

  @Test
  void exportJsonEmpty() {
    ReMockPerHostStore perHostStore = new ReMockPerHostStore();
    WireMockExporter exporter = new WireMockExporter(perHostStore);
    List<String> json = exporter.exportJson();
    assertThat(json).isEmpty();
  }

  @Test
  void exportJsonOneHost(){
    ReMockPerHostStore perHostStore = new ReMockPerHostStore();
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
    ReMockPerHostStore perHostStore = new ReMockPerHostStore();
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