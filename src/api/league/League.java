package api.league;

import api.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.league
import contracts.ISchedule;

public class League implements ILeague {
    private String name;
    private ITeam[] teams;
    private ISchedule schedule;

    public League(String name, ITeam[] teams, ISchedule schedule) {
        if (name == null || teams == null || schedule == null)
            throw new IllegalArgumentException("Nome, equipas e calendário não podem ser nulos");

        this.name = name;
        this.teams = teams;
        this.schedule = schedule;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ITeam[] getTeams() {
        return this.teams;
    }

    @Override
    public ISchedule getSchedule() {
        return this.schedule;
    }

    @Override
    public ITeam getTeamByName(String name) {
        for (ITeam team : teams) {
            if (team != null && team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }
}
