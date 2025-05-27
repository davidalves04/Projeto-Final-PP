
package api.data;

import static api.data.TeamImporterJSON.readTeamFromParser;
import api.league.League;
import api.league.Match;



import api.league.Season;


import api.team.Team;
import com.fasterxml.jackson.core.JsonFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;


import com.ppstudios.footballmanager.api.contracts.team.IClub;


import java.io.File;
import java.io.IOException;





/**
 *
 * @author Utilizador
 */
public class LeagueImporterJSON {

    public static League readLeagueFromFile(String filePath) throws IOException {
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(new File(filePath));

    String leagueName = null;
    Season[] seasons = null;
    int seasonsCount = 0;

    if (parser.nextToken() != JsonToken.START_OBJECT) {
        parser.close();
        throw new IOException("Esperado objeto JSON para a liga");
    }

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String field = parser.getCurrentName();
        parser.nextToken();

        if ("name".equals(field)) {
            leagueName = parser.getValueAsString();
        } else if ("seasons".equals(field)) {
            if (parser.currentToken() != JsonToken.START_ARRAY) {
                parser.close();
                throw new IOException("Esperado array para seasons");
            }
            
            // Primeiro passo: contar as seasons
            JsonParser tempParser = factory.createParser(new File(filePath));
            while (tempParser.nextToken() != JsonToken.START_ARRAY) {} // pular até array de seasons
            seasonsCount = 0;
            while (tempParser.nextToken() == JsonToken.START_OBJECT) {
                seasonsCount++;
                tempParser.skipChildren();
            }
            tempParser.close();

            seasons = new Season[seasonsCount];

            // Agora, ler as seasons de verdade (parser já está no começo do array)
            int i = 0;
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                seasons[i++] = readSeasonFromParser(parser);
            }
        } else {
            parser.skipChildren();
        }
    }
    parser.close();

    League league = new League(leagueName);
    for (int i = 0; i < seasonsCount; i++) {
        league.createSeason(seasons[i]);
    }

    return league;
}
    
    
      
    
 
    
     private static Season readSeasonFromParser(JsonParser parser) throws IOException {
         
         String name = null;
    int year  = 0; 
    int pointsPerWin = 0;
    int pointsPerLoss = 0;
    int pointsPerDraw = 0;
    int maxTeams = 0;
    int maxRounds = 0;
    Match[][] currentMatches = null;

    if (parser.currentToken() != JsonToken.START_OBJECT) {
        throw new IOException("Esperado objeto de season");
    }

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String field = parser.getCurrentName();
        parser.nextToken();

        switch (field) {
            case "name" -> name = parser.getValueAsString(); 
            case "year" -> year = parser.getIntValue();
            case "maxTeams" -> maxTeams = parser.getIntValue();
            case "maxRounds" -> maxRounds = parser.getIntValue();
            case "pointsPerWin" -> pointsPerWin = parser.getIntValue();
            case "pointsPerLoss" -> pointsPerLoss = parser.getIntValue();
            case "pointsPerDraw" -> pointsPerDraw = parser.getIntValue();
            case "rounds" -> {
                // Aqui o parser está no START_OBJECT de "rounds"
                currentMatches = roundsFromJson(parser, maxRounds, 10);
            }
            default -> parser.skipChildren();
        }
    }

    IClub[] clubs = TeamImporterJSON.teamsFromJson("clubs.json");
    
    
    Season season = new Season(name, year, maxTeams, maxRounds, pointsPerWin, pointsPerLoss, pointsPerDraw, clubs);
    if (currentMatches != null) {
        season.setMatches(currentMatches);
    }
    season.setTeams("squad.json", clubs);
    season.generateSchedule();
    
        return season;
        
    }
    
   
    private static Match readMatchFromParser(JsonParser parser) throws IOException {
        
       Team home = null;
    Team away = null;
    boolean played = false;
    int round = 0;

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String field = parser.getCurrentName();
        parser.nextToken();

        switch (field) {
            case "home" -> home = readTeamFromParser(parser);
            case "away" -> away = readTeamFromParser(parser);
            case "played" -> played = parser.getBooleanValue();
            case "round" -> round = parser.getIntValue();
            default -> parser.skipChildren();
        }
    }

    Match match = new Match(home, away);
    match.setRound(round);
    if (played) {
        match.setPlayed();
    }
    return match;
    }

    
public static Season[] readSeasonsFromFile(String filePath) throws IOException {
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(new File(filePath));

    Season[] seasons = null;
    int seasonsCount = 0;

    if (parser.nextToken() != JsonToken.START_OBJECT) {
        parser.close();
        throw new IOException("Esperado objeto JSON no início");
    }

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String field = parser.getCurrentName();
        parser.nextToken();

        if ("seasons".equals(field)) {
            if (parser.currentToken() != JsonToken.START_ARRAY) {
                parser.close();
                throw new IOException("Esperado array para seasons");
            }

            // Contar seasons
            JsonParser tempParser = factory.createParser(new File(filePath));
            while (tempParser.nextToken() != JsonToken.START_ARRAY) {} // pular até array seasons
            seasonsCount = 0;
            while (tempParser.nextToken() == JsonToken.START_OBJECT) {
                seasonsCount++;
                tempParser.skipChildren();
            }
            tempParser.close();

            seasons = new Season[seasonsCount];

            // Ler seasons para o array
            int i = 0;
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                seasons[i++] = readSeasonFromParser(parser);
            }
        } else {
            parser.skipChildren();
        }
    }
    parser.close();

    return seasons;
}
      
    private static Match[][] roundsFromJson(JsonParser parser, int maxRounds, int matchesPerRound) throws IOException {
    Match[][] rounds = new Match[maxRounds][matchesPerRound];

    if (parser.currentToken() != JsonToken.START_OBJECT)
        throw new IOException("Esperado objeto de rounds");

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String roundKey = parser.getCurrentName(); 
        parser.nextToken();

       int roundIndex = Integer.parseInt(roundKey.split("_")[1]) - 1;


        if (parser.currentToken() != JsonToken.START_ARRAY)
            throw new IOException("Esperado array de jogos para " + roundKey);

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            rounds[roundIndex][i++] = readMatchFromParser(parser);
        }
    }

    return rounds;
}

  
    
    
}