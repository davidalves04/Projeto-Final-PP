/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

/**
 * Implementação de {@link IPlayerSelector} que seleciona o melhor jogador
 * de um clube para uma determinada posição com base na média das estatísticas.
 * 
 * A seleção é feita calculando a média dos atributos Passing, Shooting, Speed
 * e Stamina, retornando o jogador com maior valor médio para a posição indicada.
 * 
 * @author Utilizador
 */
public class BestPlayerSelector implements IPlayerSelector {
    
    /**
     * Seleciona o melhor jogador de um clube para a posição especificada.
     * 
     * @param iclub O clube onde se procura o jogador
     * @param ipp A posição do jogador pretendido
     * @return O jogador com a melhor média estatística para a posição
     * @throws IllegalArgumentException se o clube ou posição forem nulos
     * @throws IllegalStateException se o clube não tiver jogadores
     * @throws IllegalStateException se nenhum jogador for encontrado para a posição
     */
    @Override
    public IPlayer selectPlayer(IClub iclub, IPlayerPosition ipp) {
        if (iclub == null || ipp == null) {
            throw new IllegalArgumentException("Clube ou posição não podem ser nulos.");
        }

        IPlayer[] players = iclub.getPlayers();
        int bestStats = 0;
        IPlayer bestPlayer = null;
        int i = 0;

        if (players.length == 0) {
            throw new IllegalStateException("O clube está vazio.");
        }

        while (i < players.length) {
            IPlayer player = players[i];

            if (player.getPosition().equals(ipp)) {
                int playerStats = (player.getPassing() + player.getShooting() + player.getSpeed() + player.getStamina()) / 4;

                if (playerStats > bestStats) {
                    bestStats = playerStats;
                    bestPlayer = player;
                }
            }
            i++;
        }

        if (bestPlayer == null) {
            throw new IllegalStateException("Nenhum jogador encontrado para essa posição.");
        }

        return bestPlayer;
    }
}
