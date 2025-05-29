package api.league;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import utils.JsonAccumulator;

/**
 * Implementação da interface {@link IMatch}, representando um jogo entre dois clubes.
 */
public class Match implements IMatch {

    private final IClub home;
    private final IClub away;
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
    public Match(IClub home, IClub away) {
        this.home = home;
        this.away = away;
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
        return home;
    }

    /**
     * Retorna o clube visitante.
     *
     * @return o clube visitante
     */
    @Override
    public IClub getAwayClub() {
        return away;
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
        return home != null && away != null && !home.equals(away);
    }

    /**
     * Retorna a equipa vencedora do jogo.
     *
     * @return equipa vencedora ou null em caso de empate
     */
    @Override
    public ITeam getWinner() {
        int homeGoals = getTotalByEvent(IGoalEvent.class, home);
        int awayGoals = getTotalByEvent(IGoalEvent.class, away);

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
    public void exportToJson() {
        if (jsonAccumulator == null) {
            System.err.println("JsonAccumulator não definido para Match!");
            return;
        }

        jsonAccumulator.append("{");

        jsonAccumulator.append("  \"home\": {");
        jsonAccumulator.append("    \"code\": \"" + home.getCode() + "\",");
        jsonAccumulator.append("    \"name\": \"" + home.getName() + "\"");
        jsonAccumulator.append("  },");

        jsonAccumulator.append("  \"away\": {");
        jsonAccumulator.append("    \"code\": \"" + away.getCode() + "\",");
        jsonAccumulator.append("    \"name\": \"" + away.getName() + "\"");
        jsonAccumulator.append("  },");

        // Golos
        int homeGoals = getTotalByEvent(IGoalEvent.class, home);
        int awayGoals = getTotalByEvent(IGoalEvent.class, away);
        jsonAccumulator.append("  \"homeGoals\": " + homeGoals + ",");
        jsonAccumulator.append("  \"awayGoals\": " + awayGoals + ",");

        // Resultado
        String result;
        if (homeGoals > awayGoals) {
            result = "HOME_WIN";
        } else if (awayGoals > homeGoals) {
            result = "AWAY_WIN";
        } else {
            result = "DRAW";
        }
        jsonAccumulator.append("  \"result\": \"" + result + "\",");

        // Outros metadados
        jsonAccumulator.append("  \"played\": " + played + ",");
        jsonAccumulator.append("  \"round\": " + round + ",");

        // Eventos
        jsonAccumulator.append("  \"events\": [");
        for (int i = 0; i < eventCount; i++) {
            IEvent e = events[i];
            jsonAccumulator.append("    {");
            jsonAccumulator.append("      \"minute\": " + e.getMinute() + ",");
            jsonAccumulator.append("      \"description\": \"" + e.getDescription().replace("\"", "'") + "\"");
            jsonAccumulator.append("    }" + (i < eventCount - 1 ? "," : ""));
        }
        jsonAccumulator.append("  ]");

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
