package api.league;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 * Classe utilitária responsável pela geração de jogos em formato round-robin
 * entre clubes de futebol.
 */
public class MatchGenerator {

    /**
     * Gera um conjunto de jornadas utilizando o algoritmo round-robin.
     * Cada jornada contém um conjunto de jogos entre os clubes fornecidos.
     *
     * @param clubs           Array com os clubes participantes.
     * @param numberOfRounds  Número de voltas (por exemplo, 2 para ida e volta).
     * @return Uma matriz onde cada linha representa uma jornada, e cada coluna um jogo.
     */
    public static IMatch[][] generateRoundRobinMatches(ITeam[] clubs, int numberOfRounds) {
        int n = clubs.length;

        if (n % 2 != 0) {
            throw new IllegalArgumentException("Número de equipas deve ser par.");
        }

        int roundsPerRoundRobin = n - 1;
        int matchesPerRound = n / 2;
        int totalRounds = roundsPerRoundRobin * numberOfRounds;

        IMatch[][] schedule = new IMatch[totalRounds][matchesPerRound];

        // Índices base para a primeira volta
        for (int round = 0; round < roundsPerRoundRobin; round++) {
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (n - 1);
                int away = (n - 1 - match + round) % (n - 1);

                if (match == 0) {
                    away = n - 1; // Última equipa é fixa
                }

                schedule[round][match] = new Match(clubs[home], clubs[away]);
            }
        }

        // Gerar voltas adicionais invertendo casa/fora
        for (int round = 0; round < totalRounds; round++) {
            for (int match = 0; match < matchesPerRound; match++) {
                int baseRound = round % roundsPerRoundRobin;
                int home = (baseRound + match) % (n - 1);
                int away = (n - 1 - match + baseRound) % (n - 1);

                if (match == 0) {
                    away = n - 1;
                }

                ITeam homeClub = clubs[home];
                ITeam awayClub = clubs[away];

                // Alternar casa/fora em voltas ímpares
                if ((round / roundsPerRoundRobin) % 2 == 1) {
                    ITeam temp = homeClub;
                    homeClub = awayClub;
                    awayClub = temp;
                }

                schedule[round][match] = new Match(homeClub, awayClub);
            }
        }

        return schedule;
    }
}