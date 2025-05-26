package api.data;

import static api.data.TeamImporterJSON.readTeamFromParser;
import api.league.League;
import api.league.Match;
import api.player.Player;
import api.player.PlayerStats;
import api.player.Position;
import api.team.Team;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;



/**
 *
 * @author Utilizador
 */
public class LeagueImporterJSON {
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

    

    
    // Importa um array de ligas a partir de um ficheiro JSON
    public static League[] leaguesFromJson(String filePath) throws IOException {
       
    }
}