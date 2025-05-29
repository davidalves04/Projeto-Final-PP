package api.event;

import api.player.Player;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

/**
 * Representa um evento de remate falhado por um jogador durante um jogo de futebol.
 */
public class MissedShotEvent implements IEvent {


    /** Jogador que falhou o remate. */
    

    /** Minuto do jogo em que o remate foi falhado. */
    private final int minute;
    
    private final IClub club;
    /** Jogador que falhou o remate. */
    
    private final Player attacker;
    private final Player goalkeeper;


    /**
     * Construtor do evento de remate falhado.
     *
     * @param club   Clube ao qual pertence o jogador que falhou o remate (não armazenado internamente).
     * @param player Jogador que falhou o remate.
     * @param minute Minuto do jogo em que o remate foi falhado.
     */
   

    

    public MissedShotEvent(IClub club, Player attacker,Player goalkeeper, int minute) {
        this.club = club;
        this.attacker = attacker;
        this.goalkeeper = goalkeeper;

        this.minute = minute;
    }

    /**
     * Devolve a descrição textual do evento de remate falhado.
     *
     * @return String descritiva do remate falhado.
     */
    @Override
    public String getDescription() {
        return "⏱️ " + minute + "'" + " EVENTO: " + attacker.getName() + " tentou rematar (Remate: " + attacker.getShooting() +
                               "), mas " + goalkeeper.getName() + " defendeu (Defesa: " + goalkeeper.getDefense() + ")";
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
