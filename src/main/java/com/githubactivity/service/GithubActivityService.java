package com.githubactivity.service;

import java.util.List;

public interface GithubActivityService {
    List<String> getRecentActivity(String username, String eventTypeFilter);
}