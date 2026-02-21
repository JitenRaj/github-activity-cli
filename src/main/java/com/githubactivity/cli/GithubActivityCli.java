package com.githubactivity.cli;

import com.githubactivity.cli.command.GithubCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GithubActivityCli implements CommandLineRunner {

    private final Map<String, GithubCommand> commandRegistry;

    public GithubActivityCli(List<GithubCommand> commands) {
        // map each command by its unique name (e.g., "push", "star", "all")
        this.commandRegistry = commands.stream()
                .collect(Collectors.toMap(GithubCommand::getName, Function.identity()));
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===================================");
        System.out.println("   Welcome to GitHub Activity CLI  ");
        System.out.println("===================================");
        System.out.println("Type 'help' to see available commands, or 'exit' to quit.\n");

        // Keep the application running in an infinite loop
        while (true) {
            System.out.print("github-activity> ");
            String input = scanner.nextLine().trim();

            // Check if the user wants to exit
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break; // This breaks the loop and allows Spring Boot to stop
            }

            // Ignore empty inputs (if you just press Enter)
            if (input.isEmpty()) {
                continue;
            }

            // Parse the input string into arguments (split by spaces)
            String[] inputArgs = input.split("\\s+");

            // Handle explicit help requests
            if (inputArgs[0].equalsIgnoreCase("help")) {
                commandRegistry.get("help").execute(null);
                System.out.println();
                continue;
            }

            // Extract username and command
            String username = inputArgs[0];
            String commandName = inputArgs.length > 1 ? inputArgs[1].toLowerCase() : "all";

            // Look up the requested command
            GithubCommand command = commandRegistry.get(commandName);

            if (command == null) {
                System.out.println("Error: Unknown command '" + commandName + "'");
                System.out.println("Type 'help' to see available commands.\n");
                continue;
            }

            // Execute the valid command
            System.out.println("Fetching '" + commandName + "' activity for " + username + "...\n");
            command.execute(username);
            System.out.println();
        }

        scanner.close();
    }
}