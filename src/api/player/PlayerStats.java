/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.player;

/**
 *
 * @author david
 */
public class PlayerStats {
    private int shooting;
    private int passing;
    private int speed;
    private int stamina;

    public PlayerStats(int shooting, int passing, int speed, int stamina) {
        this.shooting = shooting;
        this.passing = passing;
        this.speed = speed;
        this.stamina = stamina;
    }

    public int getShooting() {
        return shooting;
    }

    public int getPassing() {
        return passing;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
}
