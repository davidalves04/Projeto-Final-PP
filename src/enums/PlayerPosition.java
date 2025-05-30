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
 * Enumeração que representa as posições básicas de um jogador em campo.
 * 
 * As posições disponíveis são:
 * <ul>
 *   <li>{@code GK} – Guarda-Redes</li>
 *   <li>{@code DEF} – Defesa</li>
 *   <li>{@code MID} – Médio</li>
 *   <li>{@code FWD} – Avançado</li>
 * </ul>
 * 
 * O método {@link #positionToString(PlayerPosition)} permite converter a posição para uma 
 * descrição textual mais legível em português.
 * 
 * Esta enumeração pode ser usada para categorizar os jogadores de uma equipa com base na 
 * sua função principal em campo.
 * 
 * @author David
 */
public enum PlayerPosition {
    /** Guarda-redes (Goalkeeper) */
    GK,
    /** Defesa (Defender) */
    DEF,
    /** Médio (Midfielder) */
    MID,
    /** Avançado (Forward) */
    FWD;

    /**
     * Converte a posição {@code PlayerPosition} para uma string descritiva em português.
     * 
     * @param pos a posição a ser convertida
     * @return a descrição da posição em português (ex: "Médio"), ou "Desconhecida" se não reconhecida
     */
    public static String positionToString(PlayerPosition pos) {
        switch (pos) {
            case GK:
                return "Guarda-Redes";
            case DEF:
                return "Defesa";
            case MID:
                return "Médio";
            case FWD:
                return "Avançado";
            default:
                return "Desconhecida";
        }
    }
}
