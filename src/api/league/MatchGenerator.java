/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
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

        // Número de jornadas por volta (round-robin simples)
        int roundsPerRoundRobin = n - 1;
        int matchesPerRound = n / 2;

        // Total de jornadas considerando todas as voltas
        int totalRounds = roundsPerRoundRobin * numberOfRounds;

        IMatch[][] schedule = new IMatch[totalRounds][matchesPerRound];

        for (int r = 0; r < totalRounds; r++) {
            for (int m = 0; m < matchesPerRound; m++) {
                int home = (r + m) % (n - 1);
                int away = (n - 1 - m + r) % (n - 1);

                if (m == 0) away = n - 1;

                ITeam homeClub = clubs[home];
                ITeam awayClub = clubs[away];

                if (r >= roundsPerRoundRobin) {
                    // Segunda volta: inverter casa/fora
                    ITeam temp = homeClub;
                    homeClub = awayClub;
                    awayClub = temp;
                }

                schedule[r][m] = new Match(homeClub, awayClub);
            }
        }

        return schedule;
    }
}
