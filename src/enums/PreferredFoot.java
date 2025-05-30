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
 * Enumeração que representa o pé preferido de um jogador.
 * 
 * Os valores possíveis são:
 * <ul>
 *   <li>{@code LEFT} – Pé Esquerdo</li>
 *   <li>{@code RIGHT} – Pé Direito</li>
 * </ul>
 * 
 * O método {@link #footToString(PreferredFoot)} permite converter o valor da enumeração
 * para uma string descritiva em português.
 * 
 * Esta enumeração pode ser utilizada para representar a dominância de pé de um jogador,
 * útil em lógicas de posicionamento, remates, cruzamentos, entre outros.
 * 
 * @author David
 */
public enum PreferredFoot {
    /** Pé esquerdo (Left foot) */
    LEFT,
    /** Pé direito (Right foot) */
    RIGHT;

    /**
     * Converte o valor {@code PreferredFoot} para uma string descritiva em português.
     * 
     * @param foot o pé preferido
     * @return uma string representando o pé ("Esquerdo", "Direito") ou "Desconhecido" se nulo
     */
    public static String footToString(PreferredFoot foot) {
        switch (foot) {
            case LEFT:
                return "Esquerdo";
            case RIGHT:
                return "Direito";
            default:
                return "Desconhecido";
        }
    }
}
