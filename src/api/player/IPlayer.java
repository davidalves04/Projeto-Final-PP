/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.player;

import enums.PlayerPosition;
import enums.PreferredFoot;

/**
 *
 * @author Utilizador
 */
public interface IPlayer {
    
   int getShooting();
    void setShooting(int shooting);
    
    int getPassing();
    void setPassing(int passing);
    
    int getSpeed();
    void setSpeed(int speed);
    
    int getStamina();
    void setStamina(int stamina);
    
    int getDefense();
    void setDefense(int defense);
    
    int getDefenseGk();
    void setDefenseGk(int defenseGk);
   
}
