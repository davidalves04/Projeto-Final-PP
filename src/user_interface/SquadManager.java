package user_interface;

import api.team.Squad;
import api.team.Team;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;
import java.util.Scanner;

public class SquadManager {

    // Retorna jogadores que não estão convocados (não estão no array squadPlayers)
    public static IPlayer[] getNonSelectedPlayers(Squad mySquad, IPlayer[] squadPlayers) {
        IPlayer[] allPlayers = mySquad.getClub().getPlayers();
        int count = 0;

        for (IPlayer player : allPlayers) {
            boolean isInSquad = false;
            for (IPlayer squadPlayer : squadPlayers) {
                if (player.getNumber() == squadPlayer.getNumber()) {
                    isInSquad = true;
                    break;
                }
            }
            if (!isInSquad) count++;
        }

        IPlayer[] nonSelectedPlayers = new IPlayer[count];
        int index = 0;
        for (IPlayer player : allPlayers) {
            boolean isInSquad = false;
            for (IPlayer squadPlayer : squadPlayers) {
                if (player.getNumber() == squadPlayer.getNumber()) {
                    isInSquad = true;
                    break;
                }
            }
            if (!isInSquad) {
                nonSelectedPlayers[index++] = player;
            }
        }

        return nonSelectedPlayers;
    }

    // Realiza a substituição entre um jogador titular e um do banco
    private void subSquadPlayer(IPlayer[] squadPlayers, Squad mySquad, int numSquadPlayer, int numNonSelectedPlayers) {
              int indexTitular = -1, indexBanco = -1;
    IPlayer[] nonSelectedPlayers = getNonSelectedPlayers(mySquad, squadPlayers);

    // Encontrar índice do titular para substituir
    for (int i = 0; i < squadPlayers.length; i++) {
        if (squadPlayers[i].getNumber() == numSquadPlayer) {
            indexTitular = i;
            break;
        }
    }

    // Encontrar índice do jogador do banco para entrar
    for (int i = 0; i < nonSelectedPlayers.length; i++) {
        if (nonSelectedPlayers[i].getNumber() == numNonSelectedPlayers) {
            indexBanco = i;
            break;
        }
    }

    if (indexTitular != -1 && indexBanco != -1) {
        IPlayer temp = squadPlayers[indexTitular];             // jogador que sai
        IPlayer newPlayer = nonSelectedPlayers[indexBanco];    // jogador que entra

        squadPlayers[indexTitular] = newPlayer;                // substitui no squad

        // Atualiza o time: remove o que saiu, adiciona o que entrou
        mySquad.removePlayer(temp);
        mySquad.addPlayer(newPlayer);

        System.out.println("Substituição feita: " + temp.getName() + " por " + newPlayer.getName());
    } else {
        System.out.println("Erro: jogador não encontrado.");
    }
    }

    // Menu de substituição
    public void promptForSubstitution(IPlayer[] squadPlayers,Squad mySquad) throws IOException {
        Scanner scanner = new Scanner(System.in);
        IPlayer[] nonSelectedPlayers = getNonSelectedPlayers(mySquad, squadPlayers);

        System.out.print("Deseja fazer alterações na convocatoria(S/N): ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("S")) {
            System.out.print("Substituir jogador (número): ");
            int numeroTitular = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            String posicaoTitular = null;
            for (IPlayer p : squadPlayers) {
                if (p.getNumber() == numeroTitular) {
                    posicaoTitular = p.getPosition().getDescription();
                    break;
                }
            }

            if (posicaoTitular == null) {
                System.out.println("Jogador do plantel não encontrado.");
                return;
            }

            TeamView.showNonSelectedPlayers(nonSelectedPlayers, posicaoTitular);

            System.out.print("Escolha o substituto(número): ");
            int numeroBanco = scanner.nextInt();

            subSquadPlayer(squadPlayers, mySquad, numeroTitular, numeroBanco);
        }
    }
}
