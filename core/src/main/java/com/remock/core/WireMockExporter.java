package com.remock.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  public String exportJson() {
    ReMockCallList callList = new ReMockCallList(new ArrayList<>());
    perHostStore.perHostEvents().values().stream().map(Map::values).forEach(it -> it.forEach(callList.mappings()::addAll));
    try {
      return mapper.writeValueAsString(callList);
    } catch (JsonProcessingException e) {
      return "";
    }
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
