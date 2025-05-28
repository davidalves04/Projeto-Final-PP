package user_interface;


import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

import api.data.LeagueImporterJSON;
import api.simulation.DefaultMatchSimulator;
import api.simulation.LeagueSimulator;
import api.league.League; // Adiciona o import para League



public class Main {
    public static void main(String[] args) {
        try {
            // Caminho para o ficheiro JSON com os dados da liga
            String jsonPath = "league.json"; // <- ajusta se necessário

            // Importar ligas do ficheiro JSON
            League liga = LeagueImporterJSON.readLeagueFromFile(jsonPath);
             
            
            
            // Verificar se há pelo menos uma liga
            if (liga == null) {
                System.out.println("Nenhuma liga encontrada no ficheiro JSON.");
                return;
            }

           
            

            // Obter a primeira temporada e a equipa do primeiro classificado
            ISeason season = liga.getSeason(0);
            
            
             
            
            IStanding[] standings = season.getLeagueStandings();
            IClub meuClube = standings[0].getTeam().getClub();
            

            // Inicializar estratégia de simulação e simulador
            MatchSimulatorStrategy strategy = new DefaultMatchSimulator();
            LeagueSimulator simulator = new LeagueSimulator(liga);

            // Iniciar menu principal
            MainMenu menu = new MainMenu();
            menu.mostrarMenu(liga, meuClube, simulator, strategy);

        } catch (Exception e) {
            System.out.println("Erro ao iniciar o jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
