/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsável por remover objetos de jogadores de um ficheiro JSON,
 * com base no número de camisola.
 * A manipulação do ficheiro é feita de forma textual, assumindo que o ficheiro
 * contém um array JSON válido de objetos de jogadores.
 */
public class PlayerRemoverJSON {

    private final String filePath;

    /**
     * Construtor da classe.
     *
     * @param filePath Caminho para o ficheiro JSON onde estão guardados os jogadores.
     */
    public PlayerRemoverJSON(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Remove do ficheiro JSON o objeto que representa o jogador com o número indicado.
     * O conteúdo é lido, filtrado e regravado no mesmo ficheiro.
     *
     * @param numberToRemove Número do jogador a remover.
     * @throws IOException Caso ocorra erro na leitura ou escrita do ficheiro.
     */
    public void removeObjectByNumber(int numberToRemove) throws IOException {
        File file = new File(filePath);

        // 1) Lê o conteúdo completo do ficheiro
        StringBuilder contentBuilder = new StringBuilder();
        if (file.exists() && file.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            }
        }

        String content = contentBuilder.toString().trim();

        // 2) Remove os colchetes [] do array JSON para facilitar o processamento
        if (content.startsWith("[") && content.endsWith("]")) {
            content = content.substring(1, content.length() - 1).trim();
        }

        // 3) Divide manualmente os objetos JSON dentro do array
        int length = content.length();
        StringBuilder filteredJson = new StringBuilder();
        filteredJson.append("[\n");

        boolean first = true;
        String pattern = "\"number\": " + numberToRemove;

        int i = 0;
        while (i < length) {
            // Encontra o próximo objeto JSON (delimitado por chaves {})
            int startIndex = content.indexOf('{', i);
            if (startIndex == -1) {
                break;
            }

            int braceCount = 0;
            int endIndex = -1;
            for (int j = startIndex; j < length; j++) {
                if (content.charAt(j) == '{') braceCount++;
                else if (content.charAt(j) == '}') braceCount--;

                if (braceCount == 0) {
                    endIndex = j;
                    break;
                }
            }

            if (endIndex == -1) {
                // Objeto mal formado, termina a leitura
                break;
            }

            String obj = content.substring(startIndex, endIndex + 1).trim();

            // Verifica se o objeto NÃO tem o número a remover
            if (!obj.contains(pattern)) {
                if (!first) {
                    filteredJson.append(",\n");
                }
                filteredJson.append(obj);
                first = false;
            }

            i = endIndex + 1;
        }

        filteredJson.append("\n]");

        // 4) Reescreve o ficheiro com os objetos restantes
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(filteredJson.toString());
        }

        System.out.println("Objeto com number '" + numberToRemove + "' removido do JSON.");
    }
}
