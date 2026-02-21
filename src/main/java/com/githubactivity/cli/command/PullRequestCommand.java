package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class PullRequestCommand extends BaseActivityCommand {

    public PullRequestCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "pr";
    }

    @Override
    public String getDescription() {
        return "Displays all pull request activities.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return "PullRequestEvent".equals(event.type());
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        String action = (event.payload() != null && event.payload().action() != null)
                ? event.payload().action() : "interacted with";
        return "- " + capitalize(action) + " a pull request in " + event.repo().name();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}