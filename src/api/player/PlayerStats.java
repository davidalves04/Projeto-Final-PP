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
public abstract class PlayerStats extends Player implements IPlayer{
    private int shooting;
    private int passing;
    private int speed;
    private int stamina;
    private int defense;
    private int defenseGk;
    
    public PlayerStats(int id, String name, PreferredFoot preferredFoot,
                      int shooting, int passing, int speed, int stamina,int defense,int defenseGk) {
       
        super(id,name,preferredFoot);
        this.shooting = shooting;
        this.passing = passing;
        this.speed = speed;
        this.stamina = stamina;
        this.defense = defense;
        this.defenseGk = defenseGk;
        
    }
    
     public abstract PlayerPosition getPosition();                 
                      
     @Override
    public int getShooting() {
        return shooting;
    }

    @Override
    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    @Override
    public int getPassing() {
        return passing;
    }

    @Override
    public void setPassing(int passing) {
        this.passing = passing;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public int getDefenseGk() {
        return defenseGk;
    }

    @Override
    public void setDefenseGk(int defenseGk) {
        this.defenseGk = defenseGk;
    
}
    
}
