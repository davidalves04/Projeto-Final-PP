/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.event;

import api.player.Player;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import utils.JsonAccumulator;

/**
 * Representa um evento de golo marcado por um jogador durante um jogo de futebol.
 */
public class GoalEvent implements IGoalEvent {


    /** Jogador que marcou o golo. */
    

    /** Minuto do jogo em que o golo foi marcado. */
    private final int minute;
    private final ITeam club;
    
    /** Jogador que marcou o golo. */
    private final Player scorer;
    private final Player goalkeeper;
    
     private JsonAccumulator jsonAccumulator;

    /**
     * Construtor do evento de golo.
     *
     * @param club  Clube ao qual pertence o jogador que marcou o golo (não armazenado internamente).
     * @param player Jogador que marcou o golo.
     * @param minute Minuto do jogo em que o golo foi marcado.
     */
    

    
   

    public GoalEvent(ITeam club, Player scorer,Player gk, int minute) {
        this.club = club;
        this.scorer = scorer;
        this.goalkeeper = gk;
        

        this.minute = minute;
    }

    
    public ITeam getScoringTeam() {
    return this.club;
    }
    
    /**
     * Devolve a descrição textual do evento de golo.
     *
     * @return String descritiva do golo.
     */
    @Override
    public String getDescription() {
        return "⚽ GOLO! " + club.getClub().getName() + " - " + scorer.getName() + " (Remate: " + scorer.getShooting() +
                               ") marcou a " + goalkeeper.getName() + " (Defesa: " + goalkeeper.getDefense() + ") ao minuto " + minute;
    }

    /**
     * Devolve o minuto do jogo em que o golo foi marcado.
     *
     * @return Minuto do evento.
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Devolve o jogador que marcou o golo.
     *
     * @return Jogador autor do golo.
     */
    @Override
    public IPlayer getPlayer() {
        return this.scorer;
    }

    public JsonAccumulator getJsonAccumulator() {
        return jsonAccumulator;
    }

    public void setJsonAccumulator(JsonAccumulator jsonAccumulator) {
        this.jsonAccumulator = jsonAccumulator;
    }

    
    
    
    /**
     * Exporta o evento para JSON.
     * <p>
     * Nota: Método não implementado. Pode ser deixado em branco se não for necessária
     * a exportação individual deste evento.
     * </p>
     */
    
        @Override
    public void exportToJson() {
        if (jsonAccumulator == null) {
            System.err.println("JsonAccumulator não definido para GoalEvent!");
            return;
        }

        jsonAccumulator.append("{");
        jsonAccumulator.append("  \"type\": \"GoalEvent\",");
        jsonAccumulator.append("  \"minute\": " + minute + ",");
        jsonAccumulator.append("  \"club\": \"" + escapeJson(club.getClub().getName()) + "\",");
        jsonAccumulator.append("  \"scorer\": \"" + escapeJson(scorer.getName()) + "\",");
        jsonAccumulator.append("  \"scorerShooting\": " + scorer.getShooting() + ",");
        jsonAccumulator.append("  \"goalkeeper\": \"" + escapeJson(goalkeeper.getName()) + "\",");
        jsonAccumulator.append("  \"goalkeeperDefense\": " + goalkeeper.getDefense());
        jsonAccumulator.append("}");
    }

    // Método simples para escapar aspas e barras no JSON
    private String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
