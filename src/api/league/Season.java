package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;

public class Season implements ISeason {

    private final String name;
    private final int year;
    private final int maxTeams;
    private final int maxRounds;
    private final int pointsPerWin;
    private final int pointsPerDraw;
    private final int pointsPerLoss;

    private final IClub[] clubs;
    private ISchedule schedule;
    private int currentRound;
    private MatchSimulatorStrategy simulator;

    public Season(String name, int year, int maxTeams, int maxRounds, int pointsPerWin, int pointsPerDraw, int pointsPerLoss, IClub[] clubs) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.maxRounds = maxRounds;
        this.pointsPerWin = pointsPerWin;
        this.pointsPerDraw = pointsPerDraw;
        this.pointsPerLoss = pointsPerLoss;
        this.clubs = clubs;
        this.currentRound = 0;

        // Exemplo de construção do calendário de jogos
        IMatch[][] generatedRounds = MatchGenerator.generateRoundRobinMatches(clubs, maxRounds); // Deves implementar isto ou adaptar
        ITeam[] teams = java.util.Arrays.copyOf(clubs, clubs.length, ITeam[].class);
        this.schedule = new Schedule(generatedRounds, teams);
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public boolean addClub(IClub iclub) {
        return false; // funcionalidade não suportada após construção
    }

    @Override
    public boolean removeClub(IClub iclub) {
        return false; // funcionalidade não suportada após construção
    }

    @Override
    public void generateSchedule() {
        // já gerado no construtor
    }

    @Override
    public IMatch[] getMatches() {
        return schedule.getAllMatches();
    }

    @Override
    public IMatch[] getMatches(int round) {
        return schedule.getMatchesForRound(round);
    }

    @Override
    public void simulateRound() {
        if (!isSeasonComplete()) {
            IMatch[] matches = schedule.getMatchesForRound(currentRound);
            for (IMatch match : matches) {
                if (!match.isPlayed()) {
                    simulator.simulate(match);
                    match.setPlayed();
                }
            }
            currentRound++;
        }
    }

    @Override
    public void simulateSeason() {
        while (!isSeasonComplete()) {
            simulateRound();
        }
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public boolean isSeasonComplete() {
        return currentRound >= schedule.getNumberOfRounds();
    }

    @Override
    public void resetSeason() {
        this.currentRound = 0;
        if (schedule instanceof Schedule s) {
            s.reset();
        }
    }

    @Override
    public String displayMatchResult(IMatch match) {
        return match.getHomeClub().getName() + " " +
               match.getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, match.getHomeClub()) +
               " - " +
               match.getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, match.getAwayClub()) +
               " " + match.getAwayClub().getName();
    }

    @Override
    public void setMatchSimulator(MatchSimulatorStrategy strategy) {
        this.simulator = strategy;
    }

    @Override
    public IStanding[] getLeagueStandings() {
        if (schedule instanceof Schedule s) {
            return s.getStandings();
        }
        return new IStanding[0];
    }

    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    @Override
    public int getPointsPerWin() {
        return pointsPerWin;
    }

    @Override
    public int getPointsPerDraw() {
        return pointsPerDraw;
    }

    @Override
    public int getPointsPerLoss() {
        return pointsPerLoss;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    @Override
    public int getMaxRounds() {
        return maxRounds;
    }

    @Override
    public int getCurrentMatches() {
        int count = 0;
        for (IMatch match : getMatches()) {
            if (match.isPlayed()) count++;
        }
        return count;
    }

    @Override
    public int getNumberOfCurrentTeams() {
        return clubs.length;
    }

    @Override
    public IClub[] getCurrentClubs() {
        return clubs;
    }

    @Override
    public void exportToJson() throws IOException {
        schedule.exportToJson();
    }
}
