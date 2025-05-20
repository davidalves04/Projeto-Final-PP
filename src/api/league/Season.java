package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;

public class Season implements ISeason {

    private final ISchedule schedule;     // Referência genérica
    private final Schedule concreteSchedule; // Referência concreta, para métodos extra
    private final int numberOfRounds;
    private int currentRound;

    public Season(IClub[] clubs) {
        this.numberOfRounds = (clubs.length - 1) * 2;
        this.schedule = new Schedule(clubs, clubs.length, numberOfRounds);

        // Garantido que o schedule é da classe Schedule concreta
        this.concreteSchedule = (Schedule) this.schedule;
        this.currentRound = 0;
    }

    @Override
    public void playNextRound(MatchSimulatorStrategy strategy) {
        if (currentRound < numberOfRounds) {
            concreteSchedule.simulateRound(currentRound, strategy);
            currentRound++;
        }
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public int getTotalRounds() {
        return numberOfRounds;
    }

    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    @Override
    public IMatch[] getMatchesForRound(int round) {
        return schedule.getMatchesForRound(round);
    }

    @Override
    public void resetSeason() {
        currentRound = 0;
        concreteSchedule.reset();
    }

    @Override
    public IStanding[] getStandings() {
        return concreteSchedule.getStandings();
    }

    @Override
    public int getTotalMatchesPlayed() {
        return concreteSchedule.getTotalMatchesPlayed();
    }
}
