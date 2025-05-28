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
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

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
    public static Team readTeamFromParser(JsonParser parser) throws IOException {
        String filePlayers;
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
                case "founded" -> foundedYear = parser.getIntValue();
                case "name" -> name = parser.getValueAsString();
                case "stadium" -> stadiumName = parser.getValueAsString();
                default -> parser.skipChildren();
            }
        }

        Team club = new Team(code, country, logo, foundedYear, name, stadiumName);
        
        // Usa o nome diretamente como ficheiro
        String fileName = club.playerJsonFile();

        File playerFile = new File(fileName);
        
        if (playerFile.exists()) {
         try {
        IPlayer[] teamPlayers = PlayerImporterJSON.playersFromJson(fileName);
        for (IPlayer p : teamPlayers) {
            club.addPlayer(p);
        }
    } catch (Exception e) {
        System.out.println("Erro ao carregar jogadores para o clube " + name + ": " + e.getMessage());
    }
} else {
    System.out.println("Ficheiro de jogadores não encontrado para o clube " + name + ": " + fileName);
}
        
        
        return club;
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
    private static Squad readSquadFromParser(JsonParser parser,IClub[] clubs) throws IOException {
        
    Formation formation = null;
    Player[] players = new Player[MAX_TEAM];
    int playerCount = 0;

    LocalDate birthDate = null;
    PreferredFoot preferredFoot = PreferredFoot.Right;
    String name = null, nationality = null, basePosition = null, photo = null;
    int number = 0, age = 0, shooting = 0, stamina = 0, speed = 0, passing = 0;
    float height = 0f, weight = 0f;
    IClub squadClub = null;

    String clubName = null;

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String fieldName = parser.getCurrentName();
        if (fieldName == null) continue;
        parser.nextToken();

        switch (fieldName) {

            case "clubName" -> {
                clubName = parser.getValueAsString();
                for (IClub c : clubs) {  //Vai procurar no array de clubs pelo nome
                    
                    if (c.getName().equalsIgnoreCase(clubName)) {
                        squadClub = c;  
                        break;
                    }
                }
                if (squadClub == null) {
                    throw new IOException("Clube não encontrado: " + clubName);
                }
            }

            case "formation" -> {
                String formationName = parser.getValueAsString();
                 formation = new Formation(formationName);
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

                        if (playerCount < MAX_TEAM) {
                            PlayerStats stats = new PlayerStats(shooting, passing, stamina, speed);
                            IPlayerPosition position = new Position(basePosition);

                            players[playerCount++] = new Player(name, birthDate, age, nationality, number, photo, stats, position, preferredFoot, height, weight);
                        }

                        // Reset variáveis para o próximo jogador
                        birthDate = null;
                        name = nationality = basePosition = photo = null;
                        number = age = shooting = stamina = speed = passing = 0;
                    }
                }
            }

            case "teamStrength" -> parser.skipChildren();

            default -> parser.skipChildren();
        }
    }

    if (squadClub == null) {
        throw new IOException("Campo clubName não encontrado no JSON da squad.");
    }

    Squad squad = new Squad(squadClub, formation);

    for (int i = 0; i < playerCount; i++) {
        squad.addPlayer(players[i]);
    }

    return squad; 
        
   
}
    
    
    
    public static Squad[] squadsFromJson(String filePath,IClub[] clubs) throws IOException {
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

        Squad[] squads = new Squad[count];

        parser = factory.createParser(new File(filePath));
        parser.nextToken(); // pula START_ARRAY

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            squads[i++] = readSquadFromParser(parser,clubs);
        }

        parser.close();
        return squads;
}
    
    
    public static Squad mySquadFromJson(String filePath, IClub[] clubs) throws IOException {
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(new File(filePath));

    if (parser.nextToken() != JsonToken.START_OBJECT) {
        parser.close();
        throw new IOException("JSON inválido: esperado um objeto de squad");
    }

    Squad squad = readSquadFromParser(parser, clubs);

    parser.close();
    return squad;
}
}

