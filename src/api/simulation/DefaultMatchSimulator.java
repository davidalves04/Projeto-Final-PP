package api.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import java.util.Random;
import api.event.GoalEvent;
import api.event.MissedShotEvent;

/**
 * Implementação padrão da estratégia de simulação de partidas.
 * 
 * Esta classe simula um jogo entre duas equipas, criando eventos como golos e remates falhados
 * de forma aleatória, com base nos jogadores disponíveis nas equipas.
 * 
 * Cada 5 minutos do jogo é simulada uma jogada, onde aleatoriamente uma das equipas ataca,
 * podendo resultar num golo, remate falhado ou nenhum evento.
 * 
 * Após os 90 minutos simulados, o jogo é marcado como concluído.
 * 
 * @author david
 */
public class DefaultMatchSimulator implements MatchSimulatorStrategy {

    private final Random random = new Random();

    /**
     * Simula a partida entre as duas equipas fornecidas no objeto {@link IMatch}.
     * Gera eventos a cada 5 minutos até completar os 90 minutos regulamentares.
     * 
     * @param match o objeto que representa o jogo a ser simulado
     */
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
