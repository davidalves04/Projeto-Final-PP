 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;



import api.league.Season;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import utils.JsonAccumulator;


/**
 *
 * @author Utilizador
 */
public class LeagueExporterJSON {
    public void appendSeasonToExistingLeague(Season season, String leagueFilePath) throws IOException {
        File file = new File(leagueFilePath);
        if (!file.exists()) {
            throw new IOException("Ficheiro da liga não existe.");
        }

        // 1. Lê o conteúdo existente
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        String jsonStr = content.toString();

        // 2. Encontra o índice do array de seasons
        int seasonsArrayStart = jsonStr.indexOf("\"seasons\"");
        if (seasonsArrayStart == -1) {
            throw new IOException("Não foi possível encontrar o array 'seasons' no JSON.");
        }
        int arrayStart = jsonStr.indexOf("[", seasonsArrayStart);
        if (arrayStart == -1) {
            throw new IOException("Formato do array 'seasons' inválido.");
        }

        // Usa método para achar o índice correto do colchete de fechamento
        int arrayEnd = findMatchingBracketIndex(jsonStr, arrayStart);

        // 3. Extrai o conteúdo dentro do array
        String seasonsContent = jsonStr.substring(arrayStart + 1, arrayEnd).trim();

        // 4. Gera o JSON da nova season
        JsonAccumulator acc = new JsonAccumulator();
        season.setJsonAccumulator(acc);
        season.exportToJson();
        String newSeasonJson = acc.getJson().trim();

        // 5. Cria o novo conteúdo do array seasons
        String updatedSeasonsContent;
        if (seasonsContent.isEmpty()) {
            // Se o array estava vazio, só adiciona a nova season
            updatedSeasonsContent = "\n  " + newSeasonJson + "\n";
        } else {
            // Se já tem temporadas, adiciona vírgula e depois a nova season
            updatedSeasonsContent = seasonsContent + ",\n  " + newSeasonJson + "\n";
        }

        // 6. Reconstroi o JSON completo substituindo o array antigo pelo novo
        String updatedJson = jsonStr.substring(0, arrayStart + 1) + updatedSeasonsContent + jsonStr.substring(arrayEnd);

        // 7. Escreve no ficheiro
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(updatedJson);
        }
    }

    // Método auxiliar para encontrar o índice de fechamento do colchete correspondente
    private int findMatchingBracketIndex(String str, int startIndex) throws IOException {
        int count = 0;
        for (int i = startIndex; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '[') count++;
            else if (c == ']') count--;
            if (count == 0) return i;
        }
        throw new IOException("Não encontrou o fechamento do array.");
    }
}