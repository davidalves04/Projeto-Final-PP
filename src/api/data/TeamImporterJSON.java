/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;

import api.team.Team;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Utilizador
 */
public class TeamImporterJSON {
    // Importa um objeto Team a partir de um JSON
    private static Team readTeamFromParser(JsonParser parser) throws IOException {
        String code = null;
        String country = null;
        String logo = null;
        int foundedYear = 0;
        String name = null;
        String stadiumName = null;

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String field = parser.getCurrentName();
            parser.nextToken();

            switch (field) {
                case "code" -> code = parser.getValueAsString();
                case "country" -> country = parser.getValueAsString();
                case "logo" -> logo = parser.getValueAsString();
                case "foundedYear" -> foundedYear = parser.getIntValue();
                case "name" -> name = parser.getValueAsString();
                case "stadiumName" -> stadiumName = parser.getValueAsString();
                default -> parser.skipChildren();
            }
        }

        return new Team(code, country, logo, foundedYear, name, stadiumName);
    }

    // Importa todos os clubes do ficheiro JSON (array de objetos Team)
    public static Team[] teamsFromJson(String filePath) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new File(filePath));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            parser.close();
            throw new IOException("JSON inválido: esperado um array de clubes");
        }

        // Conta quantos clubes existem
        int count = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            count++;
            parser.skipChildren();
        }
        parser.close();

        Team[] teams = new Team[count];

        parser = factory.createParser(new File(filePath));
        parser.nextToken(); // pula START_ARRAY

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            teams[i++] = readTeamFromParser(parser);
        }

        parser.close();
        return teams;
    }
}
