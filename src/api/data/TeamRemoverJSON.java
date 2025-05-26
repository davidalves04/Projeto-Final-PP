package api.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TeamRemoverJSON {

    private final String filePath;

    public TeamRemoverJSON(String filePath) {
        this.filePath = filePath;
    }

 public void removeObjectByCod(String teamCode) throws IOException {
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
        int length = content.length();
        StringBuilder filteredJson = new StringBuilder();
        filteredJson.append("[\n");

        boolean first = true;
        // Montar padrão para string JSON: exemplo -> "code": "teamCode"
        String pattern = "\"code\": \"" + teamCode + "\"";

        int i = 0;
        while (i < length) {
            // Encontrar o próximo objeto JSON entre {}
            int startIndex = content.indexOf('{', i);
            if (startIndex == -1) {
                break; // sem mais objetos
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
                // objeto mal formado, sai do loop
                break;
            }

            String obj = content.substring(startIndex, endIndex + 1).trim();

            
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

      
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(filteredJson.toString());
        }

        System.out.println("Objeto com code '" + teamCode + "' removido do JSON.");
    }
 
 
}