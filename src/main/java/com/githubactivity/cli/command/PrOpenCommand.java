package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class PrOpenCommand extends BaseActivityCommand {

    public PrOpenCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "pr-open";
    }

    @Override
    public String getDescription() {
        return "Displays recently opened pull requests.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return "PullRequestEvent".equals(event.type()) &&
                event.payload() != null &&
                "opened".equals(event.payload().action());
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        return "- Opened a pull request in " + event.repo().name();
    }
}
