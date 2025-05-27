package api.league;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    private String file;

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
     * Atribui uma equipa ao jogo. A primeira equipa atribuída será a da casa, a segunda será a visitante.
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
     * Retorna o caminho do ficheiro onde o jogo será exportado.
     *
     * @return caminho do ficheiro
     */
    public String getFile() {
        return file;
    }

    /**
     * Define o caminho do ficheiro onde o jogo será exportado.
     *
     * @param file caminho do ficheiro
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Exporta os dados do jogo para um ficheiro JSON.
     * Inclui dados dos clubes da casa e visitante, se o jogo foi jogado e o número da jornada.
     */
    @Override
    public void exportToJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("{\n");

            writer.write(" \"home\": \n");
            writer.write("{\n");
            writer.write("  \"code\": \"" + home.getCode() + "\",\n");
            writer.write("  \"country\": \"" + home.getCountry() + "\",\n");
            writer.write("  \"logo\": \"" + home.getLogo() + "\",\n");
            writer.write("  \"foundedYear\": " + home.getFoundedYear() + ",\n");
            writer.write("  \"name\": \"" + home.getName() + "\",\n");
            writer.write("  \"stadiumName\": \"" + home.getStadiumName() + "\"\n");
            writer.write("},\n");

            writer.write(" \"away\": \n");
            writer.write("{\n");
            writer.write("  \"code\": \"" + away.getCode() + "\",\n");
            writer.write("  \"country\": \"" + away.getCountry() + "\",\n");
            writer.write("  \"logo\": \"" + away.getLogo() + "\",\n");
            writer.write("  \"foundedYear\": " + away.getFoundedYear() + ",\n");
            writer.write("  \"name\": \"" + away.getName() + "\",\n");
            writer.write("  \"stadiumName\": \"" + away.getStadiumName() + "\"\n");
            writer.write("},\n");

            writer.write(" \"played\": " + played + ",\n");
            writer.write(" \"round\": " + round + ",\n");

            writer.write("}\n");

        } catch (IOException e) {
            System.out.println("Erro ao exportar para JSON: " + e.getMessage());
        }
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
     * @return array de eventos
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
     * @return número de eventos
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
