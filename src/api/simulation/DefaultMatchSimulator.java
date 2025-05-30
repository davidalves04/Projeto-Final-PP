 package api.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import java.util.Random;
import api.event.*;
import api.league.Standing;
import api.player.Player;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 * Implementação padrão da estratégia de simulação de um jogo de futebol.
 * 
 * Esta classe simula um jogo com eventos aleatórios durante os 90 minutos,
 * tais como faltas, passes, remates e golos, baseando-se nos atributos dos jogadores.
 * 
 * A simulação adiciona eventos ao objeto {@link IMatch} recebido e marca o jogo como jogado no final.
 * 
 * A probabilidade de ocorrência dos eventos é determinada por valores aleatórios e
 * atributos técnicos dos jogadores, como remate, passe e resistência.
 * 
 * @author 
 */
public class DefaultMatchSimulator implements MatchSimulatorStrategy {

    private final Random random = new Random();
    

    /**
     * Simula um jogo de futebol adicionando eventos durante 90 minutos.
     * 
     * Em cada minuto há 20% de chance de ocorrer um evento, que pode ser:
     * falta, passe, remate (golo, remate enquadrado ou falhado).
     * 
     * Os eventos são determinados com base nas estatísticas do jogador atacante
     * e adicionados ao objeto {@code match}.
     * 
     * No fim, o jogo é marcado como jogado.
     * 
     * @param match jogo a ser simulado, onde os eventos serão registados
     */
    @Override
 public void simulate(IMatch match) {
    ITeam home = match.getHomeTeam();
    ITeam away = match.getAwayTeam();
 
    int homeGoals = 0;
    int awayGoals = 0;
     

   

    
    
    for (int minute = 1; minute <= 90; minute++) {
        double eventProbability = random.nextDouble();
        if (eventProbability > 0.06) continue; //Probabilidade de 6% de uma oportunidade

        boolean isCounterAttack = eventProbability < 0.03;
        boolean isCorner = eventProbability < 0.04;
        boolean isPassGood = eventProbability < 0.05;

        ITeam attackingClub = random.nextBoolean() ? home : away;
        ITeam defendingClub = (attackingClub == home) ? away : home;

        IPlayer[] attackers = attackingClub.getPlayers();
        
        
        IPlayer[] defenders = defendingClub.getPlayers();

        if (attackers.length == 0 || defenders.length == 0) continue;

        // 🔍 Selecionar o guarda-redes da equipa defensora
        Player goalkeeper = null;
        for (IPlayer p : defenders) {
            if (p instanceof Player && ((Player)p).getPosition().getDescription().equalsIgnoreCase("Goalkeeper")) {
                goalkeeper = (Player) p;
                break;
            }
        }

       

      
        Player attacker = null;
        Player playmaker = null;
        
        
        while (attacker == null || playmaker == null) {
            Player temp = (Player) attackers[random.nextInt(attackers.length)];
            if (temp.getPosition().getDescription().equalsIgnoreCase("Forward")) {
                attacker = temp;
            }else if (temp.getPosition().getDescription().equalsIgnoreCase("Midfielder")) {
                playmaker = temp;
            }
        }

        
        int shooting = attacker.getShooting();
        int defense = goalkeeper.getDefense();

       

        

        if (shooting > defense) {
            GoalEvent goal = new GoalEvent(attackingClub, attacker,goalkeeper, minute);
            match.addEvent(goal);
            
            if (attackingClub == home) homeGoals++; else awayGoals++;
        } else {
            MissedShotEvent missedGoal = new MissedShotEvent(attackingClub,attacker,goalkeeper,minute);
           
                    
        }
    }

    match.setPlayed();
    
    System.out.println("\nResultado final:");
    System.out.println(home.getClub().getName() + " " + homeGoals + " x " + awayGoals + " " + away.getClub().getName());
     System.out.println("");
    
}
 
