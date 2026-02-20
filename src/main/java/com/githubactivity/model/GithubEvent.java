package com.githubactivity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubEvent(
        String type,
        Repo repo,
        Payload payload
) {}
