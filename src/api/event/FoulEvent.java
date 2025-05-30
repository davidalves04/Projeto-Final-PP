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

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import utils.JsonAccumulator;

/**
 * Representa um evento de falta cometido por um jogador durante um jogo de futebol.
 */
public class FoulEvent implements IEvent {

    /** Jogador que cometeu a falta. */
    private final IPlayer player;

    /** Minuto do jogo em que a falta ocorreu. */
    private final int minute;
    
        private JsonAccumulator jsonAccumulator;


    /**
     * Construtor do evento de falta.
     *
     * @param player Jogador que cometeu a falta.
     * @param minute Minuto do jogo em que a falta ocorreu.
     */
    public FoulEvent(IPlayer player, int minute) {
        this.player = player;
        this.minute = minute;
    }

    /**
     * Devolve a descrição textual do evento.
     *
     * @return String descritiva da falta cometida.
     */
    @Override
    public String getDescription() {
        return "Falta cometida por " + player.getName() + " ao minuto " + minute;
    }

    /**
     * Devolve o minuto do jogo em que a falta ocorreu.
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
     * Nota: Método ainda não implementado.
     * </p>
     */
    @Override
    public void exportToJson() {
         if (jsonAccumulator == null) {
        System.err.println("JsonAccumulator não definido para FoulEvent!");
        return;
    }

    jsonAccumulator.append("{\n");

    jsonAccumulator.append("  \"minute\": ");
    jsonAccumulator.append(Integer.toString(minute));
    jsonAccumulator.append(",\n");

    jsonAccumulator.append("  \"player\": \"");
    jsonAccumulator.append(player.getName());
    jsonAccumulator.append("\"\n");

    jsonAccumulator.append("}");
    }
}
