package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class StarCommand extends BaseActivityCommand {

    public StarCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "star";
    }

    @Override
    public String getDescription() {
        return "Displays repositories the user has starred.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return "WatchEvent".equals(event.type());
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        return "- Starred " + event.repo().name();
    }
}