package api.data;

import static api.data.TeamImporterJSON.readSquadFromParser;
import static api.data.TeamImporterJSON.readTeamFromParser;
import api.league.League;
import api.league.Match;
import api.league.Season;
import api.league.Standing;
import api.team.Team;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.File;
import java.io.IOException;

/**
 * Classe responsável por importar dados de uma liga a partir de um ficheiro JSON.
 * Permite reconstruir objetos do tipo League, Season e Match a partir do conteúdo do ficheiro.
 * 
 * @author Gabriel
 */
public class LeagueImporterJSON {

    /**
     * Lê uma liga completa de um ficheiro JSON.
     *
     * @param filePath Caminho para o ficheiro JSON.
     * @return Objeto League reconstruído a partir do ficheiro.
     * @throws IOException Se ocorrer um erro ao ler ou interpretar o ficheiro.
     */
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

                // Conta as seasons
                JsonParser tempParser = factory.createParser(new File(filePath));
                while (tempParser.nextToken() != JsonToken.START_ARRAY) {}
                seasonsCount = 0;
                while (tempParser.nextToken() == JsonToken.START_OBJECT) {
                    seasonsCount++;
                    tempParser.skipChildren();
                }
                tempParser.close();

                seasons = new Season[seasonsCount];

                // Lê as seasons
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

    /**
     * Lê uma temporada (Season) a partir de um JSON parser.
     *
     * @param parser JsonParser posicionado no início de um objeto Season.
     * @return Objeto Season reconstruído.
     * @throws IOException Se ocorrer erro na leitura.
     */
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
                case "rounds" -> currentMatches = roundsFromJson(parser, maxRounds, 10);
                default -> parser.skipChildren();
            }
        }

        IClub[] clubs = TeamImporterJSON.teamsFromJson("clubs.json");
        ITeam[] squads = TeamImporterJSON.squadsFromJson("squad.json", clubs);

        Season season = new Season(name, year, maxTeams, maxRounds, pointsPerWin, pointsPerLoss, pointsPerDraw,squads, clubs);
        season.setTeams("squad.json", clubs);

        if (currentMatches != null) {
            season.setMatches(currentMatches);
        }

        season.generateSchedule();
        return season;
    }
    
    
     private static Standing readStandingFromParser(JsonParser parser) throws IOException {
        IClub[] clubs = TeamImporterJSON.teamsFromJson("clubs.json");
        ITeam team = null;
        int points = 0,wins = 0,losses = 0,draws = 0,goalsScored = 0,goalsConceded = 0;
      

        if (parser.currentToken() != JsonToken.START_OBJECT) {
            throw new IOException("Esperado objeto de season");
        }

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String field = parser.getCurrentName();
            parser.nextToken();

            switch (field) {
                case "team" -> {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                     team = readSquadFromParser(parser,clubs);
                } else {
                    throw new IOException("\"home\" deve ser um objeto JSON.");
                }
            }
                case "points" -> points =  parser.getIntValue();
                case "wins" -> wins =  parser.getIntValue();
                case "losses" -> losses =  parser.getIntValue();
                case "draws" -> draws =  parser.getIntValue();
                case "goalsScored" -> goalsScored =  parser.getIntValue();
                case "goalsConceded" -> goalsConceded =  parser.getIntValue();
                
                
                default -> parser.skipChildren();
            }
        }

       

        Standing standing = new Standing(team);
        
        standing.setPoints(points);
        standing.setWins(wins);
        standing.setDraws(draws);
        standing.setLosses(losses);
        standing.setGoalsScored(goalsScored);
        standing.setGoalsConceded(goalsConceded);
      
        return standing;
    }

    /**
     * Lê um jogo (Match) a partir de um JSON parser.
     *
     * @param parser JsonParser posicionado no início de um objeto Match.
     * @return Objeto Match reconstruído.
     * @throws IOException Se ocorrer erro na leitura.
     */
    private static Match readMatchFromParser(JsonParser parser) throws IOException {
    IClub[] clubs = TeamImporterJSON.teamsFromJson("clubs.json");
    ITeam home = null;
    ITeam away = null;
    boolean played = false;
    int round = 0;

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String field = parser.getCurrentName();
        parser.nextToken();

        switch (field) {
            case "home" -> {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    home = readSquadFromParser(parser,clubs);
                } else {
                    throw new IOException("\"home\" deve ser um objeto JSON.");
                }
            }
            case "away" -> {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    away = readSquadFromParser(parser, clubs);
                } else {
                    throw new IOException("\"away\" deve ser um objeto JSON.");
                }
            }
            case "played" -> played = parser.getBooleanValue();
            case "round" -> round = parser.getIntValue();
            default -> parser.skipChildren();
        }
    }

    if (home == null || away == null) {
        throw new IOException("Dados incompletos para criar o Match (home ou away null).");
    }

    Match match = new Match(home, away);
    match.setRound(round);
    if (played) {
        match.setPlayed();
    }

    return match;
    }

    
     public static Standing[] standingsFromJson(String filePath) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new File(filePath));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            parser.close();
            throw new IOException("JSON inválido: esperado um array de clubes");
        }

        // Contar número de clubes
        int count = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            count++;
            parser.skipChildren();
        }
        parser.close();

        Standing[] standings = new Standing[count];
        parser = factory.createParser(new File(filePath));
        parser.nextToken(); // Avança o START_ARRAY

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            standings[i++] = readStandingFromParser(parser);
        }

        parser.close();
        return standings;
    }
    
    /**
     * Lê um array de temporadas de um ficheiro JSON.
     *
     * @param filePath Caminho para o ficheiro JSON.
     * @return Array de objetos Season.
     * @throws IOException Se ocorrer erro na leitura.
     */
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
                while (tempParser.nextToken() != JsonToken.START_ARRAY) {}
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

    /**
     * Lê os jogos de todas as jornadas de uma temporada a partir do JSON.
     *
     * @param parser JsonParser posicionado no objeto "rounds".
     * @param maxRounds Número máximo de jornadas.
     * @param matchesPerRound Número máximo de jogos por jornada.
     * @return Matriz de jogos por jornada.
     * @throws IOException Se ocorrer erro na leitura do JSON.
     */
    private static Match[][] roundsFromJson(JsonParser parser, int maxRounds, int matchesPerRound) throws IOException {
        Match[][] rounds = new Match[maxRounds][];

        if (parser.currentToken() != JsonToken.START_OBJECT)
            throw new IOException("Esperado objeto de rounds");

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String roundKey = parser.getCurrentName(); // ex: "round_1"
            parser.nextToken();

            int roundIndex = Integer.parseInt(roundKey.split("_")[1]) - 1;

            if (parser.currentToken() != JsonToken.START_ARRAY)
                throw new IOException("Esperado array de jogos para " + roundKey);

            Match[] tempMatches = new Match[matchesPerRound];
            int matchCount = 0;

            while (parser.nextToken() == JsonToken.START_OBJECT) {
                Match match = readMatchFromParser(parser);
                tempMatches[matchCount++] = match;
            }

            Match[] matches = new Match[matchCount];
            for (int i = 0; i < matchCount; i++) {
                matches[i] = tempMatches[i];
            }

            rounds[roundIndex] = matches;
        }

        return rounds;
    }
}
