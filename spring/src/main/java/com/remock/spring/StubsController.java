package com.remock.spring;

import com.remock.core.WireMockExporter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StubsController {

  private final WireMockExporter wireMockExporter;

  public StubsController(WireMockExporter wireMockExporter) {
    this.wireMockExporter = wireMockExporter;
  }

  @GetMapping("/remock/stubs")
  public String getStubs() {
    return String.join("\n", wireMockExporter.exportJson());
  }
}
