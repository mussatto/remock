package com.remock.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class WireMockExporterTest {

//  @Test
//  void exportJsonZipEmpty() {
//    ReMockCallsPerHost perHostStore = new ReMockCallsPerHost();
//    WireMockExporter exporter = new WireMockExporter(perHostStore);
//    String json = exporter.exportJsonZip();
//    assertThat(json).isEqualTo("{\"mappings\":[]}");
//  }
//
//  @Test
//  void exportJsonZipOneHost(){
//    ReMockCallsPerHost perHostStore = new ReMockCallsPerHost();
//    ReMockCall call = new ReMockCall(
//        ReMockRequest.ReMockRequestBuilder.aReMockRequest().withHost("example.com").withPath("/path").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
//        ReMockResponse.ReMockResponseBuilder.aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(
//            Map.of("header", "value")).withStatus(200).build()
//    );
//    perHostStore.add(call);
//    WireMockExporter exporter = new WireMockExporter(perHostStore);
//    String json = exporter.exportJsonZip();
//
//    assertThat(json).contains("example.com");
//    assertThat(json).isEqualTo("{\"mappings\":[{\"request\":{\"host\":\"example.com\",\"path\":\"/path\",\"method\":\"GET\",\"body\":\"\",\"contentType\":\"text/plain\",\"accept\":\"text/plain\",\"headers\":{\"header\":\"value\"},\"query\":\"any\"},\"response\":{\"body\":\"any\",\"contentType\":\"text/plain\",\"headers\":{\"header\":\"value\"}}}]}");
//  }
//
//  @Test
//  void exportJsonZipTwoHosts(){
//    ReMockCallsPerHost perHostStore = new ReMockCallsPerHost();
//    ReMockCall call1 = new ReMockCall(
//        ReMockRequest.ReMockRequestBuilder.aReMockRequest().withHost("example.com").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
//        ReMockResponse.ReMockResponseBuilder.aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(
//            Map.of("header", "value")).withStatus(200).build()
//    );
//    ReMockCall call2 = new ReMockCall(
//        ReMockRequest.ReMockRequestBuilder.aReMockRequest().withHost("example.org").withPath("/").withMethod("GET").withBody("").withContentType("text/plain").withAccept("text/plain").withHeaders(Map.of("header", "value")).withQuery("any").build(),
//        ReMockResponse.ReMockResponseBuilder.aReMockResponse().withBody("any").withContentType("text/plain").withHeaders(
//            Map.of("header", "value")).withStatus(200).build()
//    );
//    perHostStore.add(call1);
//    perHostStore.add(call2);
//    WireMockExporter exporter = new WireMockExporter(perHostStore);
//    String json = exporter.exportJsonZip();
//
//    assertThat(json).contains("example.com");
//    assertThat(json).contains("example.org");
//    assertThat(json).isEqualTo("{\"mappings\":[{\"request\":{\"host\":\"example.org\",\"path\":\"/\",\"method\":\"GET\",\"body\":\"\",\"contentType\":\"text/plain\",\"accept\":\"text/plain\",\"headers\":{\"header\":\"value\"},\"query\":\"any\"},\"response\":{\"body\":\"any\",\"contentType\":\"text/plain\",\"headers\":{\"header\":\"value\"}}},{\"request\":{\"host\":\"example.com\",\"path\":\"/\",\"method\":\"GET\",\"body\":\"\",\"contentType\":\"text/plain\",\"accept\":\"text/plain\",\"headers\":{\"header\":\"value\"},\"query\":\"any\"},\"response\":{\"body\":\"any\",\"contentType\":\"text/plain\",\"headers\":{\"header\":\"value\"}}}]}");
//  }
}