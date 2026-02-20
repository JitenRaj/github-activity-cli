package com.githubactivity.cache;

import com.githubactivity.model.GithubEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActivityCache {
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final int CACHE_DURATION_MINUTES = 5;

    public List<GithubEvent> get(String username) {
        CacheEntry entry = cache.get(username);
        if (entry != null && Instant.now().isBefore(entry.expirationTime)) {
            return entry.events;
        }
        cache.remove(username); // Clear expired
        return null;
    }

    public void put(String username, List<GithubEvent> events) {
        cache.put(username, new CacheEntry(events, Instant.now().plus(CACHE_DURATION_MINUTES, ChronoUnit.MINUTES)));
    }

    private record CacheEntry(List<GithubEvent> events, Instant expirationTime) {}
}