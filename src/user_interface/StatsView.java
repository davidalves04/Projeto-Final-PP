package user_interface;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

public class StatsView {

    public static void mostrarClassificacao(IStanding[] classificacao) {
        System.out.println("=== Classificação ===");

        for (int i = 0; i < classificacao.length; i++) {
            IStanding s = classificacao[i];
            IClub clube = s.getTeam().getClub(); // getTeam(): ITeam → getClub(): IClub

            System.out.println((i + 1) + ". " + clube.getName() +
                " - Pontos: " + s.getPoints() +
                ", Vitórias: " + s.getWins() +
                ", Empates: " + s.getDraws() +
                ", Derrotas: " + s.getLosses() +
                ", GM: " + s.getGoalScored() +
                ", GS: " + s.getGoalsConceded());
        }

        System.out.println();
    }
}
