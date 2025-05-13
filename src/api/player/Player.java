package api.player;

import enums.PlayerPosition;
import enums.PreferredFoot;

/**
 *
 * @author david
 */
public class Player implements IPlayer {
    private int id;
    private String name;
    private PreferredFoot preferredFoot;
    private PlayerPosition position;
    private PlayerStats stats;

    // Construtor
    public Player(int id, String name, PlayerPosition position, PreferredFoot preferredFoot, PlayerStats stats) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.preferredFoot = preferredFoot;
        this.stats = stats;
    }

    // Getters
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
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public PlayerStats getStats() {
        return stats;
    }

    // Delegações para IPlayer (se a interface exigir)
    public int getShooting() {
        return stats.getShooting();
    }

    public void setShooting(int s) {
        stats.setShooting(s);
    }

    public int getPassing() {
        return stats.getPassing();
    }

    public void setPassing(int p) {
        stats.setPassing(p);
    }

    public int getSpeed() {
        return stats.getSpeed();
    }

    public void setSpeed(int sp) {
        stats.setSpeed(sp);
    }

    public int getStamina() {
        return stats.getStamina();
    }

    public void setStamina(int st) {
        stats.setStamina(st);
    }

    public int getDefense() {
        return stats.getDefense();
    }

    public void setDefense(int d) {
        stats.setDefense(d);
    }

    public int getDefenseGk() {
        return stats.getDefenseGk();
    }

    public void setDefenseGk(int gk) {
        stats.setDefenseGk(gk);
    }
}
