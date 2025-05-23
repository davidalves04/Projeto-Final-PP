package api.simulation;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

public class LeagueSimulator {

    private final ILeague league;
    private final ISeason season;

    public LeagueSimulator(ILeague league) {
        this.league = league;
        this.season = league.getSeason(0); // Podes parametrizar depois se quiseres outra season

        if (this.season == null) {
            throw new IllegalStateException("Não existe nenhuma temporada criada na liga!");
        }
    }

    public void setMatchSimulator(MatchSimulatorStrategy strategy) {
        season.setMatchSimulator(strategy);
    }

    public void simulateRound() {
        season.simulateRound(); // A season trata da simulação corretamente
    }

    public void simulateSeason() {
        season.simulateSeason();
    }

    public boolean isComplete() {
        return season.isSeasonComplete();
    }
}
