package api.league;

import api.data.TeamImporterJSON;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import utils.JsonAccumulator;

/**
 * Implementação da interface {@link ISeason} que representa uma época desportiva
 * com clubes, jornadas, calendário de jogos e regras de pontuação.
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
     * Constrói uma época com os parâmetros dados.
     *
     * @param name           nome da época
     * @param year           ano da época
     * @param maxTeams       número máximo de equipas
     * @param maxRounds      número máximo de voltas (ida e volta)
     * @param pointsPerWin   pontos atribuídos por vitória
     * @param pointsPerDraw  pontos atribuídos por empate
     * @param pointsPerLoss  pontos atribuídos por derrota
     * @param clubs          clubes participantes
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
     * {@inheritDoc}
     */
    @Override
    public int getYear() {
        return year;
    }


    /**
     * {@inheritDoc}
     */
   
    public void generateMatchesAutomatically() {
    this.generatedRounds = MatchGenerator.generateRoundRobinMatches(clubs, maxRounds);
    
}
    
    
     private int findClub(IClub club) {
    int i = 0, pos = -1;
    while (i < numberOfClubs && pos == -1) {
        if (clubs[i].getCode().equals(club.getCode())) { 
            pos = i;
        }
        i++;
    }
    
    
    return pos; //Devolve a posição caso seja encontrado senão ira retornar -1
}
    
    
    @Override
    public boolean addClub(IClub iclub) {
        //Se o schedule for gerado nao pode adicionar
        if (this.schedule != null) {
        return false;
    }

    // Não pode adicionar se já atingiu o máximo
    if (numberOfClubs >= maxTeams) {
        return false;
    }

    clubs[numberOfClubs++] = iclub;
    return true;
       

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeClub(IClub iclub) {

    
      // Se o schedule já foi gerado, não permite remover
    if (schedule != null) {
        return false;
    }

    int pos = findClub(iclub);
    if (pos == -1) {
        return false; // clube não encontrado
    }

    // Remove o clube deslocando os elementos do array
    for (int i = pos; i < numberOfClubs - 1; i++) {
        clubs[i] = clubs[i + 1];
    }

    clubs[--numberOfClubs] = null;

    return true; // remoção bem sucedida
}
    


    @Override
    public void generateSchedule() {
        this.schedule = new Schedule(generatedRounds, teams); //Chamar o metodo para gerar o Schedule
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMatch[] getMatches() {
        return schedule.getAllMatches();
    }


    /**
     * {@inheritDoc}
     */

    
    public void setTeams(String teamFile,IClub[] clubs) throws IOException{
        
        this.teams = TeamImporterJSON.squadsFromJson(teamFile, clubs);
    }
    
    
    public void setMatches(IMatch[][] matches){ //Quando for para importar as matches do ficheiro JSON
                                                //Caso ja tenha sido gerado as matches
        this.generatedRounds = matches;
        
    }
    

    @Override
    public IMatch[] getMatches(int round) {
        return schedule.getMatchesForRound(round);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void simulateSeason() {
        while (!isSeasonComplete()) {
            simulateRound();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSeasonComplete() {
        return currentRound >= schedule.getNumberOfRounds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetSeason() {
        this.currentRound = 0;
        if (schedule instanceof Schedule s) {
            s.reset();
        }
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void setMatchSimulator(MatchSimulatorStrategy strategy) {
        this.simulator = strategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStanding[] getLeagueStandings() {
        if (schedule instanceof Schedule s) {
            return s.getStandings();
        }
        return new IStanding[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPointsPerWin() {
        return pointsPerWin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPointsPerDraw() {
        return pointsPerDraw;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPointsPerLoss() {
        return pointsPerLoss;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxRounds() {
        return maxRounds;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfCurrentTeams() {
        return clubs.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IClub[] getCurrentClubs() {
        return clubs;
    }


  
    public void setJsonAccumulator(JsonAccumulator jsonAccumulator) {
        this.jsonAccumulator = jsonAccumulator;
    }

  
/**
     * Exporta os resultados da época para um ficheiro JSON.
     *
     * @throws IOException se ocorrer erro ao escrever no ficheiro
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
