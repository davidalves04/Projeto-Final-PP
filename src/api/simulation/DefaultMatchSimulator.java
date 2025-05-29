package api.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import java.util.Random;
import api.event.*;

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
        IClub home = match.getHomeClub();
        IClub away = match.getAwayClub();

        for (int minute = 1; minute <= 90; minute++) {
            if (random.nextDouble() > 0.2) continue; // Apenas 20% de chance de haver evento

            IClub attackingClub = random.nextBoolean() ? home : away;
            IClub defendingClub = (attackingClub == home) ? away : home;

            IPlayer[] players = attackingClub.getPlayers();
            if (players.length == 0) continue;

            IPlayer player = players[random.nextInt(players.length)];
            int shooting = player.getShooting();
            int passing = player.getPassing();
            int stamina = player.getStamina();
            int speed = player.getSpeed();

            double eventRoll = random.nextDouble();

            if (eventRoll < 0.1) {
                // Falta
                match.addEvent(new FoulEvent(player, minute));
            } else if (eventRoll < 0.3) {
                // Passe
                boolean success = random.nextInt(100) < passing;
                match.addEvent(new PassEvent(player, minute, success));
            } else if (eventRoll < 0.6) {
                // Remate
                int shootChance = random.nextInt(100);
                if (shootChance < shooting) {
                    // Golo
                    match.addEvent(new GoalEvent(attackingClub, player, minute));
                } else if (shootChance < shooting + 20) {
                    // Remate enquadrado
                    match.addEvent(new ShotEvent(player, minute));
                } else {
                    // Remate falhado
                    match.addEvent(new MissedShotEvent(attackingClub, player, minute));
                }
            }
        }

        match.setPlayed();
    }
}
