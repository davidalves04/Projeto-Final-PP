package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import java.io.IOException;

public class Schedule implements ISchedule {

    private final IMatch[][] rounds;
    private final IStanding[] standings;

    public Schedule(IMatch[][] rounds, ITeam[] teams) {
        this.rounds = rounds;
        this.standings = new IStanding[teams.length];

        for (int i = 0; i < teams.length; i++) {
            standings[i] = new Standing(teams[i]);
        }
    }

    @Override
    public IMatch[] getMatchesForRound(int i) {
        if (i < 0 || i >= rounds.length) return new IMatch[0];
        return rounds[i];
    }

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

    @Override
    public int getNumberOfRounds() {
        return rounds.length;
    }

    @Override
    public IMatch[] getAllMatches() {
        java.util.List<IMatch> allMatches = new java.util.ArrayList<>();
        for (IMatch[] round : rounds) {
            java.util.Collections.addAll(allMatches, round);
        }
        return allMatches.toArray(new IMatch[0]);
    }

    @Override
    public void setTeam(ITeam team, int index) {
        for (IMatch[] round : rounds) {
            for (IMatch match : round) {
                match.setTeam(team);
            }
        }

        standings[index] = new Standing(team);
    }

    public IStanding[] getStandings() {
        return standings;
    }

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

    private IStanding getStandingByClub(IClub club) {
        for (IStanding standing : standings) {
            if (standing.getTeam().getClub().equals(club)) {
                return standing;
            }
        }
        return null;
    }

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

    @Override
    public void exportToJson() {
        System.out.println("{");
        for (int i = 0; i < rounds.length; i++) {
            System.out.println("  \"round_" + (i + 1) + "\": [");
            for (int j = 0; j < rounds[i].length; j++) {
                try {
                    rounds[i][j].exportToJson();
                } catch (IOException e) {
                    System.err.println("Erro ao exportar jogo da jornada " + (i + 1) + ", posição " + (j + 1));
                    e.printStackTrace();
                }
                if (j < rounds[i].length - 1) System.out.println(",");
            }
            System.out.println("  ]" + (i < rounds.length - 1 ? "," : ""));
        }
        System.out.println("}");
    }
}
