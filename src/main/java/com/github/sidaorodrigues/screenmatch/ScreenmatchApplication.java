package com.github.sidaorodrigues.screenmatch;

import com.github.sidaorodrigues.screenmatch.service.ConsumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String url = "https://www.omdbapi.com/?t=the+office&apikey=42f9ab4";
		ConsumoAPI consumoAPI = new ConsumoAPI();

		var json = consumoAPI.obterDados(url);
		System.out.println(json);
	}
}
