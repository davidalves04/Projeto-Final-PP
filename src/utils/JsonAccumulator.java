/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package utils;

/**
 * Classe auxiliar para acumular linhas de texto JSON num buffer de string.
 * Permite adicionar várias linhas e recuperar o conteúdo acumulado como uma única string.
 */
public class JsonAccumulator {
    
    private final StringBuilder sb = new StringBuilder();

    /**
     * Adiciona uma linha JSON ao acumulador, adicionando uma nova linha após o texto.
     * 
     * @param jsonLine Linha JSON a ser adicionada.
     */
    public void append(String jsonLine) {
        sb.append(jsonLine).append("\n");
    }

    /**
     * Obtém o conteúdo JSON acumulado até ao momento, como uma string.
     * 
     * @return String contendo todas as linhas JSON acumuladas.
     */
    public String getJson() {
        return sb.toString();
    }
}
