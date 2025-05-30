/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.simulation;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

/**
 * Classe responsável por simular jogos de uma liga de futebol.
 * 
 * Esta classe encapsula uma {@link ILeague} e delega a simulação de rondas ou da temporada
 * completa à {@link ISeason} atual da liga. Permite também configurar a estratégia de simulação
 * dos jogos.
 * 
 * Por padrão, utiliza a primeira temporada (índice 0) da liga.
 */
public class LeagueSimulator {

    private final ILeague league;
    private final ISeason season;

    /**
     * Constrói um simulador de liga com base na liga fornecida.
     * Utiliza a primeira temporada disponível (índice 0).
     * 
     * @param league a liga a ser simulada
     * @throws IllegalStateException se a liga não contiver nenhuma temporada
     */
    public LeagueSimulator(ILeague league) {
        this.league = league;
        this.season = league.getSeason(0); // Podes parametrizar depois se quiseres outra season

        if (this.season == null) {
            throw new IllegalStateException("Não existe nenhuma temporada criada na liga!");
        }
    }

    /**
     * Define a estratégia de simulação a ser utilizada para os jogos da temporada.
     * 
     * @param strategy a estratégia de simulação dos jogos
     */
    public void setMatchSimulator(MatchSimulatorStrategy strategy) {
        season.setMatchSimulator(strategy);
    }

    /**
     * Simula uma ronda (jornada) da temporada atual.
     */
    public void simulateRound() {
        season.simulateRound(); // A season trata da simulação corretamente
    }

    /**
     * Simula todos os jogos da temporada até a sua conclusão.
     */
    public void simulateSeason() {
        season.simulateSeason();
    }

    /**
     * Verifica se a temporada já foi completamente simulada.
     * 
     * @return true se a temporada estiver completa, false caso contrário
     */
    public boolean isComplete() {
        return season.isSeasonComplete();
    }
}
