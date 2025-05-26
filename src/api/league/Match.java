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
    
    public Match(IClub home, IClub away) {
        this.home = home;
        this.away = away;
        this.played = false;
        this.events = new IEvent[100]; // tamanho máximo arbitrário
        this.eventCount = 0;
    }

    @Override
    public IClub getHomeClub() {
        return home;
    }

    @Override
    public IClub getAwayClub() {
        return away;
    }

    @Override
    public boolean isPlayed() {
        return played;
    }

    @Override
    public ITeam getHomeTeam() {
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        return awayTeam;
    }

    @Override
    public void setPlayed() {
        this.played = true;
    }

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

    @Override
    public boolean isValid() {
        return home != null && away != null && !home.equals(away);
    }

    @Override
    public ITeam getWinner() {
        int homeGoals = getTotalByEvent(IGoalEvent.class, home);
        int awayGoals = getTotalByEvent(IGoalEvent.class, away);

        if (homeGoals > awayGoals) return homeTeam;
        if (awayGoals > homeGoals) return awayTeam;
        return null;
    }

    @Override
    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public void setTeam(ITeam team) {
        if (homeTeam == null) {
            homeTeam = team;
        } else if (awayTeam == null) {
            awayTeam = team;
        }
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    
    
   @Override
    public void exportToJson() {
         
        
               try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
                  
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


    @Override
    public void addEvent(IEvent event) {
        if (eventCount < events.length) {
            events[eventCount++] = event;
        }
    }

    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            result[i] = events[i];
        }
        return result;
    }

    @Override
    public int getEventCount() {
        return eventCount;
    }

    public void reset() {
        played = false;
        eventCount = 0;
    }
} 
