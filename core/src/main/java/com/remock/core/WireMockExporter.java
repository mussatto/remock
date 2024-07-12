package com.remock.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WireMockExporter {

  private final CallStorage callStorage;
  private final ObjectMapper mapper;

  public WireMockExporter(CallStorage callStorage, ObjectMapper mapper) {
    this.callStorage = callStorage;
    this.mapper = mapper;
  }
  public WireMockExporter(CallStorage callStorage) {
    this.callStorage = callStorage;
    this.mapper = new ObjectMapper();
  }

  public String exportJson() {
    ReMockCallList callList = callStorage.getCallList();

    try {
      return mapper.writeValueAsString(callList);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

}
