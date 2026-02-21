package com.githubactivity.cli.command;

import com.githubactivity.model.GithubEvent;
import com.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class CreateCommand extends BaseActivityCommand {

    public CreateCommand(GithubActivityService service) {
        super(service);
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Displays created repositories, branches, or tags.";
    }

    @Override
    protected boolean matchesCondition(GithubEvent event) {
        return "CreateEvent".equals(event.type());
    }

    @Override
    protected String formatOutput(GithubEvent event) {
        String refType = (event.payload() != null && event.payload().refType() != null)
                ? event.payload().refType() : "resource";
        return "- Created a " + refType + " in " + event.repo().name();
    }
}