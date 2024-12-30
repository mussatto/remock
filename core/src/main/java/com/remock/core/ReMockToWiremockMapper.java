package com.remock.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ReMockToWiremockMapper {

  private final ReMockCallList reMockCallList;

  public ReMockToWiremockMapper(ReMockCallList reMockCallList) {
    super();
    this.reMockCallList = reMockCallList;
  }

  public List<StubMapping> map() {
    List<StubMapping> stubMappings = new ArrayList<>();

    for(ReMockCall reMockCall : reMockCallList.mappings()) {

      RequestDefinition request = getRequestDefinition(reMockCall.getRequest());

      ResponseDefinition response = new ResponseDefinition(reMockCall.getResponse().getStatus(),
          reMockCall.getResponse().getBody(),
          reMockCall.getResponse().getHeaders());

      stubMappings.add(new StubMapping(request, response));
    }

    return stubMappings;
  }

  private static RequestDefinition getRequestDefinition(ReMockRequest request) {
    var headers = new HashMap<String, ResponseDefinitionHeader>();

    for(Entry<String, String> header: request.getHeaders().entrySet()){
      headers.put(header.getKey(), new ResponseDefinitionHeader(header.getValue()));
    }

    var bodyPattern = request.getBody() == null || request.getBody().isEmpty() ? null :
        List.of(new BodyPattern(request.getBody(), true, true));

    return new RequestDefinition(
      request.getMethod(),
      request.getUrl(),
      new HashMap<>(),
      headers,
      bodyPattern
    );
  }

}
