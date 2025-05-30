package htmlgenerators;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeagueHtmlGenerator {

    public LeagueHtmlGenerator() {}

    public static void generate(String jsonFilePath, String outputPath) throws IOException {
        String jsonContent = Files.readString(Paths.get(jsonFilePath));

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Época</title></head><body>");
        html.append("<h1>Dados da Época (JSON)</h1>");
        html.append("<pre>").append(jsonContent).append("</pre>");
        html.append("</body></html>");

        Files.write(Paths.get("html/" + outputPath), html.toString().getBytes(StandardCharsets.UTF_8));
    }
}
