package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Representa um evento de passe realizado por um jogador durante um jogo de futebol.
 * Pode ser um passe bem sucedido ou falhado.
 */
public class PassEvent implements IEvent {

    /** Jogador que realizou o passe. */
    private final IPlayer player;

    /** Minuto do jogo em que o passe ocorreu. */
    private final int minute;

    /** Indica se o passe foi bem sucedido. */
    private final boolean successful;

    /**
     * Construtor do evento de passe.
     *
     * @param player     Jogador que realizou o passe.
     * @param minute     Minuto do jogo em que o passe ocorreu.
     * @param successful Verdadeiro se o passe foi bem sucedido; falso se foi falhado.
     */
    public PassEvent(IPlayer player, int minute, boolean successful) {
        this.player = player;
        this.minute = minute;
        this.successful = successful;
    }

    /**
     * Devolve a descrição textual do evento de passe.
     *
     * @return String descritiva do passe (bem sucedido ou falhado).
     */
    @Override
    public String getDescription() {
        return (successful ? "Passe bem sucedido de " : "Passe falhado de ") + player.getName() + " ao minuto " + minute;
    }

    /**
     * Devolve o minuto do jogo em que o passe ocorreu.
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
     * Nota: Método não implementado. Pode ser implementado se necessário.
     * </p>
     */
    @Override
    public void exportToJson() {
        // Implementa exportação se necessário
    }
}
