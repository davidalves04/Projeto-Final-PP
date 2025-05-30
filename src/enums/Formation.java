/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package enums;

/**
 * Enumeração que representa as formações táticas disponíveis para uma equipa de futebol.
 * 
 * As formações são nomeadas com palavras em português e mapeadas para uma string mais convencional
 * com números (ex: "4-3-3", "4-4-2") através do método {@code formationToString}.
 * 
 * Formações disponíveis:
 * <ul>
 *   <li>QUATRO_TRES_TRES → "4-3-3"</li>
 *   <li>QUATRO_QUATRO_DOIS → "4-4-2"</li>
 *   <li>TRES_CINCO_DOIS → "3-5-2"</li>
 *   <li>CINCO_TRES_DOIS → "5-3-2"</li>
 *   <li>QUATRO_DOIS_TRES_UM → "4-2-3-1"</li>
 * </ul>
 */
public enum Formation {
    QUATRO_TRES_TRES,
    QUATRO_QUATRO_DOIS,
    TRES_CINCO_DOIS,
    CINCO_TRES_DOIS,
    QUATRO_DOIS_TRES_UM;

    /**
     * Converte uma formação {@code Formation} no seu formato textual convencional.
     * 
     * @param f a formação a ser convertida
     * @return uma string representando a formação no formato numérico padrão (ex: "4-3-3")
     */
    public static String formationToString(Formation f) {
        switch (f) {
            case QUATRO_TRES_TRES:
                return "4-3-3";
            case QUATRO_QUATRO_DOIS:
                return "4-4-2";
            case TRES_CINCO_DOIS:
                return "3-5-2";
            case CINCO_TRES_DOIS:
                return "5-3-2";
            case QUATRO_DOIS_TRES_UM:
                return "4-2-3-1";
            default:
                return "Desconhecida";
        }
    }
}
