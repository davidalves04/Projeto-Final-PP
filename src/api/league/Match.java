/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.league;

import api.event.GoalEvent;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.IOException;

import utils.JsonAccumulator;

/**
 * Implementação da interface {@link IMatch}, representando um jogo entre dois clubes.
 */
public class Match implements IMatch {

    
    private boolean played;
    private ITeam homeTeam;
    private ITeam awayTeam;
    private int round;
    private IEvent[] events;
    private int eventCount;

    private JsonAccumulator jsonAccumulator;

    /**
     * Construtor que inicializa um jogo entre dois clubes.
     *
     * @param home clube da casa
     * @param away clube visitante
     */
    public Match(ITeam home, ITeam away) {
        this.homeTeam = home;
        this.awayTeam = away;
        this.played = false;
        this.events = new IEvent[100]; // tamanho máximo arbitrário
        this.eventCount = 0;
    }

    /**
     * Retorna o clube da casa.
     *
     * @return o clube da casa
     */
    @Override
    public IClub getHomeClub() {
        return homeTeam.getClub();
    }

    /**
     * Retorna o clube visitante.
     *
     * @return o clube visitante
     */
    @Override
    public IClub getAwayClub() {
        return awayTeam.getClub();
    }

    /**
     * Indica se o jogo já foi jogado.
     *
     * @return true se o jogo foi jogado, false caso contrário
     */
    @Override
    public boolean isPlayed() {
        return played;
    }

    /**
     * Retorna a equipa associada ao clube da casa.
     *
     * @return equipa da casa
     */
    @Override
    public ITeam getHomeTeam() {
        return homeTeam;
    }

    /**
     * Retorna a equipa associada ao clube visitante.
     *
     * @return equipa visitante
     */
    @Override
    public ITeam getAwayTeam() {
        return awayTeam;
    }

    /**
     * Define o jogo como jogado.
     */
    @Override
    public void setPlayed() {
        this.played = true;
    }

    /**
     * Retorna o total de eventos de um determinado tipo para um clube.
     *
     * @param type tipo de evento (classe)
     * @param club clube para o qual os eventos serão contados
     * @return número de eventos do tipo especificado para o clube
     */
    @Override
    public int getTotalByEvent(Class type, IClub club) {
        int count = 0;
        for (int i = 0; i < eventCount; i++) {
            IEvent e = events[i];
            if (type.isInstance(e) && e instanceof IGoalEvent) {
                IGoalEvent ge = (IGoalEvent) e;
                IPlayer[] jogadores = club.getPlayers();
                if (jogadores != null) {
                    for (IPlayer p : jogadores) {
                        if (p.equals(ge.getPlayer())) {
                            count++;
                            break;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * Verifica se o jogo é válido (ambos os clubes são diferentes e não nulos).
     *
     * @return true se válido, false caso contrário
     */
    @Override
    public boolean isValid() {
        return homeTeam != null && awayTeam != null && !homeTeam.equals(awayTeam);
    }

    /**
     * Retorna a equipa vencedora do jogo.
     *
     * @return equipa vencedora ou null em caso de empate
     */
    @Override
    public ITeam getWinner() {
        int homeGoals = getTotalByEvent(IGoalEvent.class, homeTeam.getClub());
        int awayGoals = getTotalByEvent(IGoalEvent.class, awayTeam.getClub());

        if (homeGoals > awayGoals) return homeTeam;
        if (awayGoals > homeGoals) return awayTeam;
        return null;
    }

    /**
     * Retorna o número da jornada (round).
     *
     * @return número da jornada
     */
    @Override
    public int getRound() {
        return round;
    }

    /**
     * Define o número da jornada (round).
     *
     * @param round número da jornada
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Atribui uma equipa ao jogo. A primeira equipa atribuída será a da casa,
     * a segunda será a visitante.
     *
     * @param team equipa a associar
     */
    @Override
    public void setTeam(ITeam team) {
        if (homeTeam == null) {
            homeTeam = team;
        } else if (awayTeam == null) {
            awayTeam = team;
        }
    }

    /**
     * Define o acumulador JSON usado para exportar dados do jogo.
     *
     * @param accumulator acumulador JSON a usar
     */
    public void setJsonAccumulator(JsonAccumulator accumulator) {
        this.jsonAccumulator = accumulator;
    }

    /**
     * Exporta os dados do jogo para um ficheiro JSON.
     * Inclui dados dos clubes da casa e visitante, golos, resultado,
     * estado do jogo, número da jornada e eventos registados.
     */
    

@Override
public void exportToJson() throws IOException {
    if (jsonAccumulator == null) {
        System.err.println("JsonAccumulator não definido para Match!");
        return;
    }

    jsonAccumulator.append("{\n");

    // HOME TEAM
    jsonAccumulator.append("  \"home\": {\n");
    jsonAccumulator.append("    \"clubName\": \"" + homeTeam.getClub().getName() + "\",\n");
    jsonAccumulator.append("    \"formation\": \"" + homeTeam.getFormation().getDisplayName() + "\",\n");
    jsonAccumulator.append("    \"teamStrength\": " + homeTeam.getTeamStrength() + "\n");
    jsonAccumulator.append("  },\n");

    // AWAY TEAM
    jsonAccumulator.append("  \"away\": {\n");
    jsonAccumulator.append("    \"clubName\": \"" + awayTeam.getClub().getName() + "\",\n");
    jsonAccumulator.append("    \"formation\": \"" + awayTeam.getFormation().getDisplayName() + "\",\n");
    jsonAccumulator.append("    \"teamStrength\": " + awayTeam.getTeamStrength() + "\n");
    jsonAccumulator.append("  },\n");

    // MATCH STATUS
    jsonAccumulator.append("  \"played\": " + this.played + ",\n");
    jsonAccumulator.append("  \"round\": " + this.round + ",\n");

    // EVENTS
    jsonAccumulator.append("  \"events\": [\n");
    for (int i = 0; i < eventCount; i++) {
        IEvent event = events[i];

        JsonAccumulator eventAccumulator = new JsonAccumulator();

        try {
            event.getClass().getMethod("setJsonAccumulator", JsonAccumulator.class)
                 .invoke(event, eventAccumulator);
        } catch (Exception e) {
            System.err.println("Não foi possível injetar JsonAccumulator no evento: " + e.getMessage());
            continue;
        }

        try {
            event.exportToJson();
        } catch (Exception e) {
            System.err.println("Erro ao exportar evento: " + e.getMessage());
            continue;
        }

        String eventJson = eventAccumulator.getJson().trim();  // Aqui está o fix importante

        jsonAccumulator.append(eventJson);

        if (i < eventCount - 1) {
            jsonAccumulator.append(",\n");
        } else {
            jsonAccumulator.append("\n");
        }
    }
    jsonAccumulator.append("  ]\n");

    jsonAccumulator.append("}");
}

    /**
     * Adiciona um evento ao jogo.
     *
     * @param event evento a adicionar
     */
    @Override
    public void addEvent(IEvent event) {
        if (eventCount < events.length) {
            events[eventCount++] = event;
        }
    }

    /**
     * Retorna todos os eventos registados no jogo.
     *
     * @return array com os eventos registados
     */
    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            result[i] = events[i];
        }
        return result;
    }

    /**
     * Retorna o número total de eventos registados no jogo.
     *
     * @return número total de eventos
     */
    @Override
    public int getEventCount() {
        return eventCount;
    }

    /**
     * Reinicia o estado do jogo, removendo todos os eventos e marcando-o como não jogado.
     */
    public void reset() {
        played = false;
        eventCount = 0;
    }
}
