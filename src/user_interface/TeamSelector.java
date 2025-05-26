/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user_interface;

import api.team.Team;
import java.util.Scanner;

/**
 *
 * @author Utilizador
 */
public class TeamSelector {
      public static Team selectTeam(Team[] teams) {
          int teamCount = Team.getTeamCount();
          
        if (teams == null || teamCount == 0) {
            System.out.println("Nenhuma equipa disponível.");
            return null;
        }

        System.out.println("=== Equipas Disponíveis ===");
        for (int i = 0; i < teamCount; i++) {
            System.out.println((i + 1) + ". " + teams[i].getName()); //i + 1 para começar no 1
        }

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice < 1 || choice > teamCount) {
            System.out.print("Escolha o número da equipa: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next(); // limpar input inválido
                System.out.println("Por favor, insira um número válido.");
            }
        }

        return teams[choice - 1]; // retorna a equipa escolhida
    }
}
