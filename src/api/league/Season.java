package api.league;

import api.data.TeamImporterJSON;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;
import utils.JsonAccumulator;

/**
 * Implementação da interface {@link ISeason} que representa uma época desportiva,
 * incluindo clubes, jornadas, calendário de jogos e regras de pontuação.
 */
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
    private int numberOfClubs;
    private MatchSimulatorStrategy simulator;
    private IMatch[][] generatedRounds;
    private ITeam[] teams;

    private JsonAccumulator jsonAccumulator;

    /**
     * Constrói uma época com os parâmetros fornecidos.
     *
     * @param name          nome da época
     * @param year          ano da época
     * @param maxTeams      número máximo de equipas permitidas na época
     * @param maxRounds     número máximo de voltas (exemplo: ida e volta)
     * @param pointsPerWin  pontos atribuídos por vitória
     * @param pointsPerDraw pontos atribuídos por empate
     * @param pointsPerLoss pontos atribuídos por derrota
     * @param clubs         array de clubes participantes
     */
    public Season(String name, int year, int maxTeams, int maxRounds,
                  int pointsPerWin, int pointsPerDraw, int pointsPerLoss,
                  IClub[] clubs) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.maxRounds = maxRounds;
        this.pointsPerWin = pointsPerWin;
        this.pointsPerDraw = pointsPerDraw;
        this.pointsPerLoss = pointsPerLoss;
        this.clubs = clubs;

        this.currentRound = 0;
        this.numberOfClubs = 0;
    }

    /**
     * Devolve o ano da época.
     *
     * @return ano da época
     */
    @Override
    public int getYear() {
        return year;
    }

    /**
     * Gera automaticamente os jogos da época utilizando um sistema round-robin.
     */
    public void generateMatchesAutomatically() {
        this.generatedRounds = MatchGenerator.generateRoundRobinMatches(clubs, maxRounds);
    }

    /**
     * Procura a posição de um clube no array de clubes.
     *
     * @param club clube a procurar
     * @return índice do clube se encontrado, -1 caso contrário
     */
    private int findClub(IClub club) {
        int i = 0, pos = -1;
        while (i < numberOfClubs && pos == -1) {
            if (clubs[i].getCode().equals(club.getCode())) {
                pos = i;
            }
            i++;
        }
        return pos;
    }

    /**
     * Adiciona um clube à época se ainda não tiver sido gerado o calendário e
     * se o limite máximo de equipas não tiver sido atingido.
     *
     * @param iclub clube a adicionar
     * @return true se o clube foi adicionado, false caso contrário
     */
    @Override
    public boolean addClub(IClub iclub) {
        if (this.schedule != null) {
            return false;
        }
        if (numberOfClubs >= maxTeams) {
            return false;
        }
        clubs[numberOfClubs++] = iclub;
        return true;
    }

    /**
     * Remove um clube da época se ainda não tiver sido gerado o calendário.
     *
     * @param iclub clube a remover
     * @return true se o clube foi removido, false caso contrário
     */
    @Override
    public boolean removeClub(IClub iclub) {
        if (schedule != null) {
            return false;
        }
        int pos = findClub(iclub);
        if (pos == -1) {
            return false;
        }
        for (int i = pos; i < numberOfClubs - 1; i++) {
            clubs[i] = clubs[i + 1];
        }
        clubs[--numberOfClubs] = null;
        return true;
    }

    /**
     * Gera o calendário da época a partir dos jogos gerados.
     */
    @Override
    public void generateSchedule() {
        this.schedule = new Schedule(generatedRounds, teams);
    }

    /**
     * Devolve todos os jogos da época.
     *
     * @return array com todos os jogos
     */
    @Override
    public IMatch[] getMatches() {
        return schedule.getAllMatches();
    }

    /**
     * Define as equipas a partir de um ficheiro JSON e os clubes.
     *
     * @param teamFile caminho do ficheiro JSON com as equipas
     * @param clubs    array de clubes
     * @throws IOException se ocorrer erro na leitura do ficheiro
     */
    public void setTeams(String teamFile, IClub[] clubs) throws IOException {
        this.teams = TeamImporterJSON.squadsFromJson(teamFile, clubs);
    }

    /**
     * Define os jogos gerados manualmente, por exemplo, ao importar de ficheiro JSON.
     *
     * @param matches array bidimensional de jogos
     */
    public void setMatches(IMatch[][] matches) {
        this.generatedRounds = matches;
    }

    /**
     * Devolve os jogos correspondentes a uma determinada jornada.
     *
     * @param round número da jornada
     * @return array de jogos da jornada
     */
    @Override
    public IMatch[] getMatches(int round) {
        return schedule.getMatchesForRound(round);
    }

    /**
     * Simula os jogos da jornada atual, atualizando o estado dos jogos.
     */
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

    /**
     * Simula toda a época, jornada a jornada, até à sua conclusão.
     */
    @Override
    public void simulateSeason() {
        while (!isSeasonComplete()) {
            simulateRound();
        }
    }

    /**
     * Devolve a jornada atual da época.
     *
     * @return número da jornada atual
     */
    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Indica se a época está completa, ou seja, se todas as jornadas foram jogadas.
     *
     * @return true se a época terminou, false caso contrário
     */
    @Override
    public boolean isSeasonComplete() {
        return currentRound >= schedule.getNumberOfRounds();
    }

    /**
     * Reinicia a época, colocando a jornada atual a zero e resetando o calendário.
     */
    @Override
    public void resetSeason() {
        this.currentRound = 0;
        if (schedule instanceof Schedule s) {
            s.reset();
        }
    }

    /**
     * Devolve uma representação textual do resultado de um jogo.
     *
     * @param match jogo a representar
     * @return string com o resultado formatado
     */
    @Override
    public String displayMatchResult(IMatch match) {
        return match.getHomeClub().getName() + " " +
               match.getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, match.getHomeClub()) +
               " - " +
               match.getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, match.getAwayClub()) +
               " " + match.getAwayClub().getName();
    }

    /**
     * Define a estratégia usada para simular os jogos.
     *
     * @param strategy estratégia de simulação
     */
    @Override
    public void setMatchSimulator(MatchSimulatorStrategy strategy) {
        this.simulator = strategy;
    }

    /**
     * Devolve a classificação da época, com a posição e pontos de cada equipa.
     *
     * @return array com os standings da liga
     */
    @Override
    public IStanding[] getLeagueStandings() {
        if (schedule instanceof Schedule s) {
            return s.getStandings();
        }
        return new IStanding[0];
    }

    /**
     * Devolve o calendário da época.
     *
     * @return objeto que representa o calendário de jogos
     */
    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    /**
     * Devolve os pontos atribuídos por vitória.
     *
     * @return pontos por vitória
     */
    @Override
    public int getPointsPerWin() {
        return pointsPerWin;
    }

    /**
     * Devolve os pontos atribuídos por empate.
     *
     * @return pontos por empate
     */
    @Override
    public int getPointsPerDraw() {
        return pointsPerDraw;
    }

    /**
     * Devolve os pontos atribuídos por derrota.
     *
     * @return pontos por derrota
     */
    @Override
    public int getPointsPerLoss() {
        return pointsPerLoss;
    }

    /**
     * Devolve o nome da época.
     *
     * @return nome da época
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Devolve o número máximo de equipas permitido na época.
     *
     * @return máximo de equipas
     */
    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    /**
     * Devolve o número máximo de voltas (jornadas) da época.
     *
     * @return máximo de voltas
     */
    @Override
    public int getMaxRounds() {
        return maxRounds;
    }

    /**
     * Devolve o número de jogos que já foram disputados.
     *
     * @return número de jogos disputados
     */
    @Override
    public int getCurrentMatches() {
        int count = 0;
        for (IMatch match : getMatches()) {
            if (match.isPlayed()) count++;
        }
        return count;
    }

    /**
     * Devolve o número atual de clubes presentes na época.
     *
     * @return número de clubes
     */
    @Override
    public int getNumberOfCurrentTeams() {
        return clubs.length;
    }

    /**
     * Devolve o array de clubes atualmente inscritos na época.
     *
     * @return array de clubes
     */
    @Override
    public IClub[] getCurrentClubs() {
        return clubs;
    }

    /**
     * Define o acumulador JSON usado para exportar dados.
     *
     * @param jsonAccumulator objeto para acumular dados JSON
     */
    public void setJsonAccumulator(JsonAccumulator jsonAccumulator) {
        this.jsonAccumulator = jsonAccumulator;
    }

    /**
     * Exporta os dados da época para formato JSON usando o acumulador.
     *
     * @throws IOException se ocorrer erro na escrita do ficheiro JSON
     */
    @Override
    public void exportToJson() throws IOException {
        jsonAccumulator.append("  {");
        jsonAccumulator.append("    \"name\": \"" + name + "\",");
        jsonAccumulator.append("    \"year\": " + year + ",");
        jsonAccumulator.append("    \"maxTeams\": " + maxTeams + ",");
        jsonAccumulator.append("    \"maxRounds\": " + maxRounds + ",");
        jsonAccumulator.append("    \"pointsPerWin\": " + pointsPerWin + ",");
        jsonAccumulator.append("    \"pointsPerDraw\": " + pointsPerDraw + ",");
        jsonAccumulator.append("    \"pointsPerLoss\": " + pointsPerLoss + ",");

        ((Schedule) schedule).setJsonAccumulator(jsonAccumulator);
        schedule.exportToJson();

        jsonAccumulator.append("  }");
    }

}
