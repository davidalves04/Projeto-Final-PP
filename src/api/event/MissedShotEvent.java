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
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import utils.JsonAccumulator;

/**
 * Representa um evento de remate falhado por um jogador durante um jogo de futebol.
 */
public class MissedShotEvent implements IEvent {


    /** Jogador que falhou o remate. */
    

    /** Minuto do jogo em que o remate foi falhado. */
    private final int minute;
    
    private final ITeam club;
    /** Jogador que falhou o remate. */
    
    private final Player attacker;
    private final Player goalkeeper;
    
    private JsonAccumulator jsonAccumulator;


    /**
     * Construtor do evento de remate falhado.
     *
     * @param club   Clube ao qual pertence o jogador que falhou o remate (não armazenado internamente).
     * @param player Jogador que falhou o remate.
     * @param minute Minuto do jogo em que o remate foi falhado.
     */
   

    

    public MissedShotEvent(ITeam club, Player attacker,Player goalkeeper, int minute) {
        this.club = club;
        this.attacker = attacker;
        this.goalkeeper = goalkeeper;

        this.minute = minute;
    }

    /**
     * Devolve a descrição textual do evento de remate falhado.
     *
     * @return String descritiva do remate falhado.
     */
    @Override
    public String getDescription() {
        return "⏱️ " + minute + "'" + " EVENTO: " + attacker.getName() + " tentou rematar (Remate: " + attacker.getShooting() +
                               "), mas " + goalkeeper.getName() + " defendeu (Defesa: " + goalkeeper.getDefense() + ")";
    }

    /**
     * Devolve o minuto do jogo em que o remate foi falhado.
     *
     * @return Minuto do evento.
     */
    @Override
    public int getMinute() {
        return minute;
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
            System.err.println("JsonAccumulator não definido para MissedShotEvent!");
            return;
        }

        jsonAccumulator.append("{");
        jsonAccumulator.append("  \"type\": \"MissedShotEvent\",");
        jsonAccumulator.append("  \"minute\": " + minute + ",");
        jsonAccumulator.append("  \"club\": \"" + escapeJson(club.getClub().getName()) + "\",");
        jsonAccumulator.append("  \"attacker\": \"" + escapeJson(attacker.getName()) + "\",");
        jsonAccumulator.append("  \"attackerShooting\": " + attacker.getShooting() + ",");
        jsonAccumulator.append("  \"goalkeeper\": \"" + escapeJson(goalkeeper.getName()) + "\",");
        jsonAccumulator.append("  \"goalkeeperDefense\": " + goalkeeper.getDefense());
        jsonAccumulator.append("}");
    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
