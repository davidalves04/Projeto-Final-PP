package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Classe responsável por gerar um ficheiro HTML que apresenta informações
 * sobre uma temporada de futebol, incluindo os clubes participantes e os jogos realizados.
 * 
 * Implementa a interface IExporter para permitir a exportação dos dados da temporada.
 */
public class SeasonHtmlGenerator implements IExporter {

    private final ISeason season;
    private final String outputPath;

    /**
     * Construtor que recebe a temporada a exportar e o caminho do ficheiro de saída.
     * 
     * @param season Objeto que representa a temporada.
     * @param outputPath Caminho do ficheiro onde o HTML será guardado.
     */
    public SeasonHtmlGenerator(ISeason season, String outputPath) {
        this.season = season;
        this.outputPath = outputPath;
    }

    /**
     * Exporta os dados da temporada para um ficheiro HTML, implementando o método
     * da interface IExporter.
     * 
     * @throws IOException Se ocorrer algum erro ao escrever no ficheiro.
     */
    @Override
    public void exportToJson() throws IOException {
        generate(season, outputPath);
    }

    /**
     * Gera um ficheiro HTML que contém o ano da temporada, a lista de clubes
     * participantes e os jogos realizados.
     * 
     * @param season Temporada a ser representada em HTML.
     * @param outputPath Caminho do ficheiro onde o HTML será guardado.
     * @throws IOException Se ocorrer algum erro ao escrever no ficheiro.
     */
    public static void generate(ISeason season, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>Temporada ").append(season.getYear()).append("</h1>");
        html.append("<h2>Clubes Participantes</h2><ul>");

        Arrays.stream(season.getCurrentClubs())
            .forEach(club -> html.append("<li>").append(club.getName()).append("</li>"));
        html.append("</ul><h2>Jogos</h2><ul>");

        Arrays.stream(season.getMatches())
            .forEach(match -> html.append("<li>")
                .append(match.getHomeClub().getName())
                .append(" vs ")
                .append(match.getAwayClub().getName())
                .append("</li>"));

        html.append("</ul></body></html>");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        }
    }
}
