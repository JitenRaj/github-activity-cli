package com.githubactivity.cli;

import com.githubactivity.cli.command.GithubCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Profile("!test") // This ensures the CLI only runs in non-test environments
public class GithubActivityCli implements CommandLineRunner {

    private final Map<String, GithubCommand> commandRegistry;

    private volatile long lastInteractionTime;
    private static final long TIMEOUT_MS = 10 * 60 * 1000;

    public GithubActivityCli(List<GithubCommand> commands) {
        this.commandRegistry = commands.stream()
                .collect(Collectors.toMap(GithubCommand::getName, Function.identity()));
    }

    @Override
    public void run(String... args) {
        if (args.length > 0) {
            processCommand(args);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("===================================");
        System.out.println("   Welcome to GitHub Activity CLI  ");
        System.out.println("===================================");
        System.out.println("Type 'help' to see available commands, or 'exit' to quit.\n");


        lastInteractionTime = System.currentTimeMillis();
        startInactivityTimer();

        while (true) {
            System.out.print("github-activity> ");
            String input = scanner.nextLine().trim();

            lastInteractionTime = System.currentTimeMillis();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            processCommand(input.split("\\s+"));
        }

        scanner.close();
    }

    private void startInactivityTimer() {
        Thread timerThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);

                    if (System.currentTimeMillis() - lastInteractionTime > TIMEOUT_MS) {
                        System.out.println("\n\nSession timed out due to 10 minutes of inactivity. Goodbye!");
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        timerThread.setDaemon(true);
        timerThread.start();
    }

    private void processCommand(String[] inputArgs) {
        if (inputArgs[0].equalsIgnoreCase("help") || inputArgs[0].equalsIgnoreCase("--help")) {
            commandRegistry.get("help").execute(null);
            return;
        }

        // Extract username and command
        String username = inputArgs[0];
        String commandName = inputArgs.length > 1 ? inputArgs[1].toLowerCase() : "all";

        // Look up the requested command
        GithubCommand command = commandRegistry.get(commandName);

        if (command == null) {
            System.out.println("Error: Unknown command '" + commandName + "'");
            System.out.println("Type 'help' to see available commands.\n");
            return;
        }

        // Execute the valid command
        System.out.println("Fetching '" + commandName + "' activity for " + username + "...\n");
        command.execute(username);
        System.out.println();
    }
}