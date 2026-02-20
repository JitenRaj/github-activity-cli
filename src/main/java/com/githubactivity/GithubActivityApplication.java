package com.githubactivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GithubActivityApplication {

	public static void main(String[] args) {
		// SpringApplication.run(GithubActivityApplication.class, args);

		SpringApplication app = new SpringApplication(GithubActivityApplication.class);
		app.setLogStartupInfo(false);
		app.run(args);
	}

}
