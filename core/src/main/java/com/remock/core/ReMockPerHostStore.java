package com.remock.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReMockPerHostStore {

  private final int MAX_PER_HOST = 5;

  private final Map<String, List<ReMockCall>> perHostEvents = new HashMap<>();

  public void add(ReMockCall call) {
    List<ReMockCall> calls = perHostEvents.computeIfAbsent(call.request().host(),
        k -> new ArrayList<>());

    calls.add(call);
    if (calls.size() > MAX_PER_HOST) {
      calls.removeFirst();
    }
  }

  public Map<String, List<ReMockCall>> perHostEvents() {
    return perHostEvents;
  }
}
