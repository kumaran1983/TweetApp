package com.salesforce.tweetapi.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan("com.salesforce.tweetapi")
@SpringBootApplication
public class TweetApplication {

	public static Logger log = LoggerFactory.getLogger(TweetApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TweetApplication.class, args);
	}

}
