package com.remock.core;

import java.util.Map;

public record ResponseDefinition(int status,
                                 String body,
                                 Map<String, String> headers) {

}
