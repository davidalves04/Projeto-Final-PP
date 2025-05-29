package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Representa um evento de golo marcado por um jogador durante um jogo de futebol.
 */
public class GoalEvent implements IGoalEvent {

    /** Jogador que marcou o golo. */
    private final IPlayer player;

    /** Minuto do jogo em que o golo foi marcado. */
    private final int minute;

    /**
     * Construtor do evento de golo.
     *
     * @param club  Clube ao qual pertence o jogador que marcou o golo (não armazenado internamente).
     * @param player Jogador que marcou o golo.
     * @param minute Minuto do jogo em que o golo foi marcado.
     */
    public GoalEvent(com.ppstudios.footballmanager.api.contracts.team.IClub club, IPlayer player, int minute) {
        this.player = player;
        this.minute = minute;
    }

    /**
     * Devolve a descrição textual do evento de golo.
     *
     * @return String descritiva do golo.
     */
    @Override
    public String getDescription() {
        return "Golo de " + player.getName() + " ao minuto " + minute;
    }

    /**
     * Devolve o minuto do jogo em que o golo foi marcado.
     *
     * @return Minuto do evento.
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Devolve o jogador que marcou o golo.
     *
     * @return Jogador autor do golo.
     */
    @Override
    public IPlayer getPlayer() {
        return player;
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
