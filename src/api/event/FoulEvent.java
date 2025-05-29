package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class FoulEvent implements IEvent {

    private final IPlayer player;
    private final int minute;

    public FoulEvent(IPlayer player, int minute) {
        this.player = player;
        this.minute = minute;
    }

    @Override
    public String getDescription() {
        return "Falta cometida por " + player.getName() + " ao minuto " + minute;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public void exportToJson() {
        // Implementa exportação se necessário
    }
}
