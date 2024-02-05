package com.cs309.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cs309.tutorial.quoridorApp.player.Player;
import com.cs309.tutorial.quoridorApp.repository.PlayerRepository;
import com.cs309.tutorial.quoridorApp.player.PlayerSessions;

/**
 * Creates a commandLine runner to enter dummy data into the database
 */
@SpringBootApplication
@EnableJpaRepositories
public class QuoridorSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoridorSpringApplication.class, args);
	}

	@Bean(name="entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		return sessionFactory;
	}
}



/*
{
    "login":false,
    "username":"name",
    "password":123,
    "name":"me"
}

"38b9281b-bd2a-4cac-8ffa-a1884c4a1ad1"
"2cdac078-44a3-469f-a548-c1819520a4ef"


{
    "id": "7f1e0121-e59b-4814-a554-1a54e8c03dd7",
    "gameId":0,
    "x":3,
    "y":3
}
 */