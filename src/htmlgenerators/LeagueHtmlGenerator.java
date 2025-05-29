package htmlgenerators;

import java.io.FileWriter;
import java.io.IOException;

public class LeagueHtmlGenerator {

    public static void generate(String leagueName, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>").append(leagueName).append("</h1>");
        html.append("<p>Conteúdo adicional da liga pode ser inserido aqui.</p>");
        html.append("</body></html>");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        }
    }
}