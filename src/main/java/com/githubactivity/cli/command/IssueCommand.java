package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class IssueCommand extends BaseActivityCommand {

    public IssueCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "issue";
    }

    @Override
    public String getDescription() {
        return "Displays issue-related activities.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return "IssuesEvent".equals(event.type());
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        String action = (event.payload() != null && event.payload().action() != null)
                ? event.payload().action() : "interacted with";
        return "- " + capitalize(action) + " an issue in " + event.repo().name();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}