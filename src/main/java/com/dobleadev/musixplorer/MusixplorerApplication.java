package com.dobleadev.musixplorer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusixplorerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MusixplorerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("YES");
	}
}
