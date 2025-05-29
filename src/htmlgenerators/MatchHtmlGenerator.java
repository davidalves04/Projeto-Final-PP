package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsável por gerar um ficheiro HTML que apresenta informações
 * de um jogo de futebol, incluindo as equipas e os jogadores que participaram.
 * 
 * O ficheiro HTML contém o nome das equipas em confronto e detalhes dos
 * jogadores de cada equipa.
 */
public class MatchHtmlGenerator {

    /**
     * Gera um ficheiro HTML com os detalhes do jogo, incluindo nome das equipas
     * e informações individuais dos jogadores de ambas as equipas.
     * 
     * @param match Objeto que representa o jogo, contendo as equipas e jogadores.
     * @param outputPath Caminho do ficheiro onde o HTML será guardado.
     * @throws IOException Se ocorrer algum erro ao escrever no ficheiro.
     */
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

    /**
     * Adiciona ao HTML um bloco com as informações do jogador, incluindo nome,
     * idade, nacionalidade e um valor geral médio das suas capacidades.
     * 
     * @param html StringBuilder onde o conteúdo HTML será acrescentado.
     * @param player Jogador cujas informações serão apresentadas.
     */
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
