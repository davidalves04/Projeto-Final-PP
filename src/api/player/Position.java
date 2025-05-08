/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.player;

import enums.PlayerPosition;
import enums.PreferredFoot;

/**
 *
 * @author david
 */
public class Position extends PlayerStats {
    private PlayerPosition position;

    public Position( int id, String name, 
            PreferredFoot preferredFoot, int shooting, int passing, int speed, int stamina,int defense,int defenseGk) {
        super(id, name, preferredFoot, shooting, passing, speed, stamina,defense,defenseGk);
        
    }

  @Override
    public PlayerPosition getPosition() {
        return this.position;
    }
    
    
}
