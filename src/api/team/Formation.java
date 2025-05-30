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

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

/**
 * Representa uma formação tática de uma equipa de futebol.
 * 
 * Esta classe implementa {@link IFormation} e permite definir um nome de exibição
 * para a formação, bem como calcular a vantagem tática relativamente a outra formação.
 * 
 * As regras para a vantagem tática são fictícias e baseiam-se em comparações simples
 * entre formações comuns.
 * 
 * Exemplo de valores retornados pelo método {@link #getTacticalAdvantage(IFormation)}:
 * <ul>
 *   <li>2: vantagem tática bastante significativa</li>
 *   <li>1: vantagem tática moderada</li>
 *   <li>0: empate ou sem vantagem</li>
 *   <li>-1: desvantagem tática moderada</li>
 *   <li>-2: desvantagem tática bastante significativa</li>
 * </ul>
 */
public class Formation implements IFormation {
    private String displayName;

    /**
     * Constrói uma formação com o nome de exibição indicado.
     * 
     * @param displayName o nome da formação (ex: "4-3-3")
     */
    public Formation(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Obtém o nome de exibição da formação.
     * 
     * @return o nome da formação
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Define o nome de exibição da formação.
     * 
     * @param displayName o novo nome da formação
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Calcula a vantagem tática desta formação em relação a outra formação.
     * 
     * As regras de vantagem são simplificadas e comparam nomes de formação específicos.
     * 
     * @param formation a formação adversária para comparar
     * @return um inteiro que representa a vantagem tática: 
     *         valores positivos indicam vantagem, negativos desvantagem, zero empate
     * @throws IllegalStateException se o nome da formação atual não estiver definido
     */
    @Override
    public int getTacticalAdvantage(IFormation formation) {
        if (this.displayName == null) {
            throw new IllegalStateException("Home team formation not set."); 
        }

        String home = this.displayName;  
        String away = formation.getDisplayName();

        // Regras fictícias simples
        if (home.equals(away)) {
            return 0; // Igualdade tática
        }

        if (home.equals("5-3-2") && away.equals("4-3-3")) return 1; // Vantagem moderada
        if (home.equals("4-3-3") && away.equals("5-3-2")) return -1; // Desvantagem moderada
        if (home.equals("4-4-2") && away.equals("4-3-3")) return -2; // Bastante desfavorecida
        if (home.equals("4-3-3") && away.equals("4-4-2")) return 2;  // Bastante vantajosa
        if (home.equals("4-4-2") && away.equals("5-3-2")) return 0;  // Empate
        if (home.equals("5-3-2") && away.equals("4-4-2")) return 0;  // Empate

        // Caso nenhuma regra específica se aplique
        return 0;
    }

    /**
     * Representação em texto da formação, devolvendo o nome de exibição.
     * 
     * @return o nome da formação
     */
    @Override
    public String toString() {
        return this.displayName;
    }
}
