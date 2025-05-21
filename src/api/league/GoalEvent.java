package api.league;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class GoalEvent implements IGoalEvent {

    private final int minute;
    private final IPlayer player;

    public GoalEvent(int minute, IPlayer player) {
        this.minute = minute;
        this.player = player;
    }

    @Override
    public String getDescription() {
        return "Golo de " + player.getName() + " aos " + minute + " minutos.";
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public IPlayer getPlayer() {
        return player;
    }

    @Override
    public void exportToJson() {
        // Implementação fictícia para cumprir contrato
        System.out.println("{ \"minute\": " + minute + ", \"player\": \"" + player.getName() + "\" }");
    }
}
