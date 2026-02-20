package com.githubactivity.cli;

import com.githubactivity.service.GithubActivityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GithubActivityCli implements CommandLineRunner {

    private final GithubActivityService activityService;

    public GithubActivityCli(GithubActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String username = args[0];
        String filter = null;

        // Simple parsing for optional filter flag: --event=PushEvent
        if (args.length > 1 && args[1].startsWith("--event=")) {
            filter = args[1].split("=")[1];
        }

        System.out.println("Fetching activity for " + username + "...\n");
        List<String> activities = activityService.getRecentActivity(username, filter);

        if (activities.isEmpty()) {
            System.out.println("No matching events found.");
        } else {
            activities.forEach(System.out::println);
        }
    }

    private void printUsage() {
        System.out.println("Usage: java -jar app.jar <username> [--event=<EventType>]");
        System.out.println("Example: java -jar app.jar jitenraj");
        System.out.println("Example: java -jar app.jar jitenraj --event=PushEvent");
    }
}