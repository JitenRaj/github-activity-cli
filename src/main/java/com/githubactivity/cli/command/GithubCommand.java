package com.githubactivity.cli.command;

public interface GithubCommand {
    String getName();
    String getDescription();
    void execute(String username);
}
