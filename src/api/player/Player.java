package api.player;

import enums.PlayerPosition;
import enums.PreferredFoot;
import java.time.LocalDate;

/**
 *
 * @author david
 */
public class Player implements IPlayer {
   
    
    private String name;
    private LocalDate birth;
    private int age;
    private String nationality;
    private String photo;
    private int number;
    private int passing;
    private int speed;
    private int shooting;
    private int stamina;
    private IPlayerPosition position;
    private PreferredFoot preferredFoot;
    private int height;
    private int weight;

    public Player(String name, LocalDate birth, int age, String nationality, int number, int passing, int speed, int shooting, int stamina, IPlayerPosition position, PreferredFoot preferredFoot, int height, int weight) {
        this.name = name;
        this.birth = birth;
        this.age = age;
        this.nationality = nationality;
        this.number = number;
        this.passing = passing;
        this.speed = speed;
        this.shooting = shooting;
        this.stamina = stamina;
        this.position = position;
        this.preferredFoot = preferredFoot;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public LocalDate getBirthDate() {
        return this.birth;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public String getNationality() {
        return this.nationality;
    }

    @Override
    public void setPosition(IPlayerPosition position) {
         if (position == null) {
        throw new IllegalArgumentException("Formation cannot be null");
    }
        this.position = position;
    }

    @Override
    public String getPhoto() {
        return this.photo;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public int getShooting() {
        return this.shooting;
    }

    @Override
    public int getPassing() {
       return this.passing;
    }

    @Override
    public int getStamina() {
      return this.stamina;
    }

    @Override
    public int getSpeed() {
      return this.speed;
    }

    @Override
    public IPlayerPosition getPosition() {
      return this.position;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public PreferredFoot getPreferredFoot() {
        return this.preferredFoot;
    }
    
      
}
