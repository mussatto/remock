package com.remock.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class WireMockExporter {

  private final ReMockCallsPerHost perHostStore;
  private final ObjectMapper mapper;

  public WireMockExporter(ReMockCallsPerHost perHostStore, ObjectMapper mapper) {
    this.perHostStore = perHostStore;
    this.mapper = mapper;
  }
  public WireMockExporter(ReMockCallsPerHost perHostStore) {
    this.perHostStore = perHostStore;
    this.mapper = new ObjectMapper();
  }

  public List<String> exportJson() {
    List<String> stubs = new ArrayList<>();
    perHostStore.perHostEvents().values().forEach((calls) -> {
      stubs.add(toJson(calls));
    });

    return stubs;
  }

  private String toJson(List<ReMockCall> calls) {
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
