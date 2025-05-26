package api.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerRemoverJSON {

    private final String filePath;

    public PlayerRemoverJSON(String filePath) {
        this.filePath = filePath;
    }

    public void removeObjectByNumber(int numberToRemove) throws IOException {
        File file = new File(filePath);

        // 1) Ler conteúdo completo do arquivo
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

        // 2) Remover o [ e ] do array JSON
        if (content.startsWith("[") && content.endsWith("]")) {
            content = content.substring(1, content.length() - 1).trim();
        }

        // 3) Separar os objetos JSON manualmente sem coleções
        // Aqui vamos dividir a string em objetos JSON simples, assumindo que cada objeto começa com '{' e termina com '}'
        // Como não podemos usar regex split para arrays, vamos fazer um parse simples por índice
        int length = content.length();
        StringBuilder filteredJson = new StringBuilder();
        filteredJson.append("[\n");

        boolean first = true;
        String pattern = "\"number\": " + numberToRemove;

        int i = 0;
        while (i < length) {
            // Encontrar o próximo objeto JSON entre {}
            // Encontrar o índice de abertura {
            int startIndex = content.indexOf('{', i);
            if (startIndex == -1) {
                break; // sem mais objetos
            }
            // Encontrar o índice de fechamento correspondente }
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
                // objeto mal formado, sai do loop
                break;
            }

            String obj = content.substring(startIndex, endIndex + 1).trim();

            // Verifica se o objeto contem o "number": numberToRemove
            if (obj.indexOf(pattern) == -1) {
                if (!first) {
                    filteredJson.append(",\n");
                }
                filteredJson.append(obj);
                first = false;
            }

            i = endIndex + 1;
        }

        filteredJson.append("\n]");

        // 4) Reescrever o arquivo com o JSON filtrado
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(filteredJson.toString());
        }

        System.out.println("Objeto com number '" + numberToRemove + "' removido do JSON.");
    }
}