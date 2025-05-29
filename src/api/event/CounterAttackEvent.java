package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class CounterAttackEvent {
    private final IPlayer player;
    private final IClub club;
    private final int minute;

    public CounterAttackEvent(IClub club, IPlayer player, int minute) {
        this.club = club;
        this.player = player;
        this.minute = minute;
    }

  
    public String getDescription() {
        return "⏱️ " + minute + "' - EVENTO: Contra-ataque do " + club.getName() + "!\n" +
               "- Passe longo...  finalização de " + player.getName() + "!";
    }

    
    public int getMinute() {
        return minute;
    }

    
}