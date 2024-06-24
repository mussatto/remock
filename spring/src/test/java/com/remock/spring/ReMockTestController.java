package com.remock.spring;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReMockTestController {

  @PostMapping(value = "/api/my-endpoint",
      produces = "application/json")
  public Map<String, String> myEndpoint(@RequestBody Map<String, String> params) {
    return Map.of("response", "Hello, " + params.get("myParam"));
  }
}
