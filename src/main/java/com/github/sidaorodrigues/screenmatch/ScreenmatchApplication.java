package com.github.sidaorodrigues.screenmatch;

import com.github.sidaorodrigues.screenmatch.model.DadosEpisodio;
import com.github.sidaorodrigues.screenmatch.model.DadosSerie;
import com.github.sidaorodrigues.screenmatch.model.DadosTemporada;
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
		String url = "https://www.omdbapi.com/?t=the+office&apikey=42f9ab4";
		ConsumoAPI consumoAPI = new ConsumoAPI();

		var json = consumoAPI.obterDados(url);
//		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

		System.out.println(dados);

		//dados episodio
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		json = consumoAPI.obterDados("https://www.omdbapi.com/?t=the+office&season=3&episode=10&apikey=42f9ab4");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		//dados temporada
		List<DadosTemporada> dadosTemporadas = new ArrayList<>();
		for (int x=1; x<=dados.totalTemporadas(); x++) {
			json = consumoAPI.obterDados("https://www.omdbapi.com/?t=the+office&season="+ x +"&apikey=42f9ab4");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			dadosTemporadas.add(dadosTemporada);
		}

		dadosTemporadas.forEach(System.out::println);
	}
}
