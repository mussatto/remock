package com.remock.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReMockCallsPerHost {

  private final int maxPerHost;

  public ReMockCallsPerHost(int maxPerHost) {
    this.maxPerHost = maxPerHost;
  }

  public ReMockCallsPerHost() {
    this.maxPerHost = 5;
  }

  private static final Map<String, List<ReMockCall>> perHostEvents = new HashMap<>();

  public void add(ReMockCall call) {
    List<ReMockCall> calls = perHostEvents.computeIfAbsent(call.getRequest().getHost(),
        k -> new ArrayList<>());

    calls.add(call);
    if (calls.size() > maxPerHost) {
      calls.removeFirst();
    }
  }

  public Map<String, List<ReMockCall>> perHostEvents() {
    return perHostEvents;
  }
}
