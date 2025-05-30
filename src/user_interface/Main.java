package user_interface;


import api.data.LeagueExporterJSON;


import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

import api.data.LeagueImporterJSON;
import api.data.TeamImporterJSON;
import api.simulation.DefaultMatchSimulator;
import api.simulation.LeagueSimulator;


import api.league.League; // Adiciona o import para League
import api.league.Season;



import api.team.Squad;
import api.team.Team;

import java.io.File;
import java.io.IOException;




public class Main {
    public static void main(String[] args) {
        try {
         
            // Caminho para o ficheiro JSON com os dados da liga
            String leagueFile = "LigaFODASSE.json"; // <- ajusta se necessário
            String clubsFile = "clubs.json";
            String squadsFile = "squad.json";
            String mySquadFile = "mySquad.json";
            // Importar ligas do ficheiro JSON
           
            
            
            
           League league = LeagueImporterJSON.readLeagueFromFile(leagueFile);
           
            Team[] totalClubs = TeamImporterJSON.teamsFromJson(clubsFile);
            Squad[] totalSquads = TeamImporterJSON.squadsFromJson(squadsFile, totalClubs);
            //Squad do utilizador num ficheiro aparte
            Squad mySquad = null;
            File fileMySquad = new File(mySquadFile);
            
            if(fileMySquad.exists()){
                mySquad = TeamImporterJSON.mySquadFromJson(mySquadFile, totalClubs);
            }
            
           


       League league2 = new League("2024/2025");
           Season s2 = new Season("2024/2025",2025,20,2,3,1,0,totalSquads,totalClubs);
           s2.setTeams("squad.json", totalClubs);
           s2.generateMatchesAutomatically();
           s2.generateSchedule();
           s2.setMyTeam(mySquad);
           
           league2.createSeason(s2);
           league2.setFile("LigaPortugal2.json");
           league2.exportToJson();

     

            
             
            
         
            

            // Inicializar estratégia de simulação e simulador
            MatchSimulatorStrategy strategy = new DefaultMatchSimulator();
            LeagueSimulator simulator = new LeagueSimulator(league);

            // Iniciar menu principal
            MainMenu menu = new MainMenu();
            menu.mostrarMenu(mySquad,mySquadFile,totalSquads,league, simulator, strategy);

        } catch (IOException e) {
            System.out.println("Erro ao iniciar o jogo: " + e.getMessage());
        }
    }
}
