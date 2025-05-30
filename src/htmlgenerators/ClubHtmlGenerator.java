/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package htmlgenerators;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe responsável por gerar um ficheiro HTML com a informação detalhada de um clube.
 * Inclui dados como nome, código, país, ano de fundação, estádio, logótipo e plantel.
 */
public class ClubHtmlGenerator {

    /**
     * Construtor por omissão da classe {@code ClubHtmlGenerator}.
     */
    public ClubHtmlGenerator() {}

    /**
     * Gera um ficheiro HTML com os detalhes do clube fornecido.
     *
     * @param club O clube cuja informação será usada para gerar o HTML.
     * @param outputPath O nome do ficheiro de saída (dentro da pasta "html/").
     * @throws IOException Se ocorrer um erro ao escrever o ficheiro HTML.
     */
    public static void generate(IClub club, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>").append(club.getName()).append("</title></head><body>");
        html.append("<h1>").append(club.getName()).append("</h1>");
        html.append("<p><strong>Código:</strong> ").append(club.getCode()).append("</p>");
        html.append("<p><strong>País:</strong> ").append(club.getCountry()).append("</p>");
        html.append("<p><strong>Ano de fundação:</strong> ").append(club.getFoundedYear()).append("</p>");
        html.append("<p><strong>Estádio:</strong> ").append(club.getStadiumName()).append("</p>");
        html.append("<img src='").append(club.getLogo()).append("' alt='Logo' height='100'/>");
        html.append("<h2>Plantel</h2><ul>");

        for (IPlayer player : club.getPlayers()) {
            html.append("<li>").append(player.getNumber()).append(" - ").append(player.getName())
                .append(" (").append(player.getPosition()).append(")</li>");
        }

        html.append("</ul></body></html>");

        Files.write(Paths.get("html/" + outputPath), html.toString().getBytes(StandardCharsets.UTF_8));
    }
}
