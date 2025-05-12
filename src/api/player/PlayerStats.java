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
public class PlayerStats extends Player{
    private int shooting;
    private int passing;
    private int speed;
    private int stamina;
    private int defense;
    private int defenseGk;
    
    public PlayerStats(int id, String name, PreferredFoot preferredFoot,PlayerPosition position,
                      int shooting, int passing, int speed, int stamina,int defense,int defenseGk) {
       
        super(id,name,preferredFoot,position);
        this.shooting = shooting;
        this.passing = passing;
        this.speed = speed;
        this.stamina = stamina;
        this.defense = defense;
        this.defenseGk = defenseGk;
        
    }
    
                      
    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDefenseGk() {
        return defenseGk;
    }

    public void setDefenseGk(int defenseGk) {
        this.defenseGk = defenseGk;
    
}
    
}
