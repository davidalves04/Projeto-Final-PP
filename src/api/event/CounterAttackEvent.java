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
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import utils.JsonAccumulator;

public class CounterAttackEvent implements IEvent{
    private final IPlayer player;
    private final ITeam club;
    private final int minute;
    
    private JsonAccumulator jsonAccumulator;

    public CounterAttackEvent(ITeam club, IPlayer player, int minute) {
        this.club = club;
        this.player = player;
        this.minute = minute;
    }

  
    public String getDescription() {
        return "⏱️ " + minute + "' - EVENTO: Contra-ataque do " + club.getClub().getName() + "!\n" +
               "- Passe longo...  finalização de " + player.getName() + "!";
    }

    
    public int getMinute() {
        return minute;
    }

    public JsonAccumulator getJsonAccumulator() {
        return jsonAccumulator;
    }

    public void setJsonAccumulator(JsonAccumulator jsonAccumulator) {
        this.jsonAccumulator = jsonAccumulator;
    }

    
  @Override
    public void exportToJson() {
        if (jsonAccumulator == null) {
            System.err.println("JsonAccumulator não definido para CounterAttackEvent!");
            return;
        }

        // Gerar JSON válido manualmente
        jsonAccumulator.append("{");
        jsonAccumulator.append("  \"type\": \"CounterAttackEvent\",");
        jsonAccumulator.append("  \"minute\": " + minute + ",");
        jsonAccumulator.append("  \"club\": \"" + escapeJson(club.getClub().getName()) + "\",");
        jsonAccumulator.append("  \"player\": \"" + escapeJson(player.getName()) + "\"");
        jsonAccumulator.append("}");
    }
    
     private String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    
}