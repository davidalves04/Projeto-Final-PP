/**
 * A classe {@code Schedule} implementa a interface {@code ISchedule} e representa a
 * agenda de partidas de uma liga, incluindo os jogos por jornada e a classificação
 * das equipas.
 *
 * <p>Esta classe permite obter jogos por jornada, por equipa, exportar dados em JSON,
 * simular jogos e atualizar classificações automaticamente.
 */
package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;

import java.io.IOException;
import utils.JsonAccumulator;

public class Schedule implements ISchedule {

    private final IMatch[][] rounds;
    private final IStanding[] standings;
    private JsonAccumulator jsonAccumulator;

    /**
     * Cria um novo {@code Schedule} com as jornadas e as equipas especificadas.
     *
     * @param rounds matriz de partidas organizadas por jornada
     * @param teams  vetor de equipas participantes na liga
     */
    public Schedule(IMatch[][] rounds, ITeam[] teams) {
        this.rounds = rounds;
        this.standings = new IStanding[teams.length];

        for (int i = 0; i < teams.length; i++) {
            standings[i] = new Standing(teams[i]);
        }
    }

    /**
     * Retorna os jogos de uma jornada específica.
     *
     * @param i índice da jornada (base 0)
     * @return vetor de partidas da jornada, ou vetor vazio se o índice for inválido
     */
    @Override
    public IMatch[] getMatchesForRound(int i) {
        if (i < 0 || i >= rounds.length) return new IMatch[0];
        return rounds[i];
    }

    /**
     * Retorna todos os jogos em que uma equipa participou.
     *
     * @param team equipa desejada
     * @return vetor de partidas da equipa
     */
    @Override
    public IMatch[] getMatchesForTeam(ITeam team) {
        int count = 0;
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                if (match.getHomeTeam() == team || match.getAwayTeam() == team) {
                    count++;
                }
            }
        }

        IMatch[] matches = new IMatch[count];
        int index = 0;
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                if (match.getHomeTeam() == team || match.getAwayTeam() == team) {
                    matches[index++] = match;
                }
            }
        }

        return matches;
    }

    /**
     * Retorna o número total de jornadas.
     *
     * @return número de jornadas
     */
    @Override
    public int getNumberOfRounds() {
        return rounds.length;
    }

    /**
     * Retorna todas as partidas da época.
     *
     * @return vetor com todas as partidas
     */
    @Override
    public IMatch[] getAllMatches() {
        int total = 0;
        for (IMatch[] round : rounds) {
            total += round.length;
        }

        IMatch[] allMatches = new IMatch[total];
        int index = 0;
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                allMatches[index++] = match;
            }
        }

        return allMatches;
    }

    /**
     * Atualiza a equipa no índice especificado e nos jogos da agenda.
     *
     * @param team  nova equipa
     * @param index índice da equipa na classificação
     */
    @Override
    public void setTeam(ITeam team, int index) {
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                match.setTeam(team);
            }
        }
        standings[index] = new Standing(team);
    }

    /**
     * Retorna a classificação atual de todas as equipas.
     *
     * @return vetor de classificações
     */
    public IStanding[] getStandings() {
        return standings;
    }

    /**
     * Simula todos os jogos ainda não jogados, atualizando as estatísticas das equipas.
     */
    public void simulate() {
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                if (!match.isPlayed()) {
                    match.setPlayed();

                    ITeam home = match.getHomeTeam();
                    ITeam away = match.getAwayTeam();

                    int homeGoals = match.getTotalByEvent(IGoalEvent.class, home.getClub());
                    int awayGoals = match.getTotalByEvent(IGoalEvent.class, away.getClub());

                    IStanding homeStanding = getStandingByClub(home);
                    IStanding awayStanding = getStandingByClub(away);

                    if (homeStanding instanceof Standing h && awayStanding instanceof Standing a) {
                        h.updateStats(homeGoals, awayGoals);
                        a.updateStats(awayGoals, homeGoals);
                    }
                }
            }
        }
    }

    /**
     * Obtém a classificação correspondente a uma determinada equipa.
     *
     * @param team equipa desejada
     * @return classificação da equipa, ou {@code null} se não encontrada
     */
    public IStanding getStandingByClub(ITeam team) {
        String teamName = team.getClub().getName();
        for (IStanding standing : standings) {
            if (standing.getTeam().getClub().getName().equals(teamName)) {
                return standing;
            }
        }
        return null;
    }

    /**
     * Reinicia todos os jogos e estatísticas das equipas.
     */
    public void reset() {
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                if (match instanceof Match m) {
                    m.reset();
                }
            }
        }

        for (IStanding standing : standings) {
            if (standing instanceof Standing s) {
                s.reset();
            }
        }
    }

    /**
     * Define o {@code JsonAccumulator} usado para exportação dos dados em JSON.
     *
     * @param jsonAccumulator acumulador JSON a utilizar
     */
    public void setJsonAccumulator(JsonAccumulator jsonAccumulator) {
        this.jsonAccumulator = jsonAccumulator;
    }

    /**
     * Exporta os dados das jornadas e respetivos jogos para JSON.
     *
     * @throws IOException se ocorrer erro durante a escrita
     */
    @Override
    public void exportToJson() throws IOException {
        jsonAccumulator.append("  \"rounds\": {");

        for (int i = 0; i < rounds.length; i++) {
            jsonAccumulator.append("    \"round_" + (i + 1) + "\": [");

            for (int j = 0; j < rounds[i].length; j++) {
                ((Match) rounds[i][j]).setJsonAccumulator(jsonAccumulator);
                rounds[i][j].exportToJson();

                if (j < rounds[i].length - 1) {
                    jsonAccumulator.append(",");
                }
            }

            jsonAccumulator.append("    ]" + (i < rounds.length - 1 ? "," : ""));
        }

        jsonAccumulator.append("  }");
    }
}
