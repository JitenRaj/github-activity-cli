package com.githubactivity.service;

import com.githubactivity.adapter.GithubApiAdapter;
import com.githubactivity.cache.ActivityCache;
import com.githubactivity.model.GithubEvent;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GithubActivityServiceImpl implements GithubActivityService {

    private final GithubApiAdapter apiAdapter;
    private final ActivityCache cache;

    public GithubActivityServiceImpl(GithubApiAdapter apiAdapter, ActivityCache cache) {
        this.apiAdapter = apiAdapter;
        this.cache = cache;
    }

    @Override
    public List<GithubEvent> getRawEvents(String username) {
        try {
            List<GithubEvent> events = cache.get(username);
            if (events == null) {
                events = apiAdapter.fetchUserActivity(username);
                cache.put(username, events);
            }
            return events != null ? events : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error fetching events: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}