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
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import utils.JsonAccumulator;

public class CornerEvent implements IEvent {
    private final IPlayer player;
    private final ITeam club;
    private final int minute;
    
    private JsonAccumulator jsonAccumulator;

    public CornerEvent(ITeam club, IPlayer player, int minute) {
        this.club = club;
        this.player = player;
        this.minute = minute;
    }

    public String getDescription() {
        return "⏱️ " + minute + "' - EVENTO: Canto para " + club.getClub().getName() + "\n" +
               "- Cruzamento de " + player.getName() + "!";
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
            System.err.println("JsonAccumulator não definido para CornerEvent!");
            return;
        }

        jsonAccumulator.append("{");
        jsonAccumulator.append("  \"type\": \"CornerEvent\",");
        jsonAccumulator.append("  \"minute\": " + minute + ",");
        jsonAccumulator.append("  \"club\": \"" + escapeJson(club.getClub().getName()) + "\",");
        jsonAccumulator.append("  \"player\": \"" + escapeJson(player.getName()) + "\"");
        jsonAccumulator.append("}");
    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}