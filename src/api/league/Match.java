package api.league;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

public class Match implements IMatch {

    private final IClub home;
    private final IClub away;
    private int homeGoals;
    private int awayGoals;
    private boolean played;

    public Match(IClub home, IClub away) {
        this.home = home;
        this.away = away;
        this.played = false;
    }

    @Override
    public IClub getHomeTeam() {
        return home;
    }

    @Override
    public IClub getAwayTeam() {
        return away;
    }

    @Override
    public int getHomeGoals() {
        return homeGoals;
    }

    @Override
    public int getAwayGoals() {
        return awayGoals;
    }

    @Override
    public boolean wasPlayed() {
        return played;
    }

    @Override
    public void setResult(int homeGoals, int awayGoals) {
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.played = true;
    }

    @Override
    public void reset() {
        homeGoals = 0;
        awayGoals = 0;
        played = false;
    }
}
