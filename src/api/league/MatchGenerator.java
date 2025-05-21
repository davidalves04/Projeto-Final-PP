package api.league;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

public class MatchGenerator {

    public static IMatch[][] generateRoundRobinMatches(IClub[] clubs, int numberOfRounds) {
        int n = clubs.length;

        // Calcula o número de jornadas por volta
        int roundsPerRoundRobin = n - 1;
        int matchesPerRound = n / 2;

        // Total de jornadas
        int totalRounds = roundsPerRoundRobin * numberOfRounds;

        IMatch[][] schedule = new IMatch[totalRounds][matchesPerRound];

        for (int r = 0; r < totalRounds; r++) {
            for (int m = 0; m < matchesPerRound; m++) {
                int home = (r + m) % (n - 1);
                int away = (n - 1 - m + r) % (n - 1);

                if (m == 0) away = n - 1;

                IClub homeClub = clubs[home];
                IClub awayClub = clubs[away];

                if (r >= roundsPerRoundRobin) {
                    // Segunda volta: troca casa/fora
                    IClub temp = homeClub;
                    homeClub = awayClub;
                    awayClub = temp;
                }

                schedule[r][m] = new Match(homeClub, awayClub); // Assume que tens uma classe Match com este construtor
            }
        }

        return schedule;
    }
}
