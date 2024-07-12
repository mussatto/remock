package com.remock.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.remock.core.CallStorage;
import com.remock.core.ReMockCallList;
import org.junit.jupiter.api.BeforeEach;
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

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CallStorage callStorage;

  @BeforeEach
  public void setup() {
    callStorage.clear();
  }
  @Test
  public void testGetStubs() throws Exception {

    mockMvc.perform(post("/api/my-endpoint").content("{ \"myParam\" : \"World\" }").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("{\"response\":\"Hello, World\"}"));

    mockMvc.perform(get("/remock/stubs"))
        .andExpect(status().isOk())
        .andExpect(content().string("""
            {"mappings":[{"request":{"host":"localhost","path":"/api/my-endpoint","method":"POST","body":"{ \\"myParam\\" : \\"World\\" }","contentType":"application/json","accept":"","headers":{"Content-Length":"23","Content-Type":"application/json;charset=UTF-8"},"query":""},"response":{"body":"{\\"response\\":\\"Hello, World\\"}","contentType":"application/json","headers":{"Content-Type":"application/json;charset=UTF-8"}}}]}"""));

  }

  @Test
  public void testGetMultipleStubs() throws Exception {

    mockMvc.perform(post("/api/my-endpoint").content("{ \"myParam\" : \"World\" }").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("{\"response\":\"Hello, World\"}"));

    mockMvc.perform(post("/api/my-endpoint").content("{ \"myParam\" : \"World 2\" }").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("{\"response\":\"Hello, World 2\"}"));

    mockMvc.perform(post("/api/my-endpoint-2").content("{ \"myParam\" : \"World\" }").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("{\"response\":\"Hello, World\"}"));

    var list = objectMapper.readValue(mockMvc.perform(get("/remock/stubs"))
        .andExpect(status().isOk())
//        .andExpect(content().string("""
//                {"mappings":[{"request":{"host":"localhost","path":"/api/my-endpoint","method":"POST","body":"{ \\"myParam\\" : \\"World\\" }","contentType":"application/json","accept":"","headers":{"Content-Length":"23","Content-Type":"application/json;charset=UTF-8"},"query":""},"response":{"body":"{\\"response\\":\\"Hello, World\\"}","contentType":"application/json","headers":{"Content-Type":"application/json;charset=UTF-8"}}},{"request":{"host":"localhost","path":"/api/my-endpoint","method":"POST","body":"{ \\"myParam\\" : \\"World 2\\" }","contentType":"application/json","accept":"","headers":{"Content-Length":"25","Content-Type":"application/json;charset=UTF-8"},"query":""},"response":{"body":"{\\"response\\":\\"Hello, World 2\\"}","contentType":"application/json","headers":{"Content-Type":"application/json;charset=UTF-8"}}},{"request":{"host":"localhost","path":"/api/my-endpoint-2","method":"POST","body":"{ \\"myParam\\" : \\"World\\" }","contentType":"application/json","accept":"","headers":{"Content-Length":"23","Content-Type":"application/json;charset=UTF-8"},"query":""},"response":{"body":"{\\"response\\":\\"Hello, World\\"}","contentType":"application/json","headers":{"Content-Type":"application/json;charset=UTF-8"}}}]}"""))
        .andReturn().getResponse().getContentAsString(), ReMockCallList.class);

    assertThat(list.mappings()).hasSize(3);
    assertThat(list.mappings().stream().map(it -> it.getRequest().getPath())).containsExactlyInAnyOrder("/api/my-endpoint", "/api/my-endpoint", "/api/my-endpoint-2");
    assertThat(list.mappings().stream().map(it -> it.getResponse().getBody())).containsExactlyInAnyOrder("{\"response\":\"Hello, World\"}", "{\"response\":\"Hello, World 2\"}", "{\"response\":\"Hello, World\"}");

  }

}
