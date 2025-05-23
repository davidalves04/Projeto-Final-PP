package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class GoalEvent implements IGoalEvent {

    private final IPlayer player;
    private final int minute;

    public GoalEvent(com.ppstudios.footballmanager.api.contracts.team.IClub club, IPlayer player, int minute) {
        this.player = player;
        this.minute = minute;
    }

    @Override
    public String getDescription() {
        return "Golo de " + player.getName() + " ao minuto " + minute;
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
        // Se não for necessário exportar o evento individualmente, pode ficar vazio.
    }
}
