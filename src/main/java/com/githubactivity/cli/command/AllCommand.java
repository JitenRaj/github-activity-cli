package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class AllCommand extends BaseActivityCommand {

    public AllCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "all";
    }

    @Override
    public String getDescription() {
        return "Displays all recent GitHub activity.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return true;
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        String repoName = event.repo() != null ? event.repo().name() : "unknown";
        return "- [" + event.type() + "] on " + repoName;
    }
}
