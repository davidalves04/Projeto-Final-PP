package api.player;


import java.time.LocalDate;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


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
    private IPlayerPosition position;
    private PreferredFoot preferredFoot;
    private float height;
    private float weight;
    
    private String file;

    private PlayerStats stats;

    public Player(String name, LocalDate birth, int age, String nationality, int number,String photo, 
                  PlayerStats stats, IPlayerPosition position, PreferredFoot preferredFoot, 
                  float height, float weight) {
        this.name = name;
        this.birth = birth;
        this.age = age;
        this.nationality = nationality;
        this.photo = photo;
        this.number = number;
        this.stats = stats;
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
            throw new IllegalArgumentException("Position cannot be null");
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
        return stats.getShooting();
    }

    @Override
    public int getPassing() {
        return stats.getPassing();
    }

    @Override
    public int getStamina() {
        return stats.getStamina();
    }

    @Override
    public int getSpeed() {
        return stats.getSpeed();
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
    
    

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    

    @Override
    public void exportToJson() throws IOException {
               try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
                  
        writer.write("{\n");
        writer.write("  \"name\": \"" + this.name + "\",\n");
        writer.write("  \"birthDate\": \"" + this.birth + "\",\n");
        writer.write("  \"nationality\": \"" + this.nationality + "\",\n");
        writer.write("  \"basePosition\": \"" + this.position.getDescription() + "\",\n");
        writer.write("  \"photo\": \"" + this.photo + "\",\n");
        writer.write("  \"number\": " + this.number + ",\n");
        writer.write("  \"age\": " + this.age + ",\n");
        writer.write("  \"shootingstats\": " + getShooting() + ",\n");
        writer.write("  \"staminastats\": " + getStamina() + ",\n");
        writer.write("  \"speedstats\": " + getSpeed() + ",\n");
        writer.write("  \"passingstats\": " + getPassing() + "\n");
                   
        writer.write("}\n");
    } catch (IOException e) {
        System.out.println("Erro ao exportar para JSON: " + e.getMessage());
    }
    }

   public void exportPlayersArrayToJson(Player[] players, File file) {
    try {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("[\n"); //Escreve o [ inicial
        }

        
        for (int i = 0; i < players.length; i++) {
            players[i].exportToJson();  

            
            if (i < players.length - 1) { //Assim o ultimo nao tem virgula
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    writer.write(",\n");
                }
            }
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("\n]"); //Escreve o ] final
        }
    } catch (IOException e) {
        System.out.println("Erro ao exportar lista para JSON: " + e.getMessage());
    }
}
   
}

