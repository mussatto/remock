package com.remock.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReMockCallsPerHostMethod implements CallStorage {

  private final int maxPerHost;
  private final int maxPerEndpoint;

  public ReMockCallsPerHostMethod(int maxPerHost, int maxPerEndpoint) {
    this.maxPerHost = maxPerHost;
    this.maxPerEndpoint = maxPerEndpoint;
  }

  public ReMockCallsPerHostMethod() {
    this.maxPerHost = 5;
    this.maxPerEndpoint = 5;
  }

  private final Map<String, Map<HostPathKey, List<ReMockCall>>> perHostEvents = new HashMap<>();

  @Override
  public void add(ReMockCall call) {

    Map<HostPathKey, List<ReMockCall>> calls = perHostEvents.get(call.getRequest().getHost());
    if (calls == null) {
      if (perHostEvents.size() > maxPerEndpoint) {
        return;
      }

      calls = new HashMap<>();
      perHostEvents.put(call.getRequest().getHost(), calls);
    }
    var key = new HostPathKey(call.getRequest().getHost(), call.getRequest().getPath(),
        call.getRequest().getMethod());
    if (calls.size() > maxPerHost || (calls.get(key) != null
        && calls.get(key).size() >= maxPerEndpoint)) {
      return;
    }

    calls.computeIfAbsent(key, k -> new ArrayList<>()).add(call);

  }

  public Map<String, Map<HostPathKey, List<ReMockCall>>> perHostEvents() {
    return perHostEvents;
  }

  @Override
  public ReMockCallList getCallList() {
    ReMockCallList callList = new ReMockCallList(new ArrayList<>());
    perHostEvents().values().stream().map(Map::values)
        .forEach(it -> it.forEach(callList.mappings()::addAll));
    return callList;
  }

  @Override
  public void clear() {
    perHostEvents.clear();
  }

  public record HostPathKey(String host, String path, String httpMethod) {

  }
}
