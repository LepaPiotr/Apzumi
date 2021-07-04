package com.apzumi.apzumi;

import com.apzumi.apzumi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootApplication
public class ApzumiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApzumiApplication.class, args);
	}

	@Autowired
	PostService postService;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Scheduled(cron = "0 0 0 * * MON-SUN")
	public void generateSearch() {
		postService.addUpdatePostsSchedule();
	}

}
