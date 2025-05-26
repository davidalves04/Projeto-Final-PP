package user_interface;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

public class MatchView {

    public static void mostrarResultado(IMatch jogo) {
        System.out.println("=== Resultado do Jogo ===");
        System.out.println(jogo.getHomeClub().getName() + " vs " + jogo.getAwayClub().getName());

        if (jogo.isPlayed()) {
            int golosCasa = jogo.getTotalByEvent(IEvent.class, jogo.getHomeClub());
            int golosFora = jogo.getTotalByEvent(IEvent.class, jogo.getAwayClub());

            System.out.println("Resultado: " + golosCasa + " - " + golosFora);
        } else {
            System.out.println("Jogo ainda não foi jogado.");
        }

        System.out.println();
    }
    
    public static void mostrarProximoJogo(ILeague liga, IClub clube) {
        // lógica para encontrar e mostrar o próximo jogo
    }
}
