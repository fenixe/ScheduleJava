package com.diplom;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)

public class GeneralInfo {
    @JsonProperty
    private String day;
    @JsonProperty
    private String lessonId;
    @JsonProperty
    private boolean isSet;
}
