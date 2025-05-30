/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package user_interface;

import api.team.Squad;
import api.team.Team;
import java.util.Scanner;

/**
 *
 * @author Utilizador
 */
public class TeamSelector {
      public static Squad selectTeam(Squad[] squads) {
          int teamCount = squads.length;
          
        if (squads == null || teamCount == 0) {
            System.out.println("Nenhuma equipa disponível.");
            return null;
        }

        System.out.println("=== Equipas Disponíveis ===");
        for (int i = 0; i < teamCount; i++) {
            System.out.println((i + 1) + ". " + squads[i].getClub().getName()); //i + 1 para começar no 1
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

        return squads[choice - 1]; // retorna a equipa escolhida
    }
}