 public void detailedSimulation(IMatch match){
     ITeam home = match.getHomeTeam();
    ITeam away = match.getAwayTeam();
 
    int homeGoals = 0;
    int awayGoals = 0;
     

    System.out.println("- Início do jogo entre " + home.getClub().getName() + " e " + away.getClub().getName());

    
    
    for (int minute = 1; minute <= 90; minute++) {
        double eventProbability = random.nextDouble();
        if (eventProbability > 0.06) continue; //Probabilidade de 6% de uma oportunidade

        boolean isCounterAttack = eventProbability < 0.03;
        boolean isCorner = eventProbability < 0.04;
        boolean isPassGood = eventProbability < 0.05;

        ITeam attackingClub = random.nextBoolean() ? home : away;
        ITeam defendingClub = (attackingClub == home) ? away : home;

        IPlayer[] attackers = attackingClub.getPlayers();
        
        IPlayer[] defenders = defendingClub.getPlayers();

        if (attackers.length == 0 || defenders.length == 0) continue;

        // 🔍 Selecionar o guarda-redes da equipa defensora
        Player goalkeeper = null;
        for (IPlayer p : defenders) {
            if (p instanceof Player && ((Player)p).getPosition().getDescription().equalsIgnoreCase("Goalkeeper")) {
                goalkeeper = (Player) p;
                break;
            }
        }

       

      
        Player attacker = null;
        Player playmaker = null;
        
        
        while (attacker == null || playmaker == null) {
            Player temp = (Player) attackers[random.nextInt(attackers.length)];
            if (temp.getPosition().getDescription().equalsIgnoreCase("Forward")) {
                attacker = temp;
            }else if (temp.getPosition().getDescription().equalsIgnoreCase("Midfielder")) {
                playmaker = temp;
            }
        }

        
        int shooting = attacker.getShooting();
        int defense = goalkeeper.getDefense();

        if (isCounterAttack) {
            System.out.println(new CounterAttackEvent(attackingClub, playmaker, minute).getDescription());
        } else if (isCorner) {
            System.out.println(new CornerEvent(attackingClub, playmaker, minute).getDescription());
        } else if(isPassGood){
           System.out.println(new PassEvent(attackingClub, playmaker, minute).getDescription());
        }

        

        if (shooting > defense) {
            GoalEvent goal = new GoalEvent(attackingClub, attacker,goalkeeper, minute);
            match.addEvent(goal);
            System.out.println(goal.getDescription());
            if (attackingClub == home) homeGoals++; else awayGoals++;
        } else {
            MissedShotEvent missedGoal = new MissedShotEvent(attackingClub,attacker,goalkeeper,minute);
            System.out.println(missedGoal.getDescription());
                    
        }
    }

    match.setPlayed();
    System.out.println("\nResultado final:");
    System.out.println(home.getClub().getName() + " " + homeGoals + " x " + awayGoals + " " + away.getClub().getName());
    
   
 }
 
 
 public void saveStatistics(IMatch match,IStanding homeStanding,IStanding awayStanding,int winPoints,int lossPoints,int drawPoints){
    
 ITeam home = match.getHomeTeam();
    ITeam away = match.getAwayTeam();

    int homeGoalsFromEvents = 0;
    int awayGoalsFromEvents = 0;

    for (var event : match.getEvents()) {
        if (event instanceof GoalEvent goal) {
            if (goal.getScoringTeam().equals(home)) {
                homeGoalsFromEvents++;
            } else if (goal.getScoringTeam().equals(away)) {
                awayGoalsFromEvents++;
            }
        }
    }

    
    
    // Atualiza os standings com os golos
    if (homeStanding instanceof Standing sh) {
        sh.updateStats(homeGoalsFromEvents, awayGoalsFromEvents);
    }

    if (awayStanding instanceof Standing sa) {
        sa.updateStats(awayGoalsFromEvents, homeGoalsFromEvents);
    }

    
    
 }
     
 }

