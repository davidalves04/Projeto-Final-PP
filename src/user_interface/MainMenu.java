package user_interface;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Gerir Plantel");
            System.out.println("2. Ver Calendário e Classificação");
            System.out.println("3. Preparar Próximo Jogo");
            System.out.println("4. Simular Jornada");
            System.out.println("5. Estatísticas");
            System.out.println("6. Salvar e Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor insira um número válido.");
                scanner.nextLine(); // limpa o buffer
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    // new TeamView().mostrarPlantel();
                    System.out.println("Mostrar equipa...");
                    break;
                case 2:
                    // new MatchView().mostrarCalendario();
                    System.out.println("Mostrar calendário e classificação...");
                    break;
                case 3:
                    // lógica de preparação
                    System.out.println("Preparação próximo jogo...");
                    break;
                case 4:
                    // new LeagueSimulator(...).simulateRound(...)
                    System.out.println("Simulando Jornada...");
                    break;
                case 5:
                    // new StatsView().mostrarEstatisticas();
                    System.out.println("Mostra Estatísticas...");
                    break;
                case 6:
                    System.out.println("A salvar e a sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 6);
    }
}
