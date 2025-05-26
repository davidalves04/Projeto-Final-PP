package user_interface;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

public class TeamView {

    public static void mostrarPlantel(IClub clube) {
        System.out.println("=== Plantel do Clube: " + clube.getName() + " ===");
        IPlayer[] jogadores = clube.getPlayers();

        for (int i = 0; i < jogadores.length; i++) {
            IPlayer jogador = jogadores[i];
            System.out.println((i + 1) + ". " + jogador.getName() +
                " (" + jogador.getPosition().getDescription() + ") - " +
                "Número: " + jogador.getNumber());
        }

        System.out.println("Total de jogadores: " + clube.getPlayerCount());
        System.out.println();
    }
}
