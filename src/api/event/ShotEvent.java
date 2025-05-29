package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Representa um evento de remate enquadrado realizado por um jogador durante um jogo de futebol.
 */
public class ShotEvent implements IEvent {

    /** Jogador que realizou o remate enquadrado. */
    private final IPlayer player;

    /** Minuto do jogo em que o remate ocorreu. */
    private final int minute;

    /**
     * Construtor do evento de remate enquadrado.
     *
     * @param player Jogador que realizou o remate.
     * @param minute Minuto do jogo em que o remate ocorreu.
     */
    public ShotEvent(IPlayer player, int minute) {
        this.player = player;
        this.minute = minute;
    }

    /**
     * Devolve a descrição textual do evento de remate enquadrado.
     *
     * @return String descritiva do remate.
     */
    @Override
    public String getDescription() {
        return "Remate enquadrado por " + player.getName() + " ao minuto " + minute;
    }

    /**
     * Devolve o minuto do jogo em que o remate ocorreu.
     *
     * @return Minuto do evento.
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Exporta o evento para JSON.
     * <p>
     * Nota: Método não implementado. Pode ser desenvolvido caso seja necessário exportar o evento.
     * </p>
     */
    @Override
    public void exportToJson() {
        // Implementa exportação se necessário
    }
}
