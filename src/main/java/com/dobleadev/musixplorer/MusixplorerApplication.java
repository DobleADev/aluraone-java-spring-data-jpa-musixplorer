package com.dobleadev.musixplorer;

import com.dobleadev.musixplorer.main.Main;
import com.dobleadev.musixplorer.repository.SingerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusixplorerApplication implements CommandLineRunner {
	@Autowired
	private SingerRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(MusixplorerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main program = new Main(repository);
		program.showMenu();
	}
}
