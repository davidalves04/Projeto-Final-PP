package api.league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Standing implements IStanding {

    private final ITeam team;
    private int points;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;

    public Standing(ITeam team) {
        this.team = team;
        this.points = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }

    @Override
    public ITeam getTeam() {
        return team;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void addPoints(int p) {
        points += p;
    }

    @Override
    public void addWin(int i) {
        wins += i;
    }

    @Override
    public void addDraw(int i) {
        draws += i;
    }

    @Override
    public void addLoss(int i) {
        losses += i;
    }

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public int getDraws() {
        return draws;
    }

    @Override
    public int getLosses() {
        return losses;
    }

    @Override
    public int getTotalMatches() {
        return wins + draws + losses;
    }

    @Override
    public int getGoalScored() {
        return goalsScored;
    }

    @Override
    public int getGoalsConceded() {
        return goalsConceded;
    }

    @Override
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }

    // Métodos auxiliares para atualizar os golos (opcional)
    public void addGoalsScored(int goals) {
        goalsScored += goals;
    }

    public void addGoalsConceded(int goals) {
        goalsConceded += goals;
    }
    
    public void updateStats(int goalsScored, int goalsConceded) {
    this.goalsScored += goalsScored;
    this.goalsConceded += goalsConceded;

    if (goalsScored > goalsConceded) {
        addPoints(3);
        addWin(1);
    } else if (goalsScored == goalsConceded) {
        addPoints(1);
        addDraw(1);
    } else {
        addLoss(1);
    }
}

    public void reset() {
        this.points = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }
}
