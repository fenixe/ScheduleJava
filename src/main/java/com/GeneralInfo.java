package com;

import org.codehaus.jackson.annotate.*;

@JsonIgnoreProperties(ignoreUnknown = true)

public class GeneralInfo {
    @JsonProperty
    private String day;
    @JsonProperty
    private String lessonId;
    @JsonProperty
    private boolean isSet;
}
