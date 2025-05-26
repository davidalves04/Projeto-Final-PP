package api.league;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class Match implements IMatch {

    private final IClub home;
    private final IClub away;
    private boolean played;
    private ITeam homeTeam;
    private ITeam awayTeam;
    private int round;
    private IEvent[] events;
    private int eventCount;

    public Match(IClub home, IClub away) {
        this.home = home;
        this.away = away;
        this.played = false;
        this.events = new IEvent[100]; // tamanho máximo arbitrário
        this.eventCount = 0;
    }

    @Override
    public IClub getHomeClub() {
        return home;
    }

    @Override
    public IClub getAwayClub() {
        return away;
    }

    @Override
    public boolean isPlayed() {
        return played;
    }

    @Override
    public ITeam getHomeTeam() {
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        return awayTeam;
    }

    @Override
    public void setPlayed() {
        this.played = true;
    }

    @Override
    public int getTotalByEvent(Class type, IClub club) {
        int count = 0;
        for (int i = 0; i < eventCount; i++) {
            IEvent e = events[i];
            if (type.isInstance(e) && e instanceof IGoalEvent) {
                IGoalEvent ge = (IGoalEvent) e;
                IPlayer[] jogadores = club.getPlayers();
                if (jogadores != null) {
                    for (IPlayer p : jogadores) {
                        if (p.equals(ge.getPlayer())) {
                            count++;
                            break;
                        }
                    }
                }
            }
        }
        return count;
    }

    @Override
    public boolean isValid() {
        return home != null && away != null && !home.equals(away);
    }

    @Override
    public ITeam getWinner() {
        int homeGoals = getTotalByEvent(IGoalEvent.class, home);
        int awayGoals = getTotalByEvent(IGoalEvent.class, away);

        if (homeGoals > awayGoals) return homeTeam;
        if (awayGoals > homeGoals) return awayTeam;
        return null;
    }

    @Override
    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public void setTeam(ITeam team) {
        if (homeTeam == null) {
            homeTeam = team;
        } else if (awayTeam == null) {
            awayTeam = team;
        }
    }

    @Override
    public void exportToJson() {
        System.out.println("{ \"home\": \"" + home.getName() + "\", \"away\": \"" + away.getName() + "\", \"played\": " + played + " }");
    }

    @Override
    public void addEvent(IEvent event) {
        if (eventCount < events.length) {
            events[eventCount++] = event;
        }
    }

    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            result[i] = events[i];
        }
        return result;
    }

    @Override
    public int getEventCount() {
        return eventCount;
    }

    public void reset() {
        played = false;
        eventCount = 0;
    }
} 
