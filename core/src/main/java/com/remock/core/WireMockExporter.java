package com.remock.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class WireMockExporter {

  private final ReMockPerHostStore perHostStore;
  private final ObjectMapper mapper;

  public WireMockExporter(ReMockPerHostStore perHostStore, ObjectMapper mapper) {
    this.perHostStore = perHostStore;
    this.mapper = mapper;
  }
  public WireMockExporter(ReMockPerHostStore perHostStore) {
    this.perHostStore = perHostStore;
    this.mapper = new ObjectMapper();
  }

  public List<String> exportJava() {
    List<String> stubs = new ArrayList<>();
    perHostStore.perHostEvents().forEach((host, calls) -> {
      calls.forEach(call -> {
        StringBuilder sb = new StringBuilder();
        sb.append("stubFor(");
        sb.append("  get(urlEqualTo(\"" + call.request().path() + "\")");
        sb.append("    .willReturn(aResponse()");
        sb.append("      .withStatus(" + call.response().status() + ")");
        sb.append("      .withHeader(\"Content-Type\", \"" + call.response().contentType() + "\")");
        call.response().headers().forEach((key, value) -> {
          sb.append("      .withHeader(\"" + key + "\", \"" + value + "\")");
        });
        sb.append("      .withBody(\"" + call.response().body() + "\")");
        sb.append("    )");
        sb.append(");");
        stubs.add(toString());
      });
    });
    return stubs;
  }

  public List<String> exportJson() {
    List<String> stubs = new ArrayList<>();
    perHostStore.perHostEvents().forEach((host, calls) -> {
      stubs.add(toJson(host, calls));
    });

    return stubs;
  }

  private String toJson(String host, List<ReMockCall> calls) {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    sb.append("  \"mappings\": [\n");
    for (ReMockCall call : calls) {
      try {
        sb.append(mapper.writeValueAsString(call));
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    sb.append("  ]\n");
    sb.append("}\n");
    return sb.toString();
  }

}
