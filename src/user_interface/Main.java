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
import api.league.League; // Adiciona o import para League
import api.league.Season;
import api.team.Squad;
import api.team.Team;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;


import com.ppstudios.footballmanager.api.contracts.league.ISeason;


import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
         
            // Caminho para o ficheiro JSON com os dados da liga

            String leagueFile = "LigaPortuguesa.json"; // <- ajusta se necessário

           

            String clubsFile = "clubs.json";
            String squadsFile = "squad.json";
            String mySquadFile = "mySquad.json";
            // Importar ligas do ficheiro JSON
           
            
            
            
          
           
            Team[] totalClubs = TeamImporterJSON.teamsFromJson(clubsFile);
            Squad[] totalSquads = TeamImporterJSON.squadsFromJson(squadsFile, totalClubs);
            //Squad do utilizador num ficheiro aparte
            Squad mySquad = null;
            File fileMySquad = new File(mySquadFile);
            
            if(fileMySquad.exists()){
                mySquad = TeamImporterJSON.mySquadFromJson(mySquadFile, totalClubs);
            }
            
      



      
            League league = LeagueImporterJSON.readLeagueFromFile(leagueFile);
            ISeason s1 = league.getSeason(0);
           
  ((Season) s1).generateMatchesAutomatically();

            
            
            ((Season) s1).generateMatchesAutomatically();

            s1.generateSchedule();
            ((Season)s1).setMyTeam(mySquad);
            
            league.setFile(leagueFile);



     

            
             
            
         
            

            // Inicializar estratégia de simulação e simulador
            MatchSimulatorStrategy strategy = new DefaultMatchSimulator();
            
            LeagueSimulator simulator = new LeagueSimulator(league);
            

            // Iniciar menu principal
            MainMenu menu = new MainMenu();
            menu.mostrarMenu(mySquad,mySquadFile,totalSquads,league ,simulator, strategy);

        } catch (IOException e) {
            System.out.println("Erro ao iniciar o jogo: " + e.getMessage());
            e.printStackTrace();  // <-- imprime onde o erro aconteceu no código
            
        }
    }
}
