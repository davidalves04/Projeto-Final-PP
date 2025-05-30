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

import api.team.Formation;
import java.util.Scanner;

public class PrepareMatch {
     public static Formation mostrarTaticas() {
         
         
        Formation form1 = new Formation("4-3-3");
        Formation form2 = new Formation("5-3-2");
        Formation form3 = new Formation("4-4-2");
        Formation[] defaultFormations = new Formation[]{form1,form2,form3}; 
         
        System.out.println("=== ESTRATÉGIA ===");
        System.out.println("Escolha a tática para este jogo:");

        for (int i = 0; i < defaultFormations.length; i++) {
            String display = defaultFormations[i].getDisplayName();
            String descricao = switch (display) {
                case "5-3-2" -> "Defensiva (" + display + ") - Foco em contenção";
                case "4-4-2" -> "Equilibrada (" + display + ") - Padrão";
                case "4-3-3" -> "Ofensiva (" + display + ") - Pressão alta";
                default -> "tatica desconhecida";
            };

            System.out.println((i + 1) + ". " + descricao);
        }

        
        
        
         Scanner scanner = new Scanner(System.in);
        int choice = -1;

         while (choice < 1 || choice > defaultFormations.length) {
            System.out.print("Escolha a formação: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next(); // limpar input inválido
                System.out.println("Por favor, insira um número válido.");
            }
        }
         return defaultFormations[choice - 1];
    }
     
    
}
