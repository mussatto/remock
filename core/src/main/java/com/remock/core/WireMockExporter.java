package com.remock.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public ByteArrayOutputStream exportJsonZip() {
    var stubs = new ReMockToWiremockMapper(callStorage.getCallList()).map();

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try(ZipOutputStream zos = new ZipOutputStream(byteArrayOutputStream)) {

      for(StubMapping stub : stubs) {
        try {
          String json = mapper.writeValueAsString(stub);
          var zipEntry = new ZipEntry(UUID.randomUUID() + ".json");
          zos.putNextEntry(zipEntry);
          zos.write(json.getBytes());
          zos.closeEntry();
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return byteArrayOutputStream;
  }

}
