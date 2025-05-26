/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;

import api.player.Player;
import api.player.PlayerStats;
import api.player.Position;
import api.team.Formation;
import api.team.Squad;
import api.team.Team;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author Utilizador
 */
public class TeamImporterJSON {
    private final static int MAX_TEAM = 18;
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
    
    
    //Importa a squad 
    private static Squad readSquadFromParser(JsonParser parser, IClub club) throws IOException {
    
        
    Formation formation = null;

    // Array fixo para jogadores, e contador
    Player[] players = new Player[MAX_TEAM];
    int playerCount = 0;

    // Variáveis temporárias para jogador
    LocalDate birthDate = null;
     PreferredFoot preferredFoot = PreferredFoot.Right;
    String name = null,  nationality = null, basePosition = null, photo = null;
    int number = 0, age = 0, shooting = 0, stamina = 0, speed = 0, passing = 0;
    float height = 0f, weight = 0f;


    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String fieldName = parser.getCurrentName();
        if (fieldName == null) continue;
        parser.nextToken();

        switch (fieldName) {
            case "formation" -> {
                String formationName = parser.getValueAsString();
                formation.setDisplayName(formationName);
            }
            case "players" -> {
                if (parser.currentToken() == JsonToken.START_ARRAY) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        // Ler jogador
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                            String playerField = parser.getCurrentName();
                            parser.nextToken();
                            switch (playerField) {
                                case "name" -> name = parser.getValueAsString();
                                case "birthDate" -> birthDate = LocalDate.parse(parser.getValueAsString());
                                case "nationality" -> nationality = parser.getValueAsString();
                                case "basePosition" -> basePosition = parser.getValueAsString();
                                case "photo" -> photo = parser.getValueAsString();
                                case "number" -> number = parser.getIntValue();
                                case "age" -> age = parser.getIntValue();
                                case "preferredFoot" -> preferredFoot = PreferredFoot.fromString(parser.getValueAsString());
                case "height" -> height = (float) parser.getDoubleValue();
                case "weight" -> weight = (float) parser.getDoubleValue();
                                case "shootingstats" -> shooting = parser.getIntValue();
                                case "staminastats" -> stamina = parser.getIntValue();
                                case "speedstats" -> speed = parser.getIntValue();
                                case "passingstats" -> passing = parser.getIntValue();
                                default -> parser.skipChildren();
                            }
                        }

                        if (playerCount < 18) {
                             PlayerStats stats = new PlayerStats(shooting, passing, stamina, speed);
        IPlayerPosition position = new Position(basePosition);

                            
                            
                            players[playerCount++] = new Player(name, birthDate, age, nationality, number, photo, stats, position, preferredFoot, height, weight);
                        }

                        // Reset variáveis para o próximo jogador
                        birthDate = null;
                        name =  nationality = basePosition = photo = null;
                        number = age = shooting = stamina = speed = passing = 0;
                    }
                }
            }
            case "teamStrength" -> parser.skipChildren();
            default -> parser.skipChildren();
        }
    }

    Squad squad = new Squad(club, formation);

    for (int i = 0; i < playerCount; i++) {
        squad.addPlayer(players[i]);
    }

    return squad;
}
    
    
    
    public static Squad loadSquadFromJson(String filePath, IClub club) throws IOException {
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(new File(filePath));

    if (parser.nextToken() != JsonToken.START_OBJECT) {
        parser.close();
        throw new IOException("JSON inválido: esperado um objeto JSON que representa a squad");
    }

    Squad squad = readSquadFromParser(parser, club);

    parser.close();
    return squad;
}
    
}
