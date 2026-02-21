package com.githubactivity.cli.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelpCommand implements GithubCommand {

    private final List<GithubCommand> allCommands;

    public HelpCommand(@Lazy List<GithubCommand> allCommands) {
        this.allCommands = allCommands;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Displays this help menu.";
    }

    @Override
    public void execute(String username) {
        System.out.println("\nGitHub Activity CLI");
        System.out.println("Usage: github-activity <username> [command]");
        System.out.println("\nAvailable Commands:");

        for (GithubCommand cmd : allCommands) {
            System.out.printf("  %-15s : %s%n", cmd.getName(), cmd.getDescription());
        }
        System.out.println();
    }
}
