package com.githubactivity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Payload(
        Integer size,
        String action,
        @JsonProperty("ref_type") String refType
) {}