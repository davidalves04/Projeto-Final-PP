package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe responsável por gerar uma representação HTML de uma época desportiva.
 * Implementa a interface {@code IExporter}, mas o método de exportação para JSON não está implementado.
 */
public class SeasonHtmlGenerator implements IExporter {

    private final ISeason season;
    private final String outputPath;

    /**
     * Construtor da classe {@code SeasonHtmlGenerator}.
     *
     * @param season Objeto que representa a época a ser exportada.
     * @param outputPath Caminho relativo (dentro da pasta "html/") onde o ficheiro HTML será guardado.
     */
    public SeasonHtmlGenerator(ISeason season, String outputPath) {
        this.season = season;
        this.outputPath = outputPath;
    }

    /**
     * Método da interface {@code IExporter}.
     * Lança uma exceção por não estar implementado o suporte para exportação para JSON.
     *
     * @throws IOException Nunca é lançado, pois o método apenas lança {@code UnsupportedOperationException}.
     */
    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Exportar para JSON não implementado.");
    }

    /**
     * Gera um ficheiro HTML com os dados da época, incluindo clubes participantes e jogos realizados.
     *
     * @param season Objeto que representa a época.
     * @param outputPath Caminho relativo (dentro da pasta "html/") onde o ficheiro HTML será guardado.
     * @throws IOException Se ocorrer um erro ao escrever o ficheiro.
     */
    public static void generate(ISeason season, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>").append(season.getName()).append("</title></head><body>");
        html.append("<h1>").append(season.getName()).append(" - ").append(season.getYear()).append("</h1>");

        // Substituindo o uso de coleção por um array
        html.append("<h2>Clubes Participantes</h2><ul>");
        IClub[] clubs = season.getCurrentClubs(); // Supondo que 'getCurrentClubs()' retorna um array
        for (int i = 0; i < clubs.length; i++) {
            html.append("<li>").append(clubs[i].getName()).append("</li>");
        }
        html.append("</ul>");

        // Substituindo o uso de coleção por um array
        html.append("<h2>Jogos</h2><ul>");
        IMatch[] matches = season.getMatches(); // Supondo que 'getMatches()' retorna um array
        for (int i = 0; i < matches.length; i++) {
            IMatch match = matches[i];
            html.append("<li>").append(match.getHomeClub().getName())
                .append(" vs ").append(match.getAwayClub().getName());
            if (match.isPlayed()) {
                html.append(" - Resultado: ")
                    .append(match.getTotalByEvent(null, match.getHomeClub()))
                    .append(" x ")
                    .append(match.getTotalByEvent(null, match.getAwayClub()));
            }
            html.append("</li>");
        }
        html.append("</ul></body></html>");

        Files.write(Paths.get("html/" + outputPath), html.toString().getBytes(StandardCharsets.UTF_8));
    }
}
