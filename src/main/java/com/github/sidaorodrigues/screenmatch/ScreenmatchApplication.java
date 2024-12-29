package com.github.sidaorodrigues.screenmatch;

import com.github.sidaorodrigues.screenmatch.model.DadosEpisodio;
import com.github.sidaorodrigues.screenmatch.model.DadosSerie;
import com.github.sidaorodrigues.screenmatch.model.DadosTemporada;
import com.github.sidaorodrigues.screenmatch.principal.Principal;
import com.github.sidaorodrigues.screenmatch.service.ConsumoAPI;
import com.github.sidaorodrigues.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
