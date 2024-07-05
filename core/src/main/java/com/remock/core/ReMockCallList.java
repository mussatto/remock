package com.remock.core;

import java.util.List;

/**
 * A list of ReMockCall objects.
 * used to export to wiremock json format.
 * @param mappings
 */
public record ReMockCallList(List<ReMockCall> mappings) {

}
