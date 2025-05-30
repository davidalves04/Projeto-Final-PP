/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.player;

/**
 * Representa as estatísticas técnicas de um jogador, incluindo atributos como remate,
 * passe, velocidade e resistência.
 * 
 * Esta classe permite a leitura e modificação das estatísticas individuais.
 */
public class PlayerStats {
    private int shooting;
    private int passing;
    private int speed;
    private int stamina;
    private int defense;
    

    /**
     * Construtor que inicializa os atributos técnicos do jogador.
     * 
     * @param shooting Valor do remate (shooting)
     * @param passing Valor do passe (passing)
     * @param speed Valor da velocidade (speed)
     * @param stamina Valor da resistência (stamina)
     * @param defense Valor de defesa(defense) 
     */
    public PlayerStats(int shooting, int passing, int speed, int stamina,int defense) {
        this.shooting = shooting;
        this.passing = passing;
        this.speed = speed;
        this.stamina = stamina;
        this.defense = defense;
        
    }

    /**
     * Obtém o valor do remate (shooting).
     * 
     * @return valor do remate
     */
    public int getShooting() {
        return shooting;
    }

    /**
     * Obtém o valor do passe (passing).
     * 
     * @return valor do passe
     */
    public int getPassing() {
        return passing;
    }

    /**
     * Obtém o valor da velocidade (speed).
     * 
     * @return valor da velocidade
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Obtém o valor da resistência (stamina).
     * 
     * @return valor da resistência
     */
    public int getStamina() {
        return stamina;
    }

    
     public int getDefense(){
        return defense;
    }
    
   

    
    
    /**
     * Define o valor do remate (shooting).
     * 
     * @param shooting novo valor de remate
     */
    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    /**
     * Define o valor do passe (passing).
     * 
     * @param passing novo valor de passe
     */
    public void setPassing(int passing) {
        this.passing = passing;
    }

    /**
     * Define o valor da velocidade (speed).
     * 
     * @param speed novo valor de velocidade
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Define o valor da resistência (stamina).
     * 
     * @param stamina novo valor de resistência
     */
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    
     public void setDefense(int defense){
       this.defense = defense; 
    }
    
    
}
