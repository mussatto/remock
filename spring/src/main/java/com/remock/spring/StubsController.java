package com.remock.spring;

import com.remock.core.WireMockExporter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StubsController {

  private final WireMockExporter wireMockExporter;

  public StubsController(WireMockExporter wireMockExporter) {
    this.wireMockExporter = wireMockExporter;
  }

  @GetMapping("/remock/stubs")
  public ResponseEntity<byte[]> getStubs() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("filename", "stubs.zip");
    return new ResponseEntity<>(wireMockExporter.exportJsonZip().toByteArray(), headers, HttpStatus.OK);
  }

}
