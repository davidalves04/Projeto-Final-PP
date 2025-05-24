/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;

import api.player.Player;
import api.player.PlayerStats;
import api.player.Position;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author Utilizador
 */
public class PlayerImporterJSON {
        // Importa um jogador a partir de um JSON
    private static Player readPlayerFromParser(JsonParser parser) throws IOException {
        String name = null, nationality = null, photo = null, positionDesc = null;
        PreferredFoot preferredFoot = PreferredFoot.Right;
        LocalDate birthDate = null;
        int number = 0, shooting = 0, passing = 0, stamina = 0, speed = 0, age = 0;
        float height = 0f, weight = 0f;

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String field = parser.getCurrentName();
            parser.nextToken();

            switch (field) {
                case "name" -> name = parser.getValueAsString();
                case "birthDate" -> birthDate = LocalDate.parse(parser.getValueAsString());
                case "nationality" -> nationality = parser.getValueAsString();
                case "photo" -> photo = parser.getValueAsString();
                case "number" -> number = parser.getIntValue();
                case "basePosition" -> positionDesc = parser.getValueAsString();
                case "preferredFoot" -> preferredFoot = PreferredFoot.fromString(parser.getValueAsString());
                case "height" -> height = (float) parser.getDoubleValue();
                case "weight" -> weight = (float) parser.getDoubleValue();
                case "age" -> age = parser.getIntValue();
                case "shootingstats" -> shooting = parser.getIntValue();
                case "passingstats" -> passing = parser.getIntValue();
                case "staminastats" -> stamina = parser.getIntValue();
                case "speedstats" -> speed = parser.getIntValue();
                default -> parser.skipChildren();
            }
        }

        PlayerStats stats = new PlayerStats(shooting, passing, stamina, speed);
        IPlayerPosition position = new Position(positionDesc);

        return new Player(name, birthDate, age, nationality, number, photo, stats, position, preferredFoot, height, weight);
    }
    
    
    //Importar todos os jogadores do ficheiro JSON
     public static Player[] playersFromJson(String filePath) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new File(filePath));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            parser.close();
            throw new IOException("JSON inválido: esperado um array de jogadores");
        }

        // Conta quantos jogadores
        int count = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            count++;
            parser.skipChildren();
        }
        parser.close();

        Player[] players = new Player[count];

        parser = factory.createParser(new File(filePath));
        parser.nextToken(); // pula START_ARRAY

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            players[i++] = readPlayerFromParser(parser);
        }

        parser.close();
        return players;
    }
}
