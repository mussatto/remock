package com.remock.spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({StubsController.class, ReMockTestController.class})
@ContextConfiguration(classes = {ReMockConfiguration.class, ReMockTestConfiguration.class})
public class StubsControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetStubs() throws Exception {

    mockMvc.perform(post("/api/my-endpoint").content("{ \"myParam\" : \"World\" }").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("{\"response\":\"Hello, World\"}"));

    mockMvc.perform(get("/remock/stubs"))
        .andExpect(status().isOk())
        .andExpect(content().string("[{\"request\":{\"url\":\"/my-endpoint\"},\"response\":{\"status\":200,\"body\":\"Hello, World!\"}}]"));
  }

}