package api.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

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
   

     private final ITeam club;

    /**
     * Construtor do evento de passe.
     *
     * @param player     Jogador que realizou o passe.
     * @param minute     Minuto do jogo em que o passe ocorreu.
     * @param successful Verdadeiro se o passe foi bem sucedido; falso se foi falhado.
     */
   
   

    

    public PassEvent(ITeam club,IPlayer player, int minute) {

        this.player = player;
        this.minute = minute;
       
        this.club = club;
    }

    /**
     * Devolve a descrição textual do evento de passe.
     *
     * @return String descritiva do passe (bem sucedido ou falhado).
     */
    @Override
    public String getDescription() {
        return "⏱️ " + minute + "' - EVENTO:" + club.getClub().getName() + "\n" +
               "- Passe perigoso de " + player.getName() + "!" + "(Passe: " + player.getPassing() + ")";
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
