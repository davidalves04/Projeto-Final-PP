package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe responsável por gerar uma representação HTML de um jogo de futebol.
 * A informação inclui nomes dos clubes, ronda, estado e resultado (caso o jogo tenha sido jogado).
 */
public class MatchHtmlGenerator {

    /**
     * Construtor por omissão da classe {@code MatchHtmlGenerator}.
     */
    public MatchHtmlGenerator() {}

    /**
     * Gera um ficheiro HTML com os dados de um jogo.
     *
     * @param match Objeto que representa o jogo.
     * @param outputPath Caminho relativo (dentro da pasta "html/") onde o ficheiro HTML será guardado.
     * @throws IOException Se ocorrer um erro ao escrever o ficheiro.
     */
    public static void generate(IMatch match, String outputPath) throws IOException {
        IClub home = match.getHomeClub();
        IClub away = match.getAwayClub();

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Jogo: ")
            .append(home.getName()).append(" vs ").append(away.getName())
            .append("</title></head><body>");

        html.append("<h1>").append(home.getName()).append(" vs ").append(away.getName()).append("</h1>");
        html.append("<p><strong>Ronda:</strong> ").append(match.getRound()).append("</p>");
        html.append("<p><strong>Estado:</strong> ").append(match.isPlayed() ? "Jogado" : "Por jogar").append("</p>");

        if (match.isPlayed()) {
            html.append("<h2>Resultado</h2>")
                .append("<p>")
                .append(home.getName()).append(": ").append(match.getTotalByEvent(null, home)).append(" - ")
                .append(away.getName()).append(": ").append(match.getTotalByEvent(null, away))
                .append("</p>");
        }

        html.append("</body></html>");
        Files.write(Paths.get("html/" + outputPath), html.toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Adiciona ao HTML uma representação em cartão de um jogador.
     *
     * @param html StringBuilder onde o conteúdo HTML será adicionado.
     * @param player Jogador a ser representado.
     */
    private static void appendPlayerCard(StringBuilder html, IPlayer player) {
        html.append("<div style='border:1px solid #000;padding:10px;margin:5px;'>")
            .append("<img src='").append(player.getPhoto()).append("' height='50'/>")
            .append("<p><strong>").append(player.getName()).append("</strong></p>")
            .append("<p>Idade: ").append(player.getAge()).append("</p>")
            .append("<p>Posição: ").append(player.getPosition()).append("</p>")
            .append("</div>");
    }
}
