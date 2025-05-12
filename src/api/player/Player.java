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
    private PlayerPosition position;
    
    public Player(int id, String name, PreferredFoot preferredFoot,PlayerPosition position){
              
    this.id = id;
    this.name = name;
    this.position = position;
    
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
    
    
     public PlayerPosition getPosition() {
        return this.position;
    }
    
      public void setPosition(PlayerPosition position) {
        this.position = position;
    }
    
}
