package com.githubactivity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // This activates the test profile, skipping the CLI loop!
class GithubActivityApplicationTests {

	@Test
	void contextLoads() {
	}

}
