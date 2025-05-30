package api.event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class CornerEvent {
    private final IPlayer player;
    private final ITeam club;
    private final int minute;

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
}