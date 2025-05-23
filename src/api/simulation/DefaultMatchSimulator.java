package api.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import java.util.Random;
import api.event.GoalEvent;
import api.event.MissedShotEvent;

public class DefaultMatchSimulator implements MatchSimulatorStrategy {

    private final Random random = new Random();

    @Override
    public void simulate(IMatch match) {
        IClub home = match.getHomeClub();
        IClub away = match.getAwayClub();

        for (int minute = 1; minute <= 90; minute += 5) {
            IClub attackingClub = random.nextBoolean() ? home : away;

            IPlayer[] players = attackingClub.getPlayers();
            if (players.length == 0) continue;

            IPlayer attacker = players[random.nextInt(players.length)];

            int chance = random.nextInt(100);
            if (chance < 20) {
                // Golo
                match.addEvent(new GoalEvent(attackingClub, attacker, minute));
            } else if (chance < 50) {
                // Remate falhado
                match.addEvent(new MissedShotEvent(attackingClub, attacker, minute));
            }
            // caso contrário, nada acontece
        }

        match.setPlayed();
    }
}
