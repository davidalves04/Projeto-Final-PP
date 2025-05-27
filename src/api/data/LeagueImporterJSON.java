package api.data;

import api.league.League;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import java.io.File;
import java.io.IOException;


public class LeagueImporterJSON {
    private static League readLeagueFromParser(JsonParser parser) throws IOException {
        String name = null;
        // Temporariamente guardamos as seasons como null (ou vazio)
        ISeason[] seasons = new ISeason[0];

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String field = parser.getCurrentName();
            parser.nextToken();

            switch (field) {
                case "name" -> name = parser.getValueAsString();
                case "seasons" -> {
                    if (parser.currentToken() == JsonToken.START_ARRAY) {
                       
                        seasons = readSeasonsArray(parser);
                    } else {
                        parser.skipChildren();
                    }
                }
                default -> parser.skipChildren();
            }
        }

        League league = new League(name);

        // Adiciona as seasons (aqui só adiciona as vazias, tu podes adaptar depois)
        for (ISeason season : seasons) {
            league.createSeason(season);
        }
        return league;
    }

    // Método para ler array de seasons (por enquanto vazio)
    private static ISeason[] readSeasonsArray(JsonParser parser) throws IOException {
        // Avança até o fim do array ignorando o conteúdo por enquanto
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            parser.skipChildren();
        }
        return new ISeason[0];
    }

    
    // Importa um array de ligas a partir de um ficheiro JSON
    public static League[] leaguesFromJson(String filePath) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new File(filePath));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            parser.close();
            throw new IOException("JSON inválido: esperado um array de ligas");
        }

        // Conta quantas ligas tem o array
        int count = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            count++;
            parser.skipChildren();
        }
        parser.close();

        League[] leagues = new League[count];

        parser = factory.createParser(new File(filePath));
        parser.nextToken(); // pula START_ARRAY

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            leagues[i++] = readLeagueFromParser(parser);
        }

        parser.close();
        return leagues;
}
}