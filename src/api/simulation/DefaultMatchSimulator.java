package api.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import java.util.Random;
import api.event.*;

public class DefaultMatchSimulator implements MatchSimulatorStrategy {

    private final Random random = new Random();

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
