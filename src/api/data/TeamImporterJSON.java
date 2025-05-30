/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
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
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Classe responsável por importar dados de clubes e plantéis (Squads) a partir de ficheiros JSON.
 * Utiliza a biblioteca Jackson para fazer parsing do JSON.
 * Permite importar clubes completos, plantéis dos clubes e o plantel do utilizador.
 * 
 * @author Gabriel
 */
public class TeamImporterJSON {

    private final static int MAX_TEAM = 18;

    /**
     * Lê e cria um objeto {@link Team} a partir de um parser JSON.
     * Também tenta carregar os jogadores do clube, caso exista um ficheiro correspondente.
     *
     * @param parser Parser JSON já posicionado no início do objeto.
     * @return Objeto Team construído a partir do JSON.
     * @throws IOException Se ocorrer um erro durante a leitura.
     */
    public static Team readTeamFromParser(JsonParser parser) throws IOException {
        String code = null, country = null, logo = null, name = null, stadiumName = null;
        int foundedYear = 0;

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

    /**
     * Lê um ficheiro JSON com uma lista de clubes e converte cada um num objeto {@link Team}.
     *
     * @param filePath Caminho para o ficheiro JSON.
     * @return Array de objetos Team lidos do ficheiro.
     * @throws IOException Se o JSON estiver mal formado ou ocorrer erro de leitura.
     */
    public static Team[] teamsFromJson(String filePath) throws IOException {
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

        Team[] teams = new Team[count];
        parser = factory.createParser(new File(filePath));
        parser.nextToken(); // Avança o START_ARRAY

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            teams[i++] = readTeamFromParser(parser);
        }

        parser.close();
        return teams;
    }


    /**
     * Lê e constrói um objeto {@link Squad} a partir de um parser JSON.
     * Procura o clube correspondente no array fornecido.
     *
     * @param parser Parser JSON já posicionado no início do objeto Squad.
     * @param clubs  Array de clubes disponíveis.
     * @return Squad lido do ficheiro.
     * @throws IOException Se faltar o campo do clube ou se ocorrer erro de parsing.
     */
    public static Squad readSquadFromParser(JsonParser parser, IClub[] clubs) throws IOException {
         Formation formation = null;
    Player[] players = new Player[MAX_TEAM];
    int playerCount = 0;
    IClub squadClub = null;
    int teamStrength = 0; // 👈 nova variável

    // Variáveis temporárias para jogador
    LocalDate birthDate = null;
    PreferredFoot preferredFoot = PreferredFoot.Right;
    String name = null, nationality = null, basePosition = null, photo = null;
    int number = 0, age = 0, shooting = 0, stamina = 0, speed = 0, passing = 0, defense = 0;
    float height = 0f, weight = 0f;

    String clubName = null;

    while (parser.nextToken() != JsonToken.END_OBJECT) {
        String fieldName = parser.getCurrentName();
        if (fieldName == null) continue;
        parser.nextToken();

        switch (fieldName) {
            case "clubName" -> {
                clubName = parser.getValueAsString();
                for (IClub c : clubs) {
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
                        // Reset vars antes de ler jogador
                        birthDate = null;
                        preferredFoot = PreferredFoot.Right;
                        name = nationality = basePosition = photo = null;
                        number = age = shooting = stamina = speed = passing = defense = 0;
                        height = weight = 0f;

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
                                case "defensestats" -> defense = parser.getIntValue();
                                default -> parser.skipChildren();
                            }
                        }

                        if (playerCount < MAX_TEAM) {
                            PlayerStats stats = new PlayerStats(shooting, passing, stamina, speed, defense);
                            IPlayerPosition position = new Position(basePosition);
                            players[playerCount++] = new Player(name, birthDate, age, nationality, number, photo, stats, position, preferredFoot, height, weight);
                        }
                    }
                }
            }

            case "teamStrength" -> teamStrength = parser.getIntValue(); // ✅ Aqui lês o valor
        }
    }

    if (squadClub == null) {
        throw new IOException("Campo clubName não encontrado no JSON da squad.");
    }

    Squad squad = new Squad(squadClub, formation);
    for (int i = 0; i < playerCount; i++) {
        squad.addPlayer(players[i]);
    }

    // Se quiseres guardar ou usar o teamStrength, podes por exemplo fazer:
    // squad.setTeamStrength(teamStrength); // se o método existir

    return squad;

    }

    /**
     * Importa vários objetos {@link Squad} a partir de um ficheiro JSON.
     * Cada objeto Squad deve ter um campo "clubName" que seja compatível com os clubes disponíveis.
     *
     * @param filePath Caminho para o ficheiro JSON.
     * @param clubs Array de clubes já carregados.
     * @return Array de squads lidos.
     * @throws IOException Se o ficheiro estiver mal formado ou clube não for encontrado.
     */
    public static Squad[] squadsFromJson(String filePath, IClub[] clubs) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new File(filePath));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            parser.close();
            throw new IOException("JSON inválido: esperado um array de squads");
        }

        int count = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            count++;
            parser.skipChildren();
        }
        parser.close();

        Squad[] squads = new Squad[count];
        parser = factory.createParser(new File(filePath));
        parser.nextToken();

        int i = 0;
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            squads[i++] = readSquadFromParser(parser, clubs);
        }

        parser.close();
        return squads;
    }

    /**
     * Importa o plantel (Squad) personalizado do utilizador a partir de um ficheiro JSON.
     *
     * @param filePath Caminho para o ficheiro JSON do plantel.
     * @param clubs Array de clubes disponíveis para associar o plantel.
     * @return Squad do utilizador lido do ficheiro.
     * @throws IOException Se ocorrer erro no parsing ou se o clube não for encontrado.
     */
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
    
   public static Squad findTeamByClub(IClub club) throws IOException {
     for (Squad team : squadsFromJson("squad.json", new IClub[]{club})) {
        if (team.getClub().getName().equalsIgnoreCase(club.getName())) {
            return team;
        }
    }
    return null;
    
   
}

public static Team findClubByName(String name) throws IOException {
          
     Team[] clubs = teamsFromJson("clubs.json");



    for (Team club : clubs) {
        String clubName = club.getName();
        

        if (normalize(clubName).equals(normalize(name))) {
   
            return club;
        }
    }


    return null;
}

private static String normalize(String name) {
    return name.trim().toLowerCase();
}
    
}
