package com.QuizMaster.UserServ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserServApplication {

	public static void main(String[] args) {

		System.out.println("Java timezone = " +
				java.util.TimeZone.getDefault().getID());

		System.out.println("Java version = " +
				System.getProperty("java.version"));

		SpringApplication.run(UserServApplication.class, args);
	}

}
