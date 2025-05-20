package api.league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Standing implements IStanding {

    private IClub club;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;

    public Standing(IClub club) {
        this.club = club;
    }

    @Override
    public ITeam getTeam() {
        return club;
    }

    @Override
    public int getPoints() {
        return (wins * 3) + draws;
    }

    @Override
    public void addPoints(int points) {
        // Não usado, pois pontos são derivados de vitórias/empates.
    }

    @Override
    public void addWin(int goalsScored) {
        this.wins++;
        this.goalsScored += goalsScored;
    }

    @Override
    public void addDraw(int goalsScored) {
        this.draws++;
        this.goalsScored += goalsScored;
    }

    @Override
    public void addLoss(int goalsScored) {
        this.losses++;
        this.goalsScored += goalsScored;
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

    public IClub getClub() {
        return club;
    }

    // Método usado por Schedule para atualizar as estatísticas
    public void updateStats(int scored, int conceded) {
        this.goalsScored += scored;
        this.goalsConceded += conceded;

        if (scored > conceded) {
            addWin(scored);
        } else if (scored == conceded) {
            addDraw(scored);
        } else {
            addLoss(scored);
        }
    }

    public void reset() {
        wins = 0;
        draws = 0;
        losses = 0;
        goalsScored = 0;
        goalsConceded = 0;
    }
}
