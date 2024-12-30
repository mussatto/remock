package com.remock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @GetMapping("/api/sample")
  public String getSample() {
    return "sample";
  }

  @PostMapping("/api/sample")
  public String postSample(@RequestBody String body) {
    return "sample " + body;
  }

}
