package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;

import java.util.List;

public abstract class BaseActivityCommand implements GithubCommand {
    protected final GithubActivityService service;

    protected BaseActivityCommand(GithubActivityService service) {
        this.service = service;
    }

    @Override
    public void execute(String username) {
        List<GithubEvent> events = service.getRawEvents(username);
        if (events.isEmpty()) {
            System.out.println("No activity found.");
            return;
        }

        long count = events.stream()
                .filter(this::matchesCondition)
                .peek(event -> System.out.println(formatOutput(event)))
                .count();

        if (count == 0) {
            System.out.println("No matching '" + getName() + "' events found for this user.");
        }
    }

    // abstract methods
    protected abstract boolean matchesCondition(GithubEvent event);
    protected abstract String formatOutput(GithubEvent event);
}
