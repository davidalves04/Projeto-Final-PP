package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implementação de um calendário de jogos (Schedule) para uma competição de futebol.
 * Responsável por armazenar jornadas, resultados e estatísticas das equipas.
 */
public class Schedule implements ISchedule {

    private final IMatch[][] rounds;
    private final IStanding[] standings;
    private int matchCount;
    private String file;

    /**
     * Construtor da classe Schedule.
     *
     * @param rounds Matriz de jogos organizada por jornadas.
     * @param teams  Array com todas as equipas participantes.
     */
    public Schedule(IMatch[][] rounds, ITeam[] teams) {
        this.rounds = rounds;
        this.standings = new IStanding[teams.length];

        for (int i = 0; i < teams.length; i++) {
            standings[i] = new Standing(teams[i]);
        }
    }

    /**
     * Retorna todos os jogos de uma jornada específica.
     *
     * @param i Índice da jornada (começa em 0).
     * @return Array de jogos da jornada.
     */
    @Override
    public IMatch[] getMatchesForRound(int i) {
        if (i < 0 || i >= rounds.length) return new IMatch[0];
        return rounds[i];
    }

    /**
     * Retorna todos os jogos nos quais uma equipa participou.
     *
     * @param team A equipa a procurar.
     * @return Array de jogos da equipa.
     */
    @Override
    public IMatch[] getMatchesForTeam(ITeam team) {
        java.util.List<IMatch> matches = new java.util.ArrayList<>();
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                if (match.getHomeTeam() == team || match.getAwayTeam() == team) {
                    matches.add(match);
                }
            }
        }
        return matches.toArray(new IMatch[0]);
    }

    /**
     * Retorna o número total de jornadas no calendário.
     *
     * @return Número de jornadas.
     */
    @Override
    public int getNumberOfRounds() {
        return rounds.length;
    }

    /**
     * Retorna todos os jogos do calendário.
     *
     * @return Array de todos os jogos.
     */
    @Override
    public IMatch[] getAllMatches() {
        java.util.List<IMatch> allMatches = new java.util.ArrayList<>();
        for (IMatch[] round : rounds) {
            java.util.Collections.addAll(allMatches, round);
        }
        return allMatches.toArray(new IMatch[0]);
    }

    /**
     * Associa uma equipa ao seu respetivo índice no calendário e atualiza a classificação.
     *
     * @param team  A equipa a configurar.
     * @param index Índice da equipa.
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
     * Retorna o array de classificações (standings) das equipas.
     *
     * @return Array de standings.
     */
    public IStanding[] getStandings() {
        return standings;
    }

    /**
     * Simula todos os jogos do calendário que ainda não foram jogados
     * e atualiza as estatísticas de cada equipa.
     */
    public void simulate() {
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                if (!match.isPlayed()) {
                    match.setPlayed();

                    IClub home = match.getHomeClub();
                    IClub away = match.getAwayClub();

                    int homeGoals = match.getTotalByEvent(IGoalEvent.class, home);
                    int awayGoals = match.getTotalByEvent(IGoalEvent.class, away);

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
     * Procura a classificação associada a um determinado clube.
     *
     * @param club Clube a procurar.
     * @return Classificação correspondente ou null.
     */
    private IStanding getStandingByClub(IClub club) {
        for (IStanding standing : standings) {
            if (standing.getTeam().getClub().equals(club)) {
                return standing;
            }
        }
        return null;
    }

    /**
     * Reinicia o estado de todos os jogos e classificações.
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
     * Retorna o caminho do ficheiro de exportação.
     *
     * @return Caminho do ficheiro.
     */
    public String getFile() {
        return file;
    }

    /**
     * Define o caminho do ficheiro de exportação.
     *
     * @param file Caminho do ficheiro.
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Exporta o conteúdo do calendário para um ficheiro JSON.
     */
    @Override
    public void exportToJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("{\n");
            writer.write("\n  \"round_" + (matchCount + 1) + "\": \n");

            for (int i = 0; i < rounds.length; i++) {
                for (int j = 0; j < rounds[i].length; j++) {
                    rounds[i][j].exportToJson();
                }
            }

            writer.write("}\n");
        } catch (IOException e) {
            System.out.println("Erro ao exportar para JSON: " + e.getMessage());
        }

        this.matchCount++;
    }
}
