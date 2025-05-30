package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SeasonHtmlGenerator implements IExporter {

    private final ISeason season;
    private final String outputPath;

    public SeasonHtmlGenerator(ISeason season, String outputPath) {
        this.season = season;
        this.outputPath = outputPath;
    }

    @Override
    public void exportToJson() throws IOException {
        // Não implementado pois não temos dados de serialização aqui
        throw new UnsupportedOperationException("Exportar para JSON não implementado.");
    }

    public static void generate(ISeason season, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>").append(season.getName()).append("</title></head><body>");
        html.append("<h1>").append(season.getName()).append(" - ").append(season.getYear()).append("</h1>");

        html.append("<h2>Clubes Participantes</h2><ul>");
        for (IClub club : season.getCurrentClubs()) {
            html.append("<li>").append(club.getName()).append("</li>");
        }
        html.append("</ul>");

        html.append("<h2>Jogos</h2><ul>");
        for (IMatch match : season.getMatches()) {
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
