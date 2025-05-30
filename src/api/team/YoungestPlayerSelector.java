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
 * Implementação de IPlayerSelector que seleciona o jogador mais jovem
 * numa determinada posição dentro de um clube.
 * 
 * A seleção é feita percorrendo os jogadores do clube e verificando a 
 * idade daqueles cuja posição corresponde à posição pretendida.
 * Retorna o jogador com a idade mínima encontrada.
 * 
 * Caso o clube ou a posição sejam nulos, ou o clube esteja vazio,
 * lança exceções apropriadas.
 */
public class YoungestPlayerSelector implements IPlayerSelector{

    /**
     * Seleciona o jogador mais jovem do clube que joga na posição especificada.
     * 
     * @param iclub O clube onde procurar os jogadores.
     * @param ipp A posição do jogador a selecionar.
     * @return O jogador mais jovem que joga na posição dada.
     * @throws IllegalArgumentException Se o clube ou a posição forem nulos.
     * @throws IllegalStateException Se o clube estiver vazio ou nenhum jogador for encontrado para a posição.
     */
    @Override
    public IPlayer selectPlayer(IClub iclub, IPlayerPosition ipp) {
        
        if (iclub == null || ipp == null) {
            throw new IllegalArgumentException("Clube ou posição não podem ser nulos.");
        }

        IPlayer[] players = iclub.getPlayers(); 
        int minAge = Integer.MAX_VALUE; 
        IPlayer youngestPlayer = null;
        int i = 0;
        
        if (players.length == 0) {
            throw new IllegalStateException("O clube está vazio.");
        }

        while (i < players.length) {
            IPlayer player = players[i];
            if (player.getPosition().equals(ipp)) {
                int age = player.getAge();
                if (age < minAge) {
                    minAge = age;
                    youngestPlayer = player;
                }
            }
            i++;
        }  
    
        if (youngestPlayer == null) {
            throw new IllegalStateException("Nenhum jogador encontrado para essa posição.");
        }
       
        return youngestPlayer;
    }
}
