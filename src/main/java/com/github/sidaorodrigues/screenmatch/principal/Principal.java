package com.github.sidaorodrigues.screenmatch.principal;

import com.github.sidaorodrigues.screenmatch.model.DadosEpisodio;
import com.github.sidaorodrigues.screenmatch.model.DadosSerie;
import com.github.sidaorodrigues.screenmatch.model.DadosTemporada;
import com.github.sidaorodrigues.screenmatch.service.ConsumoAPI;
import com.github.sidaorodrigues.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=42f9ab4";

    private ConverteDados conversor = new ConverteDados();

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    public void exibeMenu() {
        System.out.println("Informe o nome da série:");
        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(BASE_URL + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        //dados temporada
		List<DadosTemporada> dadosTemporadas = new ArrayList<>();

		for (int x=1; x<=dados.totalTemporadas(); x++) {
			json = consumoApi.obterDados(BASE_URL + nomeSerie.replace(" ", "+") + "&season=" + x + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			dadosTemporadas.add(dadosTemporada);
		}

//		dadosTemporadas.forEach(System.out::println);
//
//        for (int x=0;x<dados.totalTemporadas();x++) {
//            List<DadosEpisodio> episodiosTemporada = dadosTemporadas.get(x).episodios();
//
//            for (DadosEpisodio episodio: episodiosTemporada) {
//                System.out.println(episodio.titulo());
//            }
//        }

        dadosTemporadas
                .forEach(t -> t.episodios()
                        .forEach(ep -> System.out.println(ep.titulo())));
    }
}
