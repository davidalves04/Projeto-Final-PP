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

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu(Squad mySquad, String mySquadFile, Squad[] totalSquads,
                            ILeague liga, LeagueSimulator leagueSimulator, MatchSimulatorStrategy strategy) throws IOException {

        while (true) {
            mySquad = mostrarMenuInicial(mySquad, mySquadFile, totalSquads);
            if (mySquad == null) {
                break; // O utilizador escolheu sair
            }

            mostrarMenuPrincipal(mySquad, mySquadFile, liga, leagueSimulator, strategy);
        }

        scanner.close();
    }

    private Squad mostrarMenuInicial(Squad mySquad, String mySquadFile, Squad[] totalSquads) throws IOException {
        int opcao;

        System.out.println("=== BEM-VINDO ===");
        if (mySquad == null) {
            System.out.println("1. Novo Jogo");
            System.out.println("2. Sair");
        } else {
            System.out.println("1. Novo Jogo");
            System.out.println("2. Continuar");
            System.out.println("3. Sair");
        }

        System.out.print("Escolha uma opção: ");
        try {
            opcao = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Por favor insira um número válido.");
            scanner.nextLine();
            return mySquad;
        }

        switch (opcao) {
            case 1:
                System.out.println("A iniciar uma nova temporada...");
                System.out.println("Escolha a sua equipa");
                mySquad = TeamSelector.selectTeam(totalSquads);
                TeamExporterJSON.exportMySquad(mySquad, mySquadFile);
                break;
            case 2:
                if (mySquad == null) {
                    System.out.println("A sair...");
                    return null;
                }
                break;
            case 3:
                if (mySquad != null) {
                    System.out.println("A sair...");
                    return null;
                }
                System.out.println("Opção inválida.");
                break;
            default:
                System.out.println("Opção inválida.");
        }

        return mySquad;
    }

    private void mostrarMenuPrincipal(Squad mySquad, String mySquadFile,
                                      ILeague liga, LeagueSimulator leagueSimulator,
                                      MatchSimulatorStrategy strategy) throws IOException {

        IFormation myFormation = mySquad.getFormation();
        Team myTeam = (Team) mySquad.getClub();

        SetStartingLineup lineup = new SetStartingLineup();
        IPlayer[] myLineup = lineup.mySquadBestLineup(mySquad, mySquad.getFormation().getDisplayName());

        int opcao = 0;
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
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor insira um número válido.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    TeamView.showSquad(mySquad);
                    SquadManager squadManager = new SquadManager();
                    squadManager.promptForSubstitution(mySquad.getPlayers(), mySquad);
                    break;
                case 2:
                    ISeason season = liga.getSeason(0);
                    IStanding[] standings = season.getLeagueStandings();
                    StatsView.mostrarClassificacao(standings);
                    break;
                case 3:
                    myFormation = PrepareMatch.mostrarTaticas();
                    mySquad.setFormation(myFormation);
                    myLineup = lineup.mySquadBestLineup(mySquad, mySquad.getFormation().getDisplayName());
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
                    exportarHtml(liga);
                    break;
                case 0:
                    System.out.println("A guardar...");
                    TeamExporterJSON.exportMySquad(mySquad, mySquadFile);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private void exportarHtml(ILeague liga) {
        new File("html").mkdirs();
        try {
            ISeason currentSeason = liga.getSeason(0);

            SeasonHtmlGenerator.generate(currentSeason, "html/season.html");

            for (IClub club : currentSeason.getCurrentClubs()) {
                String filename = "club_" + club.getCode() + ".html";
                try {
                    ClubHtmlGenerator.generate(club, filename);
                } catch (IOException e) {
                    System.err.println("Erro ao exportar clube " + club.getName() + ": " + e.getMessage());
                }
            }

            int matchIndex = 1;
            for (IMatch match : currentSeason.getMatches()) {
                String filename = "html/match" + matchIndex++ + ".html";
                try {
                    MatchHtmlGenerator.generate(match, filename);
                } catch (IOException e) {
                    System.err.println("Erro ao exportar jogo: " + e.getMessage());
                }
            }

            File leagueJson = new File("league.json");
            if (leagueJson.exists()) {
                LeagueHtmlGenerator.generate("league.json", "league.html");
            } else {
                System.out.println("Ficheiro league.json não encontrado. Ignorado.");
            }

            System.out.println("Exportação concluída.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar para HTML: " + e.getMessage());
        }
    }
}
