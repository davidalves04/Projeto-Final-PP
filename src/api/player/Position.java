/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

/**
 * Implementação da interface {@link IPlayerPosition} que representa
 * a posição base de um jogador em campo (ex: "Avançado", "Médio", "Defesa").
 * 
 * Esta classe armazena a descrição textual da posição.
 * 
 * @author Gabriel
 */
public class Position implements IPlayerPosition {
    
    private String description;

    /**
     * Construtor da posição do jogador.
     * 
     * @param description descrição textual da posição (ex: "Guarda-Redes", "Defesa")
     */
    public Position(String description) {
        this.description = description;
    }

    /**
     * Devolve a descrição da posição.
     * 
     * @return a descrição da posição
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Representação textual da posição, equivalente à descrição.
     * 
     * @return a descrição da posição como string
     */
    @Override
    public String toString() {
        return this.description; 
    }
}
