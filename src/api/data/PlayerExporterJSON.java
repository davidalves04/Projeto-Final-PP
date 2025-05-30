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

import api.player.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsável por exportar objetos {@link Player} para um ficheiro JSON.
 * Permite adicionar múltiplos jogadores a um ficheiro JSON, mantendo o formato correto de array.
 * 
 * Esta classe assume que cada {@code Player} possui um método {@code exportToJson()} 
 * que escreve o conteúdo JSON para o ficheiro indicado anteriormente com {@code setFile()}.
 */
public class PlayerExporterJSON {

    /**
     * Exporta um array de jogadores para um ficheiro JSON.
     * Se o ficheiro já contém dados, os novos jogadores são adicionados ao array existente.
     * Caso contrário, um novo array JSON será iniciado.
     *
     * @param players Array de objetos Player a exportar.
     * @param playerFile Caminho para o ficheiro JSON de destino.
     * @throws IOException Se ocorrer um erro de escrita/leitura do ficheiro.
     */
    public void exportPlayersArrayToJson(Player[] players, String playerFile) throws IOException {
        File file = new File(playerFile);
        boolean existeConteudo = false;

        // 1) Verifica se o ficheiro existe e tem conteúdo
        if (file.exists() && file.length() > 0) {
            StringBuilder conteudo = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    conteudo.append(line).append("\n");
                }
            }

            String sConteudo = conteudo.toString().trim();
            if (sConteudo.endsWith("]")) {
                existeConteudo = true;
                // Remove o último ']' para continuar o array
                sConteudo = sConteudo.substring(0, sConteudo.length() - 1);

                // Reescreve o conteúdo sem o ']', seguido de uma vírgula
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                    writer.write(sConteudo);
                    writer.write(",\n"); // Pronto para adicionar novos objetos
                }
            }
        }

        // 2) Se o ficheiro está vazio ou não existe, inicia um novo array JSON
        if (!existeConteudo) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write("[\n");
            }
        }

        // 3) Adiciona cada jogador ao ficheiro
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (int i = 0; i < players.length; i++) {
                players[i].setFile(playerFile);
                players[i].exportToJson(); // Supõe que o método escreve no ficheiro já definido

                if (i < players.length - 1) {
                    writer.write(",\n");
                }
            }

            // 4) Fecha o array JSON
            writer.write("\n]");
        }
    }
}
