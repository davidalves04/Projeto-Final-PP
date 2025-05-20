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
public class Squad extends Team implements ITeam{
    private IClub club;
    private IFormation formation;
    private IPlayer []players;
    private int positionCount;
    private int teamStrengh;
    
    private String fileClub;
    private String filePlayers;

    public Squad(IClub club, IFormation formation, IPlayer[] players, int positionCount, int teamStrengh, String code, String country, String logo, int foundedYear, String name, int playerCount, String stadiumName) {
        super(code, country, logo, foundedYear, name, playerCount, stadiumName);
        this.club = club;
        this.formation = formation;
        this.players = super.getPlayers(); 
        this.positionCount = positionCount;
        this.teamStrengh = teamStrengh;
    }

    
        @Override
    public IClub getClub() {
      
          return this.club;
    }

    
       @Override
    public IPlayer[] getPlayers() {
        int count = getPlayerCount();
        
         IPlayer[] copy = new IPlayer[count];
    for (int i = 0; i < count; i++) {
        copy[i] = players[i];
    }
    return copy; //Ira dar return de uma copia dos jogadores criados
    
    }
    
    

    @Override
    public void addPlayer(IPlayer player) {
         if (player == null){
             throw new IllegalArgumentException("Jogador null");
         }
        if (isPlayer(player)){
            throw new IllegalStateException("Já está na equipa");
        }
        
        //Falta implementar caso o jogador teja noutra equipa
        
        if (formation == null){
            throw new IllegalStateException("Formação não definida");
        }
        
        super.addPlayer(player); // usa método original da Team
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
        
        int count = getPlayerCount();
        int total = 0;
        
        
        for(int i = 0; i < count;i++){
            if (players[i].getPosition().equals(position)) { 
            total++;
        }
        }
        
        
        return total;
    }

    @Override
    public int getTeamStrength() {
       int count = getPlayerCount();
       int totalStrength = 0;
       for(int i = 0;i < count;i++){
                IPlayer player = players[i];
        
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

   
     @Override
    public void exportToJson() throws IOException {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileClub, true))) {
        writer.write("{\n");
        writer.write("  \"code\": \"" + getCode() + "\",\n");
        writer.write("  \"country\": \"" + getCountry() + "\",\n");
        writer.write("  \"logo\": \"" + getLogo() + "\",\n");
        writer.write("  \"foundedYear\": " + getFoundedYear() + ",\n");
        writer.write("  \"name\": \"" + getName() + "\",\n");
        writer.write("  \"stadium\": \"" + getStadiumName() + "\"\n");
        writer.write("  \"formation\": \"" + this.formation + "\",\n");
        writer.write("  \"teamStrength\": " + this.teamStrengh + ",\n");
        
        writer.write("}\n");
    } catch (IOException e) {
        System.out.println("Erro ao exportar equipa: " + e.getMessage());
    }

    // Exporta os jogadores para "players.json"
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePlayers, true))) {
        for (int i = 0; i < getPlayerCount(); i++) {
            IPlayer player = getPlayers()[i];
            writer.write("{\n");
            writer.write("  \"name\": \"" + player.getName() + "\",\n");
            writer.write("  \"birthDate\": \"" + player.getBirthDate() + "\",\n");
            writer.write("  \"nationality\": \"" + player.getNationality() + "\",\n");
            writer.write("  \"basePosition\": \"" + player.getPosition() + "\",\n");
            writer.write("  \"photo\": \"" + player.getPhoto() + "\",\n");
            writer.write("  \"number\": " + player.getNumber() + "\n");
            writer.write("}\n");
        }
    } catch (IOException e) {
        System.out.println("Erro ao exportar jogadores: " + e.getMessage());
    }
}
    
    
    
    
    
}
