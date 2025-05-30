/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package user_interface;

import api.data.LeagueImporterJSON;
import api.league.Standing;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import java.io.IOException;

public class StatsView {

    public static void mostrarClassificacao(IStanding[] classificacao) throws IOException {
         // Carrega os standings do ficheiro JSON
    Standing[] standingsFromJson = LeagueImporterJSON.standingsFromJson("classificação.json");
    

    // Atualiza os dados do array classificacao (que está ligado ao modelo) com os dados importados
    for (int i = 0; i < classificacao.length && i < standingsFromJson.length; i++) {
        
        System.out.println( standingsFromJson[i].getGoalScored());
        Standing loaded = standingsFromJson[i];
        Standing current = (Standing) classificacao[i];

        // Atualiza os valores importantes — isso depende dos métodos setters que tens
        current.setPoints(loaded.getPoints());
        current.setWins(loaded.getWins());
        current.setDraws(loaded.getDraws());
        current.setLosses(loaded.getLosses());
        
        current.setGoalsScored(loaded.getGoalScored());
        current.setGoalsConceded(loaded.getGoalsConceded());

        // Se tiveres mais campos, atualiza-os aqui também
    }

    // Agora mostra a classificação atualizada
    System.out.println("=== Classificação ===");
    for (int i = 0; i < classificacao.length; i++) {
        IStanding s = classificacao[i];
        IClub clube = s.getTeam().getClub();
        System.out.println((i + 1) + ". " + clube.getName() +
            " - Pontos: " + s.getPoints() +
            ", Vitórias: " + s.getWins() +
            ", Empates: " + s.getDraws() +
            ", Derrotas: " + s.getLosses() +
            ", GM: " + s.getGoalScored() +
            ", GS: " + s.getGoalsConceded());
    }
    System.out.println();
    }
}
