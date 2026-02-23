# 🚀 GitHub Activity CLI

**GitHub Activity CLI** is a lightweight, interactive command-line tool built with Java 17 and Spring Boot that allows you to fetch, filter, and view any GitHub user's recent public activity directly from your terminal.

## Features

* **Interactive Shell (REPL):** Run the app once and execute multiple commands in a single continuous session.
* **Smart Caching:** 5-minute in-memory cache to reduce redundant GitHub API calls and prevent rate limiting.
* **Native HTTP Client:** Uses Java's built-in `java.net.http.HttpClient` rather than external REST frameworks.
* **Command Pattern:** Commands are cleanly modularized for easy extension.
* **Auto-Timeout:** Safely shuts down the shell after 10 minutes of inactivity to free up system resources.

---

## 📦 Installation

### Option 1: Run Prebuilt JAR (Recommended)

1. Go to the [Releases](https://github.com/JitenRaj/github-activity-cli/releases) page of this repository.
2. Download the latest `githubactivity-X.X.X.jar` file from the **Assets** section.
3. Run the application from your terminal:

```bash
java -jar githubactivity-0.0.1-SNAPSHOT.jar

```

### Option 2: Build from Source

**Requirements:** Java 17+ and Maven 3.8+

```bash
# 1. Clone the repository
git clone https://github.com/JitenRaj/github-activity-cli.git
cd github-activity-cli

# 2. Build the project using Maven
mvn clean package -DskipTests

# 3. Run the executable
java -jar target/githubactivity-0.0.1-SNAPSHOT.jar

```

---

## 🚀 Usage & Commands

Once started, the application launches an interactive shell (`github-activity>`). You can continuously enter commands to fetch different data.

| Command | Description |
| --- | --- |
| `help` | View the help menu |
| `<username> all` | View all recent public activity |
| `<username> push` | View recent commits and pushes |
| `<username> star` | View repositories the user recently starred |
| `<username> create` | View newly created repos, branches, or tags |
| `<username> pr` | View all pull request activity |
| `<username> pr-close` | View recently merged or closed PRs |
| `<username> issue` | View issue interactions |
| `exit` / `quit` | Close the application |

### 📘 Examples

```text
github-activity> help
github-activity> jitenraj all
github-activity> jitenraj push
github-activity> jitenraj pr
```

---

## 🛠 Troubleshooting

* **Error: Invalid username / 404 Not Found**
  Ensure the GitHub username is spelled correctly. The user's activity must also be public.
* **API Rate Limit Exceeded**
  GitHub limits unauthenticated requests to 60 per hour per IP address. Wait for the rate limit to reset.
* **Java Version Error**
  Ensure you are using Java 17 or higher by running `java -version` in your terminal.
