package user_interface;

import api.data.TeamImporterJSON;
import api.team.Squad;
import api.team.Team;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) throws IOException {
      
        MainMenu menu = new MainMenu();
        
        menu.mostrarMenu();
       
    }
    
    
    
    
    public void mostrarMenu() throws IOException{
        
        //Primeira parte do menu
        Team[] allTeams = TeamImporterJSON.teamsFromJson("clubs.json");
        Team selectedTeam = null;
        Squad selectedSquad = null;
        
         Scanner scanner = new Scanner(System.in);
        int opcao;

        
            System.out.println("=== BEM-VINDO ===");
            System.out.println("1. Novo Jogo ");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor insira um número válido.");
                scanner.nextLine(); // limpa o buffer
                opcao = -1;
               
            }

            switch (opcao) {
                case 1:
                    
                    System.out.println("Iniciando Nova Temporada...");
                    
                     
                   
                    selectedTeam = TeamSelector.selectTeam(allTeams);
                    
                    
                    break;
                case 2:
                    
                    System.out.println("Carregando Jogo...");
                    
                    
                    
                    break;
                case 3:
                    // lógica de preparação
                    System.out.println("A Sair...");
                    
                    return;
                
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

            System.out.println();
        
           
            
            
        //Segunda parte do menu    
           
        int opcao2;
        

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
                opcao2 = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor insira um número válido.");
                scanner.nextLine(); // limpa o buffer
                opcao2 = -1;
                continue;
            }

            switch (opcao2) {
                case 1:
                   
                    System.out.println("Mostrar equipa...");
                    selectedSquad = TeamView.teamView(selectedTeam);
                    
                    
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
        } while (opcao2 != 6);
    }
            
            
            
    }
    
    
   