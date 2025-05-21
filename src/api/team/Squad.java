/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.team;

import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author david
 */
public class Squad  implements ITeam{
    private IClub club;
    private IFormation formation;
    private int positionCount;
    private int teamStrengh;
    
    
    private String fileClub;
    private String filePlayers;

    public Squad( IClub club,IFormation formation, String code, String country, String logo, int foundedYear, String name, int playerCount, String stadiumName) {
       
        this.formation = formation;
       
    }

    
        @Override
    public IClub getClub() {
      
          return this.club; //Ira retornar apenas as informaçoes que estao na classe Team (como nome,estadio,etc)
    }

    
      @Override
    public IPlayer[] getPlayers() {
        
          return this.club.getPlayers(); //Ira retornar os players do array da classe Team
    }
    
    

    @Override
    public void addPlayer(IPlayer player) {
         if (player == null){
             throw new IllegalArgumentException("Jogador null");
         }
        if (this.club.isPlayer(player)){
            throw new IllegalStateException("Já está na equipa");
        }
        
        //Falta implementar caso o jogador teja noutra equipa
        
        if (formation == null){
            throw new IllegalStateException("Formação não definida");
        }
        
        this.club.addPlayer(player); // usa método original da Team
    }


     @Override
    public void setFormation(IFormation formation) {
        
    if (formation == null) {
        throw new IllegalArgumentException("Formation cannot be null");
    }

    this.formation = formation;
    }
    
    @Override
    public IFormation getFormation() {
         if (formation == null) {
        throw new IllegalArgumentException("Formação ainda não defenida");
    }
        
        
        return this.formation;
    }

 

  

   
    
    @Override
    public int getPositionCount(IPlayerPosition position) {
        
        int count = this.club.getPlayerCount();
        int total = 0;
        IPlayer[] players = this.club.getPlayers(); 
        
        for(int i = 0; i < count;i++){
            if (players[i].getPosition().equals(position)) { 
            total++;
        }
        }
        
        
        return total;
    }

    @Override
    public int getTeamStrength() {
       int count = this.club.getPlayerCount();
       int totalStrength = 0;
       IPlayer[] players = this.club.getPlayers(); 
       
       for(int i = 0;i < count;i++){
                IPlayer player = players[i];
                if (player == null) continue; // ignora se for null (por segurança)
        
        // Soma os atributos do jogador
        int totalAtributes = player.getShooting() + player.getStamina() + player.getSpeed() + player.getPassing();
        
        
        int playerStrength = totalAtributes / 4;
        
       
        
        
        totalStrength += playerStrength; // adiciona à força total da equipa
        
       }
       
       
       return totalStrength / count; //Media de todos os atributos dos jogadores

    }

    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
       //Ainda por implementar
       return false;
    }

    
    
    
    public String getFileClub() {
        return fileClub;
    }

    public void setFileClub(String fileClub) {
        this.fileClub = fileClub;
    }

    public String getFilePlayers() {
        return filePlayers;
    }

    public void setFilePlayers(String filePlayers) {
        this.filePlayers = filePlayers;
    }

    
    
    
     @Override
    public void exportToJson() throws IOException {
        
    int count = this.club.getPlayerCount();
    IPlayer[] players = getPlayers();

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePlayers, true))) {
        for (int i = 0; i < count; i++) {
            
            IPlayer player = players[i];
            if (player == null) continue; // ignora se for null (por segurança)
             // Formação
        writer.write("  \"formation\": {\n");
        writer.write("    \"description\": \"" + formation.getDisplayName() + "\"\n");
        writer.write("    \"tatical advantage\": \"" + formation.getTacticalAdvantage(formation)+ "\"\n");
        writer.write("  },\n");

        // Força da equipa
        writer.write("  \"teamStrength\": " + getTeamStrength() + ",\n");
            writer.write("}\n");
        }
    } catch (IOException e) {
        System.out.println("Erro ao exportar jogadores: " + e.getMessage());
    }
}
    
    
    
    
    
}

