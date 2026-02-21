package com.githubactivity.service;

import com.githubactivity.model.GithubEvent;

import java.util.List;

public interface GithubActivityService {
    List<GithubEvent> getRawEvents(String username);
}