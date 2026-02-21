package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class PrCloseCommand extends BaseActivityCommand {

    public PrCloseCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "pr-close";
    }

    @Override
    public String getDescription() {
        return "Displays recently closed or merged pull requests.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return "PullRequestEvent".equals(event.type()) &&
                event.payload() != null &&
                "closed".equals(event.payload().action());
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        return "- Closed/Merged a pull request in " + event.repo().name();
    }
}
