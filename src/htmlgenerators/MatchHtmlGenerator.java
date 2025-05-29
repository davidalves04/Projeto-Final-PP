package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.FileWriter;
import java.io.IOException;

public class MatchHtmlGenerator {

    public static void generate(IMatch match, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>")
            .append(match.getHomeClub().getName())
            .append(" vs ")
            .append(match.getAwayClub().getName())
            .append("</h1>");

        html.append("<h2>Equipa da Casa</h2>");
        for (IPlayer player : match.getHomeTeam().getPlayers()) {
            appendPlayerCard(html, player);
        }

        html.append("<h2>Equipa Visitante</h2>");
        for (IPlayer player : match.getAwayTeam().getPlayers()) {
            appendPlayerCard(html, player);
        }

        html.append("</body></html>");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        }
    }

    private static void appendPlayerCard(StringBuilder html, IPlayer player) {
        int overall = (player.getShooting() + player.getPassing() + player.getStamina() + player.getSpeed()) / 4;
        html.append("<div class='player-card'>")
            .append("<strong>").append(player.getName()).append("</strong><br>")
            .append("Idade: ").append(player.getAge()).append("<br>")
            .append("Nacionalidade: ").append(player.getNationality()).append("<br>")
            .append("Overall: ").append(overall).append("<br>")
            .append("</div><br>");
    }
}
