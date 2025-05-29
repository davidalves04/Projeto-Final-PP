package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SeasonHtmlGenerator implements IExporter {

    private final ISeason season;
    private final String outputPath;

    public SeasonHtmlGenerator(ISeason season, String outputPath) {
        this.season = season;
        this.outputPath = outputPath;
    }

    @Override
    public void exportToJson() throws IOException {
        generate(season, outputPath);
    }

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