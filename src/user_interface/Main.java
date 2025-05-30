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

import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import api.data.LeagueImporterJSON;
import api.data.TeamImporterJSON;
import api.simulation.DefaultMatchSimulator;
import api.simulation.LeagueSimulator;
import api.league.League;
import api.league.Season;
import api.team.Squad;
import api.team.Team;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;

import java.io.File;
import java.io.IOException;

/**
 * Classe principal responsável por inicializar o simulador de futebol.
 * <p>
 * Esta classe carrega os dados das equipas e da liga a partir de ficheiros JSON,
 * inicializa a época e o simulador, e apresenta o menu principal ao utilizador.
 * </p>
 */
public class Main {
    /**
     * Método principal que serve como ponto de entrada da aplicação.
     *
     * @param args Argumentos da linha de comandos (não utilizados).
     */
    public static void main(String[] args) {
        try {
            // Caminhos para os ficheiros JSON com os dados
            String leagueFile = "LigaPortugal.json"; // Caminho para a liga
            String clubsFile = "clubs.json";         // Caminho para os clubes
            String squadsFile = "squad.json";        // Caminho para os plantéis
            String mySquadFile = "mySquad.json";     // Caminho para o plantel do utilizador

            // Importar clubes e plantéis do ficheiro JSON
            Team[] totalClubs = TeamImporterJSON.teamsFromJson(clubsFile);
            Squad[] totalSquads = TeamImporterJSON.squadsFromJson(squadsFile, totalClubs);

            // Squad do utilizador num ficheiro à parte
            Squad mySquad = null;
            File fileMySquad = new File(mySquadFile);

            if (fileMySquad.exists()) {
                mySquad = TeamImporterJSON.mySquadFromJson(mySquadFile, totalClubs);
            }

            // Importar a liga e obter a primeira época
            League league = LeagueImporterJSON.readLeagueFromFile(leagueFile);
            ISeason s1 = league.getSeason(0);

            // Gerar jogos e definir equipa do utilizador
            ((Season) s1).generateMatchesAutomatically();
            s1.generateSchedule();
            ((Season) s1).setMyTeam(mySquad);

            league.setFile(leagueFile); // Guardar o caminho do ficheiro associado à liga

            // Inicializar estratégia e simulador
            MatchSimulatorStrategy strategy = new DefaultMatchSimulator();
            LeagueSimulator simulator = new LeagueSimulator(league);

            // Iniciar o menu principal
            MainMenu menu = new MainMenu();
            menu.mostrarMenu(mySquad, mySquadFile, totalSquads, league, simulator, strategy, totalSquads, totalClubs);

        } catch (IOException e) {
            System.out.println("Erro ao iniciar o jogo: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace para debug
        }
    }
}
