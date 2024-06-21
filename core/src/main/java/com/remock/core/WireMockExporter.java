package com.remock.core;

public class WireMockExporter {
  private final ReMockPerHostStore perHostStore;

  public WireMockExporter(ReMockPerHostStore perHostStore) {
    this.perHostStore = perHostStore;
  }

  public String exportJava() {
    StringBuilder sb = new StringBuilder();
    perHostStore.perHostEvents().forEach((host, calls) -> {
      calls.forEach(call -> {
        sb.append("stubFor(");
        sb.append("  get(urlEqualTo(\"" + call.request().path() + "\")");
        sb.append("    .willReturn(aResponse()");
        sb.append("      .withStatus(" + call.response().status() + ")");
        sb.append("      .withHeader(\"Content-Type\", \"" + call.response().contentType() + "\")");
        call.response().headers().forEach((key, value) -> {
          sb.append("      .withHeader(\"" + key + "\", \"" + value + "\")");
        });
        sb.append("      .withBody(\"" + call.response().body() + "\")");
        sb.append("    )");
        sb.append(");");
      });
    });
    return sb.toString();
  }

  public String exportJson() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    perHostStore.perHostEvents().forEach((host, calls) -> {
      sb.append("  \"" + host + "\": [\n");
      calls.forEach(call -> {
        sb.append("    {\n");
        sb.append("      \"request\": {\n");
        sb.append("        \"url\": \"" + call.request().path() + "\",\n");
        sb.append("        \"method\": \"" + call.request().method() + "\",\n");
        sb.append("        \"body\": \"" + call.request().body() + "\",\n");
        sb.append("        \"contentType\": \"" + call.request().contentType() + "\",\n");
        sb.append("        \"accept\": \"" + call.request().accept() + "\",\n");
        sb.append("        \"headers\": {\n");
        call.request().headers().forEach((key, value) -> {
          sb.append("          \"" + key + "\": \"" + value + "\",\n");
        });
        sb.append("        },\n");
        sb.append("        \"query\": \"" + call.request().query() + "\"\n");
        sb.append("      },\n");
        sb.append("      \"response\": {\n");
        sb.append("        \"status\": " + call.response().status() + ",\n");
        sb.append("        \"body\": \"" + call.response().body() + "\",\n");
        sb.append("        \"contentType\": \"" + call.response().contentType() + "\",\n");
        sb.append("        \"headers\": {\n");
        call.response().headers().forEach((key, value) -> {
          sb.append("          \"" + key + "\": \"" + value + "\",\n");
        });
        sb.append("        }\n");
        sb.append("      }\n");
        sb.append("    },\n");
      });
      sb.append("  ]\n");
    });
    sb.append("}\n");
    return sb.toString();
  }

}
