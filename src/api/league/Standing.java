/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import utils.JsonAccumulator;

/**
 * Implementação da interface {@link IStanding} que representa a classificação
 * de uma equipa numa liga durante uma época desportiva.
 */
public class Standing implements IStanding {

    private ITeam team;
    private int points;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;
    
    private String file;
    
     private JsonAccumulator jsonAccumulator;

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
     * Devolve a equipa associada a esta classificação.
     *
     * @return a equipa desta classificação
     */
    @Override
    public ITeam getTeam() {
        return team;
    }

    public void setTeam(ITeam team){
        this.team = team;
    }
    /**
     * Devolve o total de pontos obtidos pela equipa.
     *
     * @return total de pontos
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * Acrescenta um número de pontos à classificação da equipa.
     *
     * @param p número de pontos a adicionar
     */
    @Override
    public void addPoints(int p) {
        points += p;
    }

    /**
     * Acrescenta um número de vitórias à classificação da equipa.
     *
     * @param i número de vitórias a adicionar
     */
    @Override
    public void addWin(int i) {
        wins += i;
    }

    /**
     * Acrescenta um número de empates à classificação da equipa.
     *
     * @param i número de empates a adicionar
     */
    @Override
    public void addDraw(int i) {
        draws += i;
    }

    /**
     * Acrescenta um número de derrotas à classificação da equipa.
     *
     * @param i número de derrotas a adicionar
     */
    @Override
    public void addLoss(int i) {
        losses += i;
    }

    /**
     * Devolve o número total de vitórias da equipa.
     *
     * @return total de vitórias
     */
    @Override
    public int getWins() {
        return wins;
    }

    /**
     * Devolve o número total de empates da equipa.
     *
     * @return total de empates
     */
    @Override
    public int getDraws() {
        return draws;
    }

    /**
     * Devolve o número total de derrotas da equipa.
     *
     * @return total de derrotas
     */
    @Override
    public int getLosses() {
        return losses;
    }

    /**
     * Devolve o número total de jogos disputados pela equipa.
     *
     * @return total de jogos (vitórias + empates + derrotas)
     */
    @Override
    public int getTotalMatches() {
        return wins + draws + losses;
    }

    /**
     * Devolve o total de golos marcados pela equipa.
     *
     * @return total de golos marcados
     */
    @Override
    public int getGoalScored() {
        return goalsScored;
    }

    /**
     * Devolve o total de golos sofridos pela equipa.
     *
     * @return total de golos sofridos
     */
    @Override
    public int getGoalsConceded() {
        return goalsConceded;
    }

    /**
     * Devolve a diferença entre golos marcados e golos sofridos.
     *
     * @return diferença de golos (marcados - sofridos)
     */
    @Override
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }

    /**
     * Adiciona um número de golos marcados às estatísticas da equipa.
     *
     * @param goals número de golos marcados a adicionar
     */
    public void addGoalsScored(int goals) {
        goalsScored += goals;
    }

    /**
     * Adiciona um número de golos sofridos às estatísticas da equipa.
     *
     * @param goals número de golos sofridos a adicionar
     */
    public void addGoalsConceded(int goals) {
        goalsConceded += goals;
    }

    /**
     * Atualiza todas as estatísticas da equipa com base nos golos marcados e sofridos num jogo,
     * ajustando pontos, vitórias, empates e derrotas conforme o resultado.
     *
     * @param goalsScored   número de golos marcados pela equipa no jogo
     * @param goalsConceded número de golos sofridos pela equipa no jogo
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
     * Reinicia todas as estatísticas da equipa para os valores iniciais.
     */
    public void reset() {
        this.points = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

 
    
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setJsonAccumulator(JsonAccumulator jsonAccumulator) {
        this.jsonAccumulator = jsonAccumulator;
    }
    
    
 
    
    public void exportToJson(){
         if (jsonAccumulator == null) {
        System.err.println("JsonAccumulator não definido para Standing!");
        return;
    }

    jsonAccumulator.append("{");
    jsonAccumulator.append("  \"team\": {");
    jsonAccumulator.append("    \"clubName\": \"" + this.team.getClub().getName() + "\",");
    jsonAccumulator.append("    \"formation\": \"" + this.team.getFormation().getDisplayName() + "\",");
    jsonAccumulator.append("    \"teamStrength\": " + this.team.getTeamStrength());
    jsonAccumulator.append("  },");
    jsonAccumulator.append("  \"points\": " + this.points + ",");
    jsonAccumulator.append("  \"wins\": " + this.wins + ",");
    jsonAccumulator.append("  \"draws\": " + this.draws + ",");
    jsonAccumulator.append("  \"losses\": " + this.losses + ",");
    jsonAccumulator.append("  \"goalsscored\": " + this.goalsScored + ",");
    jsonAccumulator.append("  \"goalsconceded\": " + this.goalsConceded);
    jsonAccumulator.append("}");
    }
    
}
