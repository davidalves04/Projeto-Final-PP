package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Schedule implements ISchedule {

    private IMatch[][] rounds;
    private int totalRounds;
    private int teams;
    private IStanding[] standings;

    public Schedule(IClub[] clubs, int numberOfTeams, int maxRounds) {
        this.teams = numberOfTeams;
        this.totalRounds = maxRounds;
        this.rounds = new IMatch[totalRounds][numberOfTeams / 2];
        this.standings = new Standing[numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            standings[i] = new Standing(clubs[i]);
        }

        generateRoundRobin(clubs);
    }

    private void generateRoundRobin(IClub[] clubs) {
        int n = teams;
        IClub[] temp = new IClub[n];
        for (int i = 0; i < n; i++) {
            temp[i] = clubs[i];
        }

        for (int round = 0; round < n - 1; round++) {
            for (int i = 0; i < n / 2; i++) {
                IClub home = temp[i];
                IClub away = temp[n - 1 - i];
                rounds[round][i] = new Match(home, away); // ida
                rounds[round + (n - 1)][i] = new Match(away, home); // volta
            }

            // Rotação dos clubes (fixando o primeiro)
            IClub last = temp[n - 1];
            for (int i = n - 1; i > 1; i--) {
                temp[i] = temp[i - 1];
            }
            temp[1] = last;
        }
    }

    @Override
    public IMatch[] getAllMatches() {
        int totalMatches = totalRounds * (teams / 2);
        IMatch[] all = new IMatch[totalMatches];
        int index = 0;
        for (int i = 0; i < totalRounds; i++) {
            for (int j = 0; j < rounds[i].length; j++) {
                all[index++] = rounds[i][j];
            }
        }
        return all;
    }

    @Override
    public IMatch[] getMatchesForRound(int round) {
        if (round >= 0 && round < totalRounds) {
            return rounds[round];
        }
        return new IMatch[0];
    }

    @Override
    public void simulateRound(int round, MatchSimulatorStrategy strategy) {
        if (round < 0 || round >= totalRounds) return;

        for (int i = 0; i < rounds[round].length; i++) {
            IMatch match = rounds[round][i];
            strategy.simulate(match);
            updateStandings(match);
        }
    }

    private void updateStandings(IMatch match) {
        IClub home = match.getHomeTeam();
        IClub away = match.getAwayTeam();
        int homeGoals = match.getHomeGoals();
        int awayGoals = match.getAwayGoals();

        Standing sh = findStanding(home);
        Standing sa = findStanding(away);

        sh.updateStats(homeGoals, awayGoals);
        sa.updateStats(awayGoals, homeGoals);
    }

    private Standing findStanding(IClub club) {
        for (int i = 0; i < standings.length; i++) {
            if (standings[i].getClub().equals(club)) {
                return (Standing) standings[i]; // ⬅️ Downcast explicado abaixo
            }
        }
        return null;
    }

    @Override
    public int getTotalMatchesPlayed() {
        int count = 0;
        for (int i = 0; i < totalRounds; i++) {
            for (int j = 0; j < rounds[i].length; j++) {
                if (rounds[i][j].wasPlayed()) count++;
            }
        }
        return count;
    }

    @Override
    public void reset() {
        for (int i = 0; i < totalRounds; i++) {
            for (int j = 0; j < rounds[i].length; j++) {
                rounds[i][j].reset();
            }
        }
        for (int i = 0; i < standings.length; i++) {
            ((Standing) standings[i]).reset(); // ⬅️ Downcast: temos IStanding, mas o método reset() só existe em Standing
        }
    }

    @Override
    public IStanding[] getStandings() {
        return standings;
    }

    // 🔹 Método adicional exigido pelo contrato ISchedule
    @Override
    public IMatch[] getMatchesForTeam(ITeam iteam) {
        IClub club = (IClub) iteam; // ⬅️ Downcast: estamos a assumir que iteam é um IClub, pois o Schedule só trabalha com IClub
        int count = 0;

        // 1. Contar o número de jogos para alocar array
        for (int i = 0; i < totalRounds; i++) {
            for (IMatch match : rounds[i]) {
                if (match.getHomeTeam().equals(club) || match.getAwayTeam().equals(club)) {
                    count++;
                }
            }
        }

        // 2. Preencher o array com os jogos
        IMatch[] result = new IMatch[count];
        int index = 0;
        for (int i = 0; i < totalRounds; i++) {
            for (IMatch match : rounds[i]) {
                if (match.getHomeTeam().equals(club) || match.getAwayTeam().equals(club)) {
                    result[index++] = match;
                }
            }
        }

        return result;
    }

    // 🔹 Método adicional exigido pelo contrato ISchedule
    @Override
    public void setTeam(ITeam iteam, int i) {
        // Este método não tem uso real aqui, pois os clubes já são definidos no construtor
        // Mas vamos implementá-lo de forma segura:
        if (i >= 0 && i < standings.length) {
            IClub club = (IClub) iteam; // ⬅️ Downcast: garantimos que iteam é do tipo IClub
            standings[i] = new Standing(club);
        }
    }
}
