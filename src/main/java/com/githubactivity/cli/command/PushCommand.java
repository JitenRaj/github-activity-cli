package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class PushCommand extends BaseActivityCommand {

    public PushCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "push";
    }

    @Override
    public String getDescription() {
        return "Displays recent code pushes/commits.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return "PushEvent".equals(event.type());
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        int commits = (event.payload() != null && event.payload().size() != null) ? event.payload().size() : 0;
        return "- Pushed " + commits + " commit(s) to " + event.repo().name();
    }
}
