/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user_interface;

import java.util.Scanner;


/**
 *
 * @author david
 */
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
            System.out.println("5. Estatisticas");
            System.out.println("6. Salvar e Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Mostrar equipa...");
                    break;
                case 2:
                    System.out.println("Mostrar calendário e classificação...");
                    break;
                case 3:
                    System.out.println("Preparação proximo jogo...");
                    break;
                case 4:
                    System.out.println("Simulando Jornada...");
                    break;
                case 5:
                    System.out.println("Mostra Estatisticas...");
                    break;
                case 6:
                    System.out.println("A salvar e a sair...");
                    break;    
                default:
                    System.out.println("Opção inválida.");
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }
}
