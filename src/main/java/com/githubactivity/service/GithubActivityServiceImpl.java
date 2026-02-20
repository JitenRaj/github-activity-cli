package com.githubactivity.service;

import com.githubactivity.adapter.GithubApiAdapter;
import com.githubactivity.cache.ActivityCache;
import com.githubactivity.model.GithubEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubActivityServiceImpl implements GithubActivityService {

    private final GithubApiAdapter apiAdapter;
    private final ActivityCache cache;

    public GithubActivityServiceImpl(GithubApiAdapter apiAdapter, ActivityCache cache) {
        this.apiAdapter = apiAdapter;
        this.cache = cache;
    }

    @Override
    public List<String> getRecentActivity(String username, String eventTypeFilter) {
        try {
            List<GithubEvent> events = cache.get(username);

            if (events == null) {
                events = apiAdapter.fetchUserActivity(username);
                cache.put(username, events);
            }

            if (events.isEmpty()) {
                return List.of("- No recent public activity found.");
            }

            return events.stream()
                    .filter(event -> eventTypeFilter == null || event.type().equalsIgnoreCase(eventTypeFilter))
                    .map(this::formatEvent)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException | IllegalStateException e) {
            return List.of("- Error: " + e.getMessage());
        } catch (Exception e) {
            return List.of("- Unexpected error occurred: " + e.getMessage());
        }
    }

    private String formatEvent(GithubEvent event) {
        String repoName = event.repo() != null ? event.repo().name() : "unknown repository";

        return switch (event.type()) {
            case "PushEvent" -> {
                int commits = (event.payload() != null && event.payload().size() != null) ? event.payload().size() : 0;
                yield "- Pushed " + commits + " commit(s) to " + repoName;
            }
            case "IssuesEvent" -> {
                String action = event.payload() != null ? event.payload().action() : "interacted with";
                yield "- " + capitalize(action) + " an issue in " + repoName;
            }
            case "WatchEvent" -> "- Starred " + repoName;
            case "CreateEvent" -> {
                String refType = event.payload() != null ? event.payload().refType() : "resource";
                yield "- Created a " + refType + " in " + repoName;
            }
            case "ForkEvent" -> "- Forked " + repoName;
            case "PullRequestEvent" -> {
                String action = event.payload() != null ? event.payload().action() : "interacted with";
                yield "- " + capitalize(action) + " a pull request in " + repoName;
            }
            default -> "- " + event.type().replace("Event", "") + " event on " + repoName;
        };
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}