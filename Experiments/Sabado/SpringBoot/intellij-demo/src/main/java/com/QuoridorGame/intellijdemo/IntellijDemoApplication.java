package com.QuoridorGame.intellijdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class IntellijDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntellijDemoApplication.class, args);

		System.out.println("Type in your username");
		Scanner scnr = new Scanner(System.in);
		String username = scnr.next();
		System.out.println("Type in your password");
		String password = scnr.next();
		String passwordStars = "";
		for(int i = 0; i < password.length(); i++){
			passwordStars += "*";
		}

		System.out.println("Your Username is " + username);
		System.out.println("Do you want to see your password? Yes or No");
		if(scnr.next().equals("Yes")) {
			System.out.println("Your Password is " + password);
		} else {
			System.out.println("Your Password is " + passwordStars);
		}
	}

}
