package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Representa um evento de remate falhado por um jogador durante um jogo de futebol.
 */
public class MissedShotEvent implements IEvent {

    /** Jogador que falhou o remate. */
    private final IPlayer player;

    /** Minuto do jogo em que o remate foi falhado. */
    private final int minute;

    /**
     * Construtor do evento de remate falhado.
     *
     * @param club   Clube ao qual pertence o jogador que falhou o remate (não armazenado internamente).
     * @param player Jogador que falhou o remate.
     * @param minute Minuto do jogo em que o remate foi falhado.
     */
    public MissedShotEvent(com.ppstudios.footballmanager.api.contracts.team.IClub club, IPlayer player, int minute) {
        this.player = player;
        this.minute = minute;
    }

    /**
     * Devolve a descrição textual do evento de remate falhado.
     *
     * @return String descritiva do remate falhado.
     */
    @Override
    public String getDescription() {
        return "Remate falhado por " + player.getName() + " ao minuto " + minute;
    }

    /**
     * Devolve o minuto do jogo em que o remate foi falhado.
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
     * Nota: Método não implementado. Pode ser deixado em branco se não for necessária
     * a exportação individual deste evento.
     * </p>
     */
    @Override
    public void exportToJson() {
        // Se não for necessário exportar o evento individualmente, pode ficar vazio.
    }
}
