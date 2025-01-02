package com.github.sidaorodrigues.screenmatch.principal;

import com.github.sidaorodrigues.screenmatch.model.DadosEpisodio;
import com.github.sidaorodrigues.screenmatch.model.DadosSerie;
import com.github.sidaorodrigues.screenmatch.model.DadosTemporada;
import com.github.sidaorodrigues.screenmatch.model.Episodio;
import com.github.sidaorodrigues.screenmatch.service.ConsumoAPI;
import com.github.sidaorodrigues.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

//        dadosTemporadas
//                .forEach(t -> t.episodios()
//                        .forEach(ep -> System.out.println(ep.titulo())));

        List<DadosEpisodio> dadosEpisodios = dadosTemporadas.stream()
                .flatMap(t -> t.episodios().stream())
//                .toList();
                .collect(Collectors.toList());

        System.out.println("Top 5 episodes!");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = dadosTemporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                        .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " - Episódio: " + e.getTitulo() +
                                " - Data lançamento: " + formatador.format(e.getDataLancamento())
                ));

    }
}
