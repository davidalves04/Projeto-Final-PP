
package api.event;


import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class CornerEvent {
    private final IPlayer player;
    private final IClub club;
    private final int minute;

    public CornerEvent(IClub club, IPlayer player, int minute) {
        this.club = club;
        this.player = player;
        this.minute = minute;
    }

    public String getDescription() {
        return "⏱️ " + minute + "' - EVENTO: Canto para " + club.getName() + "\n" +
               "- Cruzamento de " + player.getName() + "!";
    }

   
    public int getMinute() {
        return minute;
    }
}