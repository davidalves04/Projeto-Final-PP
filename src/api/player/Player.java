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
public class Player  {

    
    private int id;
    private String name;
    
    private PreferredFoot preferredFoot;
    
    public Player(int id, String name, PreferredFoot preferredFoot){
              
    this.id = id;
    this.name = name;
    
    this.preferredFoot = preferredFoot;
}
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PreferredFoot getPreferredFoot() {
        return preferredFoot;
    }
    
    
    
}
