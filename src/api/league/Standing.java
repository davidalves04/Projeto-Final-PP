package api.league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 * Implementação da interface {@link IStanding} que representa a classificação
 * de uma equipa numa liga durante uma época desportiva.
 */
public class Standing implements IStanding {

    private final ITeam team;
    private int points;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;

    /**
     * Constrói um novo objeto {@code Standing} para a equipa especificada.
     *
     * @param team equipa associada a esta classificação
     */
    public Standing(ITeam team) {
        this.team = team;
        this.points = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITeam getTeam() {
        return team;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPoints(int p) {
        points += p;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWin(int i) {
        wins += i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDraw(int i) {
        draws += i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLoss(int i) {
        losses += i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWins() {
        return wins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDraws() {
        return draws;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLosses() {
        return losses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalMatches() {
        return wins + draws + losses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGoalScored() {
        return goalsScored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGoalsConceded() {
        return goalsConceded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }

    /**
     * Adiciona um número de golos marcados à estatística atual.
     *
     * @param goals número de golos marcados a adicionar
     */
    public void addGoalsScored(int goals) {
        goalsScored += goals;
    }

    /**
     * Adiciona um número de golos sofridos à estatística atual.
     *
     * @param goals número de golos sofridos a adicionar
     */
    public void addGoalsConceded(int goals) {
        goalsConceded += goals;
    }

    /**
     * Atualiza todas as estatísticas da classificação com base nos golos
     * marcados e sofridos de um jogo.
     *
     * @param goalsScored     número de golos marcados pela equipa
     * @param goalsConceded   número de golos sofridos pela equipa
     */
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

    /**
     * Reinicia todas as estatísticas da classificação para os valores iniciais.
     */
    public void reset() {
        this.points = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }
}
