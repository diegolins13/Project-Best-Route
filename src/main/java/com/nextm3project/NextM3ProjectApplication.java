package com.nextm3project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NextM3ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextM3ProjectApplication.class, args);
	}
	
	@GetMapping("/")
	public String index() {
		return "EQUIPE M3 - Melhor Rota";
	}

}
