package com.githubactivity.adapter;

import com.githubactivity.model.GithubEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Component
public class GithubApiAdapter {

    private static final String GITHUB_API_URL = "https://api.github.com/users/%s/events";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // LOOK HERE: No arguments inside the parentheses!
    public GithubApiAdapter() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<GithubEvent> fetchUserActivity(String username) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(GITHUB_API_URL, username)))
                .header("Accept", "application/vnd.github.v3+json")
                .header("User-Agent", "Github-Activity-CLI")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new IllegalArgumentException("User '" + username + "' not found on GitHub.");
        } else if (response.statusCode() == 403 || response.statusCode() == 429) {
            throw new IllegalStateException("GitHub API rate limit exceeded. Please try again later.");
        } else if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch data. HTTP Status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), new TypeReference<List<GithubEvent>>() {});
    }
}