package user_interface;

import api.data.TeamExporterJSON;
import api.data.TeamImporterJSON;
import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.MatchHtmlGenerator;
import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.SeasonHtmlGenerator;
import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.LeagueHtmlGenerator;



import api.simulation.LeagueSimulator;
import api.team.Formation;
import api.team.Squad;
import api.team.Team;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import java.io.File;

import java.io.IOException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    public void mostrarMenu(Squad mySquad,String mySquadFile,Squad[] totalSquads,ILeague liga, LeagueSimulator leagueSimulator, MatchSimulatorStrategy strategy) throws IOException {
        
        
        
    
        Scanner scanner = new Scanner(System.in);
        int opcao;

        
        if(mySquad == null){ //Caso ainda nao esteja sido escolhido o plantel aparece este menu
        System.out.println("=== BEM-VINDO ===");
        System.out.println("1. Novo Jogo");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");

        try {
            opcao = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Por favor insira um número válido.");
            scanner.nextLine(); // limpa buffer
            return;
        }

        switch (opcao) {
            case 1:
                System.out.println("Iniciando Nova Temporada...");
                System.out.println("Escolha a sua Equipa");
                mySquad = TeamSelector.selectTeam(totalSquads);
                TeamExporterJSON.exportMySquad(mySquad, mySquadFile);
                
                break;
            case 2:
                System.out.println("A sair...");
                return;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        
        }
        
        
        IFormation myFormation = mySquad.getFormation();
        Team myTeam = (Team) mySquad.getClub();
        //Escolhe o melhor 11 inicial para o utilizador1
        
        SetStartingLineup lineup = new SetStartingLineup();
        IPlayer[] myLineup = lineup.mySquadBestLineup(mySquad, mySquad.getFormation().getDisplayName());
       
        
        // Segunda parte do menu
        int opcao2 = 0;
        do {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Gerir Plantel");
            System.out.println("2. Ver Calendário e Classificação");
            System.out.println("3. Preparar Próximo Jogo");
            System.out.println("4. Simular Jornada");
            System.out.println("5. Estatísticas");
            System.out.println("6. Exportar HTML");
            System.out.println("0. Salvar e Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao2 = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor insira um número válido.");
                scanner.nextLine(); // limpa buffer
                continue;
            }

            switch (opcao2) {
                case 1:
                    TeamView.showSquad(mySquad);
                   
                    SquadManager squadManager = new SquadManager();
                    
                    squadManager.promptForSubstitution(mySquad.getPlayers(),mySquad);
                   
                    break;
                case 2:
                    ISeason season = liga.getSeason(0);
                    IStanding[] standings = season.getLeagueStandings();
                    StatsView.mostrarClassificacao(standings);
                    break;
                case 3:
                    myFormation = PrepareMatch.mostrarTaticas();
                    mySquad.setFormation(myFormation);
                    myLineup = lineup.mySquadBestLineup(mySquad,mySquad.getFormation().getDisplayName());
                    
                    
                    TeamView.showLineup(myLineup);
                    SetStartingLineup subsForMatch = new SetStartingLineup();
                    subsForMatch.promptForSubstitution(myLineup, mySquad);
                    
                    break;
                case 4:
                    leagueSimulator.setMatchSimulator(strategy);
                    leagueSimulator.simulateRound();
                    System.out.println("Jornada simulada.");
                    break;
                case 5:
                    StatsView.mostrarClassificacao(liga.getSeason(0).getLeagueStandings());
                    break;
                case 6:
                    new File("output").mkdirs();
                    try {
                        ISeason currentSeason = liga.getSeason(0);

                        // Exportar Season (tabela e jogos)
                        SeasonHtmlGenerator.generate(currentSeason, "output/season.html");
                        System.out.println("Temporada exportada para HTML com sucesso.");

                        // Exportar League (se tiveres esse método a funcionar no futuro)
                        // LeagueHtmlGenerator.generate("league.json", "output/league.html");

                        // Exportar um jogo específico (exemplo: o primeiro jogo)
                        if (currentSeason.getMatches().length > 0) {
                            MatchHtmlGenerator.generate(currentSeason.getMatches()[0], "output/match1.html");
                            System.out.println("Primeiro jogo exportado para HTML com sucesso.");
                        }

                    } catch (IOException e) {
                        System.out.println("Erro ao exportar para HTML: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("A sair...");
                    TeamExporterJSON.exportMySquad(mySquad, mySquadFile);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao2 != 0);

        scanner.close();
    }
}
