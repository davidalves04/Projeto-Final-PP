package user_interface;

import api.data.TeamExporterJSON;
import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.MatchHtmlGenerator;
import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.SeasonHtmlGenerator;
import api.simulation.LeagueSimulator;
import api.team.Squad;
import api.team.Team;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import htmlgenerators.ClubHtmlGenerator;
import htmlgenerators.LeagueHtmlGenerator;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    public void mostrarMenu(Squad mySquad,String mySquadFile,Squad[] totalSquads,ILeague liga, LeagueSimulator leagueSimulator, MatchSimulatorStrategy strategy) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        if(mySquad == null){ //Caso ainda nao tenha sido escolhido o plantel aparece este menu
        System.out.println("=== BEM-VINDO ===");
        System.out.println("1. Novo Jogo");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");

        try {
            opcao = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Por favor insira um número válido.");
            scanner.nextLine(); //Limpa o buffer
            return;
        }

        switch (opcao) {
            case 1:
                System.out.println("A iniciar uma nova temporada...");
                System.out.println("Escolha a sua equipa");
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
        Team myTeam = (Team) mySquad.getClub(); //Escolhe o melhor 11 inicial para o utilizador1
        
        SetStartingLineup lineup = new SetStartingLineup();
        IPlayer[] myLineup = lineup.mySquadBestLineup(mySquad, mySquad.getFormation().getDisplayName());
       
        
        //Segunda parte do menu
        int opcao2 = 0;
        do {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Gerir plantel");
            System.out.println("2. Ver calendário e classificação");
            System.out.println("3. Preparar próximo jogo");
            System.out.println("4. Simular jornada");
            System.out.println("5. Estatísticas");
            System.out.println("6. Exportar HTML");
            System.out.println("0. Guardar e sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao2 = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor insira um número válido.");
                scanner.nextLine(); //Limpa o buffer
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
                    new File("html").mkdirs();
                    try {
                        ISeason currentSeason = liga.getSeason(0);

                        // Exportar Season (tabela e jogos)
                        SeasonHtmlGenerator.generate(currentSeason, "html/season.html");
                        //System.out.println("Temporada exportada para HTML com sucesso.");

                        // Exportar todos os clubes
                        for (IClub club : currentSeason.getCurrentClubs()) {
                            String filename = "club_" + club.getCode() + ".html";
                            try {
                                ClubHtmlGenerator.generate(club, filename);
                                //System.out.println("Clube exportado: " + club.getName());
                            } catch (IOException e) {
                                System.err.println("Erro ao exportar clube " + club.getName() + ": " + e.getMessage());
                            }
                        }

                        // Exportar todos os jogos
                        int matchIndex = 1;
                        for (IMatch match : currentSeason.getMatches()) {
                            String filename = "html/match" + matchIndex++ + ".html";
                            try {
                                MatchHtmlGenerator.generate(match, filename);
                                //System.out.println("Jogo exportado: " + match.getHomeClub().getName() + " vs " + match.getAwayClub().getName());
                            } catch (IOException e) {
                                System.err.println("Erro ao exportar jogo: " + e.getMessage());
                            }
                        }

                        //Exportar JSON da liga como HTML
                        File leagueJson = new File("league.json");
                        if (leagueJson.exists()) {
                            LeagueHtmlGenerator.generate("league.json", "league.html");
                            //System.out.println("Liga exportada para HTML com sucesso.");
                        } else {
                            System.out.println("Ficheiro league.json não encontrado. Ignorado.");
                        }
                        System.out.println("Exportação concluída.");
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
