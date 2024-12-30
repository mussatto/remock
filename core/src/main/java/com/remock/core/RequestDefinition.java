package com.remock.core;

import java.util.List;
import java.util.Map;

public record RequestDefinition(String method,
                                String url,
                                Map<String, String> queryParameters,
                                Map<String, ResponseDefinitionHeader> headers,
                                List<BodyPattern> bodyPatterns){

}

