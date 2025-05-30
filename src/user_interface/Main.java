package user_interface;


import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;

import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

import api.data.LeagueImporterJSON;
import api.data.TeamImporterJSON;
import api.simulation.DefaultMatchSimulator;
import api.simulation.LeagueSimulator;
import api.league.League; //Adiciona o import para League

import api.team.Squad;
import api.team.Team;

import java.io.File;




public class Main {
    public static void main(String[] args) {
        try {
         
            // Caminho para o ficheiro JSON com os dados da liga
            String leagueFile = "league.json"; // <- ajusta se necessário
            String clubsFile = "clubs.json";
            String squadsFile = "squad.json";
            String mySquadFile = "mySquad.json";
            // Importar ligas do ficheiro JSON
            League liga = LeagueImporterJSON.readLeagueFromFile(leagueFile);
            Team[] totalClubs = TeamImporterJSON.teamsFromJson(clubsFile);
            Squad[] totalSquads = TeamImporterJSON.squadsFromJson(squadsFile, totalClubs);
            //Squad do utilizador num ficheiro aparte
            Squad mySquad = null;
            File fileMySquad = new File(mySquadFile);
            
            if(fileMySquad.exists()){
                mySquad = TeamImporterJSON.mySquadFromJson(mySquadFile, totalClubs);
            }
            
            
            // Verificar se há pelo menos uma liga
            if (liga == null) {
                System.out.println("Nenhuma liga encontrada no ficheiro JSON.");
                return;
            }

           
            

            // Obter a primeira temporada e a equipa do primeiro classificado
            ISeason season = liga.getSeason(0);
            
            SetStartingLineup lineup = new SetStartingLineup();
            
          
             
            
            IStanding[] standings = season.getLeagueStandings();
          
            

            // Inicializar estratégia de simulação e simulador
            MatchSimulatorStrategy strategy = new DefaultMatchSimulator();
            LeagueSimulator simulator = new LeagueSimulator(liga);

            // Iniciar menu principal
            MainMenu menu = new MainMenu();
            menu.mostrarMenu(mySquad,mySquadFile,totalSquads,liga, simulator, strategy);

        } catch (Exception e) {
            System.out.println("Erro ao iniciar o jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
