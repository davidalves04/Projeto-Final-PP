package user_interface;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import api.simulation.LeagueSimulator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    public void mostrarMenu(ILeague liga, IClub clube, LeagueSimulator leagueSimulator, MatchSimulatorStrategy strategy) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

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
                break;
            case 2:
                System.out.println("A sair...");
                return;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        // Segunda parte do menu
        int opcao2 = 0;
        do {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Gerir Plantel");
            System.out.println("2. Ver Classificação");
            System.out.println("3. Ver Próximo Jogo");
            System.out.println("4. Simular Jornada");
            System.out.println("5. Estatísticas");
            System.out.println("0. Sair");
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
                    TeamView.mostrarPlantel(clube);
                    break;
                case 2:
                    ISeason season = liga.getSeason(0);
                    IStanding[] standings = season.getLeagueStandings();
                    StatsView.mostrarClassificacao(standings);
                    break;
                case 3:
                    MatchView.mostrarProximoJogo(liga, clube);
                    break;
                case 4:
                    leagueSimulator.setMatchSimulator(strategy);
                    leagueSimulator.simulateRound();
                    System.out.println("Jornada simulada.");
                    break;
                case 5:
                    StatsView.mostrarClassificacao(liga.getSeason(0).getLeagueStandings());
                    break;
                case 0:
                    System.out.println("A sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao2 != 0);

        scanner.close();
    }
}
