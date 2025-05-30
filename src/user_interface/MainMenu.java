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
import java.util.Scanner;

/**
 * Classe responsável por gerir a interface principal do utilizador, incluindo o menu inicial e o menu principal.
 * Permite iniciar um novo jogo, continuar uma temporada existente, gerir o plantel, simular jornadas e exportar dados.
 */
public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Inicia o menu principal da aplicação.
     *
     * @param mySquad        Plantel atual do utilizador (pode ser nulo se for um novo jogo).
     * @param mySquadFile    Caminho para o ficheiro JSON onde o plantel é guardado.
     * @param totalSquads    Lista de todos os plantéis disponíveis para seleção.
     * @param liga           Liga que está a ser gerida.
     * @param leagueSimulator Simulador da liga.
     * @param strategy       Estratégia de simulação de jogos.
     * @throws IOException Se ocorrer um erro durante a leitura ou escrita de ficheiros.
     */
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

    /**
     * Mostra o menu inicial com as opções de novo jogo, continuar ou sair.
     *
     * @param mySquad     Plantel atual do utilizador (pode ser nulo).
     * @param mySquadFile Caminho para o ficheiro JSON onde o plantel é guardado.
     * @param totalSquads Lista de todos os plantéis disponíveis para seleção.
     * @return O plantel selecionado ou carregado pelo utilizador, ou null se escolher sair.
     * @throws IOException Se ocorrer um erro ao exportar o plantel.
     */
    private Squad mostrarMenuInicial(Squad mySquad, String mySquadFile, Squad[] totalSquads) throws IOException {
        int opcao;

        System.out.println("=== BEM-VINDO ===");
        if (mySquad == null) {
            System.out.println("1. Novo Jogo");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcaoValida(1, 2);
            if (opcao == 1) {
                System.out.println("A iniciar uma nova temporada...");
                System.out.println("Escolha a sua equipa");
                mySquad = TeamSelector.selectTeam(totalSquads);
                TeamExporterJSON.exportMySquad(mySquad, mySquadFile);
            } else {
                System.out.println("A sair...");
                return null;
            }
        } else {
            System.out.println("1. Novo Jogo");
            System.out.println("2. Continuar");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcaoValida(1, 3);
            switch (opcao) {
                case 1:
                    System.out.println("A iniciar uma nova temporada...");
                    System.out.println("Escolha a sua equipa");
                    mySquad = TeamSelector.selectTeam(totalSquads);
                    TeamExporterJSON.exportMySquad(mySquad, mySquadFile);
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("A sair...");
                    return null;
            }
        }

        return mySquad;
    }

    /**
     * Mostra o menu principal com as opções de gestão da equipa, simulação e exportação.
     *
     * @param mySquad         Plantel do utilizador.
     * @param mySquadFile     Caminho para o ficheiro JSON do plantel.
     * @param liga            Liga ativa.
     * @param leagueSimulator Simulador da liga.
     * @param strategy        Estratégia de simulação dos jogos.
     * @throws IOException Se ocorrer erro ao ler ou exportar dados.
     */
    private void mostrarMenuPrincipal(Squad mySquad, String mySquadFile,
                                      ILeague liga, LeagueSimulator leagueSimulator,
                                      MatchSimulatorStrategy strategy) throws IOException {

        IFormation myFormation = mySquad.getFormation();
        Team myTeam = (Team) mySquad.getClub();

        SetStartingLineup lineup = new SetStartingLineup();
        IPlayer[] myLineup = lineup.mySquadBestLineup(mySquad, myFormation.getDisplayName());

        int opcao;
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

            opcao = lerOpcaoValida(0, 6);

            switch (opcao) {
                case 1:
                    TeamView.showSquad(mySquad);
                    SquadManager squadManager = new SquadManager();
                    squadManager.promptForSubstitution(mySquad.getPlayers(), mySquad);
                    break;
                case 2:
                    ISeason season = liga.getSeason(0);
                    StatsView.mostrarClassificacao(season.getLeagueStandings());
                    break;
                case 3:
                    myFormation = PrepareMatch.mostrarTaticas();
                    mySquad.setFormation(myFormation);
                    myLineup = lineup.mySquadBestLineup(mySquad, myFormation.getDisplayName());
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
            }

        } while (opcao != 0);
    }

    /**
     * Exporta os dados da temporada, clubes e jogos para ficheiros HTML.
     *
     * @param liga Liga da qual se pretende exportar os dados.
     */
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

    /**
     * Lê e valida uma opção inserida pelo utilizador dentro de um intervalo.
     *
     * @param min Valor mínimo permitido.
     * @param max Valor máximo permitido.
     * @return A opção escolhida pelo utilizador.
     */
    private int lerOpcaoValida(int min, int max) {
        int opcao = -1;
        while (true) {
            String input = scanner.nextLine();
            try {
                opcao = Integer.parseInt(input);
                if (opcao >= min && opcao <= max) {
                    break;
                } else {
                    System.out.print("Por favor insira um número entre " + min + " e " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Por favor insira um número: ");
            }
        }
        return opcao;
    }
}
