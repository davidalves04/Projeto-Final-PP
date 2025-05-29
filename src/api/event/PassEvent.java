package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class PassEvent implements IEvent {

    private final IPlayer player;
    private final int minute;
    private final boolean successful;

    public PassEvent(IPlayer player, int minute, boolean successful) {
        this.player = player;
        this.minute = minute;
        this.successful = successful;
    }

    @Override
    public String getDescription() {
        return (successful ? "Passe bem sucedido de " : "Passe falhado de ") + player.getName() + " ao minuto " + minute;
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
