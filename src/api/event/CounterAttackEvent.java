package api.event;


import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class CounterAttackEvent {
    private final IPlayer player;
    private final ITeam club;
    private final int minute;

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

    
}