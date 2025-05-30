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

import api.player.Position;

import api.team.Squad;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import java.io.IOException;
import java.util.Scanner;

public class SetStartingLineup {
       
         

    //DA RETURN DA MELHOR LINEUP 
    public IPlayer[] mySquadBestLineup(Squad squad, String formation) {
        
        
        
        Position gk = new Position("Goalkeeper");
        Position def = new Position("Defender");
        Position mid = new Position("Midfielder");
        Position atck = new Position("Forward");
        
        if (squad == null || formation == null || formation.isEmpty()) {
            throw new IllegalArgumentException("Clube ou formação inválida.");
        }

        String[] parts = formation.split("-"); //Para tirar os - da formaçao (ex: 4-3-3 -> 433)

        if (parts.length != 3) {
            throw new IllegalArgumentException("Formação inválida (esperado formato: X-Y-Z).");
        }

        int defCount = Integer.parseInt(parts[0].trim()); 
        int midCount = Integer.parseInt(parts[1].trim());
        int attCount = Integer.parseInt(parts[2].trim());

        // 1 Guarda-Redes + defesas + médios + atacantes
        IPlayer[] eleven = new IPlayer[1 + defCount + midCount + attCount];
        int index = 0;

        // Guarda-Redes
        eleven[index++] = selectBestPlayer(squad, gk,eleven);

        // Defesas
        for (int i = 0; i < defCount; i++) {
            eleven[index++] = selectBestPlayer(squad, def, eleven);
        }

        // Médios
        for (int i = 0; i < midCount; i++) {
            eleven[index++] = selectBestPlayer(squad, mid, eleven);
        }

        // Avançados
        for (int i = 0; i < attCount; i++) {
            eleven[index++] = selectBestPlayer(squad,atck, eleven);
        }

        return eleven;
    }

  
    
    
     
      
      
      
      
    //ESCOLHE OS MELHORES JOGADORES PARA AS POSIÇÕES
    private IPlayer selectBestPlayer(Squad squad, IPlayerPosition position, IPlayer[] alreadyChosen) {
        IPlayer[] players = squad.getPlayers();
        IPlayer best = null;
        int bestStats = -1;

        for (int i = 0; i < players.length; i++) {
            IPlayer p = players[i];
            if (!p.getPosition().getDescription().equalsIgnoreCase(position.getDescription())) continue;

            // Verifica se já foi escolhido
            boolean alreadyUsed = false;
            for (IPlayer chosen : alreadyChosen) {
                if (chosen != null && chosen.equals(p)) {
                    alreadyUsed = true;
                    break;
                }
            }
            if (alreadyUsed) continue;

            int stats = (p.getPassing() + p.getShooting() + p.getSpeed() + p.getStamina()) / 4;
            if (stats > bestStats) {
                bestStats = stats;
                best = p;
            }
        }

        if (best == null) {
            throw new IllegalStateException("Jogador insuficiente para posição " + position);
        }

        return best;
    }
    
      //OBTER JOGADORES QUE ESTAO NO BANCO
      public static IPlayer[] getBenchPlayers(Squad squad, IPlayer[] titulares) {
    IPlayer[] allPlayers = squad.getPlayers();
    int benchCount = allPlayers.length - titulares.length;
    IPlayer[] bench = new IPlayer[benchCount];
    int benchIndex = 0;

    for (int i = 0; i < allPlayers.length; i++) {
        boolean isTitular = false;

        for (int j = 0; j < titulares.length; j++) {
            if (allPlayers[i].equals(titulares[j])) {
                isTitular = true;
                break;
            }
        }

        if (!isTitular) {
            bench[benchIndex++] = allPlayers[i];
        }
    }

    return bench;
}

      //SUBSTITUIÇÕES
       private void substituirJogador(IPlayer[] titulares,Squad mySquad, int numeroTitular, int numeroBanco) {
        int indexTitular = -1, indexBanco = -1;
        IPlayer[] bench = getBenchPlayers(mySquad,titulares);

        // Encontrar jogador titular
        for (int i = 0; i < titulares.length; i++) {
            if (titulares[i].getNumber() == numeroTitular) {
                indexTitular = i;
                break;
            }
        }

        // Encontrar jogador banco
        for (int i = 0; i < bench.length; i++) {
            if (bench[i].getNumber() == numeroBanco) {
                indexBanco = i;
                break;
            }
        }

        if (indexTitular != -1 && indexBanco != -1) {
            IPlayer temp = titulares[indexTitular];
            titulares[indexTitular] = bench[indexBanco];
            bench[indexBanco] = temp;

            System.out.println("Substituição feita: " + temp.getName() + " por " + titulares[indexTitular].getName());
        } else {
            System.out.println("Erro: jogador não encontrado.");
        }
    }
      
        
   public  void promptForSubstitution(IPlayer[] titulares, Squad mySquad) throws IOException {
        Scanner scanner = new Scanner(System.in);
        IPlayer[] bench = getBenchPlayers(mySquad,titulares);
        
        
        System.out.print("Deseja fazer alterações (S/N): ");
        String answer = scanner.nextLine();
        

        if (answer.equalsIgnoreCase("S")) {
            System.out.print("Substituir jogador (número do titular): ");
            int numeroTitular = scanner.nextInt();
            scanner.nextLine(); // limpar buffer
            
            
            String posicaoTitular = null;
        for (IPlayer p : titulares) {
            if (p.getNumber() == numeroTitular) {
                posicaoTitular = p.getPosition().getDescription(); // ex: "Midfielder"
                break;
            }
        }

        if (posicaoTitular == null) {
            System.out.println("Jogador titular não encontrado.");
            return;
        }

            TeamView.showBench(bench,posicaoTitular);
            
            System.out.print("Por jogador do banco (número do banco): ");
            int numeroBanco = scanner.nextInt();

            substituirJogador(titulares, mySquad, numeroTitular, numeroBanco);
        }
    }    
                 
       }
    
    

