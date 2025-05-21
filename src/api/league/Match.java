package api.league;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;

import java.util.ArrayList;
import java.util.List;

public class Match implements IMatch {

    private final IClub home;
    private final IClub away;
    private boolean played;
    private ITeam homeTeam;
    private ITeam awayTeam;
    private int round;
    private final List<IEvent> events;

    public Match(IClub home, IClub away) {
        this.home = home;
        this.away = away;
        this.played = false;
        this.events = new ArrayList<>();
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
        for (IEvent e : events) {
            if (type.isInstance(e)) {
                if (e instanceof IGoalEvent) {
                    IGoalEvent ge = (IGoalEvent) e;
                    if (club.getPlayers() != null) {
                        for (var p : club.getPlayers()) {
                            if (p.equals(ge.getPlayer())) {
                                count++;
                                break;
                            }
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
        // Simplificado: número de eventos de tipo "golo"
        int homeGoals = getTotalByEvent(IGoalEvent.class, home);
        int awayGoals = getTotalByEvent(IGoalEvent.class, away);


        if (homeGoals > awayGoals) return homeTeam;
        if (awayGoals > homeGoals) return awayTeam;
        return null; // empate
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
        events.add(event);
    }

    @Override
    public IEvent[] getEvents() {
        return events.toArray(new IEvent[0]);
    }

    @Override
    public int getEventCount() {
        return events.size();
    }

    public void reset() {
        played = false;
        events.clear();
    }
}
