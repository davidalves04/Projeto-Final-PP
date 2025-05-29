package api.event;

import api.player.Player;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

/**
 * Representa um evento de golo marcado por um jogador durante um jogo de futebol.
 */
public class GoalEvent implements IGoalEvent {


    /** Jogador que marcou o golo. */
    

    /** Minuto do jogo em que o golo foi marcado. */
    private final int minute;
    private final IClub club;
    
    /** Jogador que marcou o golo. */
    private final Player scorer;
    private final Player goalkeeper;

    /**
     * Construtor do evento de golo.
     *
     * @param club  Clube ao qual pertence o jogador que marcou o golo (não armazenado internamente).
     * @param player Jogador que marcou o golo.
     * @param minute Minuto do jogo em que o golo foi marcado.
     */
    

    
   

    public GoalEvent(IClub club, Player scorer,Player gk, int minute) {
        this.club = club;
        this.scorer = scorer;
        this.goalkeeper = gk;
        

        this.minute = minute;
    }

    /**
     * Devolve a descrição textual do evento de golo.
     *
     * @return String descritiva do golo.
     */
    @Override
    public String getDescription() {
        return "⚽ GOLO! " + club.getName() + " - " + scorer.getName() + " (Remate: " + scorer.getShooting() +
                               ") marcou a " + goalkeeper.getName() + " (Defesa: " + goalkeeper.getDefense() + ") ao minuto " + minute;
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
        return this.scorer;
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
