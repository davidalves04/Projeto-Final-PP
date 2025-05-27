/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.team;

import api.player.Player;
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
    private static final int MAX_TEAM = 18; //11 inicial e 7 no banco
    
    private IClub club;
    private IFormation formation;
    private int positionCount;
    private int teamStrengh;
    
    private IPlayer []players;
    private int playerCount;
    
    private String file;
    
    public Squad( IClub club,IFormation formation) {
       
        this.club = club;
        this.formation = formation;
        this.players = new IPlayer[MAX_TEAM];
       
    }

    
        @Override
    public IClub getClub() {
      
          return this.club; 
    }

    
      @Override
    public IPlayer[] getPlayers() {
        
         IPlayer[] copy = new IPlayer[playerCount];
    for (int i = 0; i < playerCount; i++) {
        copy[i] = this.players[i];
    }
    return copy; //Ira returna uma copia dos jogadores que estao na equipa
        
   
    }
    
    

    @Override
    public void addPlayer(IPlayer player) {
             if (player == null) {
         throw new IllegalArgumentException("Jogador null");
       }
             if (!club.isPlayer(player)) {
        throw new IllegalStateException("Jogador não pertence ao clube");
       }
             if (playerCount >= players.length) {
        throw new IllegalStateException("A equipa está cheia");
       }
            for (int i = 0; i < playerCount; i++) {
           if (players[i].equals(player)) {
            throw new IllegalStateException("Jogador já está na equipa");
          }
        }
            if (formation == null) {
        throw new IllegalStateException("Formação não definida");
        }

    players[playerCount++] = player; // adiciona à equipa
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

    
    public IPlayer[] getPositionPlayersList(IPlayerPosition position){
        
    
    
    IPlayer[] totalPlayersPos = new IPlayer[MAX_TEAM]; // array para armazenar os jogadores encontrados
    int count = 0; // contador de quantos jogadores foram adicionados

    for (int i = 0; i < playerCount; i++) {
        if (players[i].getPosition().equals(position)) { // compara a posição corretamente
            totalPlayersPos[count++] = players[i]; // adiciona jogador ao array e incrementa o contador
        }
    }

    
    
    return totalPlayersPos;
     
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
     String pos = position.getDescription();

    if (pos.equals("GK") || pos.equals("DEF") || pos.equals("MID") || pos.equals("FWD")) {
        return true;
    }

    return false;
       
    }

    
    
    
    public String getFile() {
        return file;
    }

    public void setFile(String fileClub) {
        this.file = fileClub;
    }


    
    
     @Override
    public void exportToJson() throws IOException {
        
    
    

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
        
            
            
            
             // Formação
        writer.write("{\n");
        writer.write("    \"clubName\": \"" + club.getName() + "\",\n");
        writer.write("    \"formation\": \"" + formation.getDisplayName() + "\",\n");
       
        for(int i = 0;i < this.playerCount;i++){
            writer.write("{\n");
        writer.write("  \"name\": \"" + players[i].getName() + "\",\n");
        writer.write("  \"birthDate\": \"" + players[i].getBirthDate() + "\",\n");
        writer.write("  \"nationality\": \"" + players[i].getNationality() + "\",\n");
        writer.write("  \"basePosition\": \"" + players[i].getPosition() + "\",\n");
        writer.write("  \"photo\": \"" + players[i].getPhoto() + "\",\n");
        writer.write("  \"number\": " + players[i].getNumber() + ",\n");
        writer.write("  \"age\": " + players[i].getAge() + ",\n");
        writer.write("  \"height\": " + players[i].getHeight()  + ",\n");
        writer.write("  \"weight\": " + players[i].getWeight() + ",\n");
      
        writer.write("  \"shootingstats\": " + players[i].getShooting() + ",\n");
        writer.write("  \"staminastats\": " + players[i].getStamina() + ",\n");
        writer.write("  \"speedstats\": " + players[i].getSpeed() + ",\n");
        writer.write("  \"passingstats\": " + players[i].getPassing() + "\n");
                   
        writer.write("}\n");
        }
        

        // Força da equipa
        writer.write("  \"teamStrength\": " + getTeamStrength() + "\n");
            writer.write("}\n");
        
    } catch (IOException e) {
        System.out.println("Erro ao exportar jogadores: " + e.getMessage());
    }
}
    
    
    
    
    
}

