/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.team;

import api.player.Player;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;


import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author david
 */
public class Team implements IClub {
    
  private static final int MAX_PLAYERS = 30;
 
    
   private String code;
   private String country;
   private String logo;
   private int foundedYear;
   private String name;
   private int playerCount;
   private String stadiumName;
   private String file;
   
   private static int teamCount;
   
   private IPlayer[] players;

    public Team(String code, String country, String logo, int foundedYear, String name,String stadiumName) {
        this.code = code;
        this.country = country;
        this.logo = logo;
        this.foundedYear = foundedYear;
        this.name = name;
        
        this.stadiumName = stadiumName;
        this.players = new IPlayer[MAX_PLAYERS]; // ← inicializar o array
        teamCount++; // incrementa sempre que uma equipa é criada
        
    }

    @Override
    public void addPlayer(IPlayer player) {
         if (playerCount == players.length) {
          throw new IllegalArgumentException("Jogador é null");
    }
         
      for (int i = 0; i < playerCount; i++) {
        if (players[i].equals(player)) {
            throw new IllegalArgumentException("Jogador já está no clube");
        }
    }

    if (playerCount == players.length) {
        throw new IllegalStateException("Clube cheio");
    }
   
         
    players[playerCount++] = player;
    
    
    }

    
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public int getFoundedYear() {
        return this.foundedYear;
    }

    @Override
    public String getLogo() {
        return this.logo;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    
    @Override
    public int getPlayerCount() {
        return this.playerCount;
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
    public String getStadiumName() {
        return this.stadiumName;
    }

    @Override
    public boolean isPlayer(IPlayer player) {
        for (int i = 0; i < playerCount; i++) { //Ira percorrer ate encontrar player e devolve true se encontrar
        if (players[i].getNumber() == player.getNumber()) {  
            return true;
        }
    }
    return false;  // Caso não encontre o jogador, retorna false
    }

    @Override
    public boolean isValid() {
           // Verifica se o nome, código, país e estádio são não nulos ou vazios
    if (this.name == null) {
        return false; // Nome da equipa inválido
    }
    if (this.code == null) {
        return false; // Código da equipa inválido
    }
    if (this.country == null) {
        return false; // País inválido
    }
    if (this.stadiumName == null) {
        return false; // Nome do estádio inválido
    }
    
    // Verifica se o número de jogadores é dentro do limite
    if (this.playerCount < 0 || this.playerCount > MAX_PLAYERS) {
        return false; // Número de jogadores inválido
    }

    if (this.foundedYear < 1800 || this.foundedYear > 2025) {
        return false; // Ano de fundação inválido
    }
    
    
    return true;
    }

    
    //Procura o player e retorna a sua posição caso ele exista
    private int findPlayer(IPlayer player) {
    int i = 0, pos = -1;
    while (i < playerCount && pos == -1) {
        if (players[i].getNumber() == player.getNumber()) {  //Ira procurar o player no array
            pos = i;
        }
        i++;
    }
    
    
    return pos; //Devolve a posição caso seja encontrado senão ira retornar -1
}
    
   
    @Override
    public void removePlayer(IPlayer player) {
 int pos = findPlayer(player);
    if (pos == -1) {
        return; // Jogador não encontrado,ou seja, nao remove nada
    }

    // Remove o jogador deslocando os restantes para a esquerda
    for (int i = pos; i < playerCount - 1; i++) {
        players[i] = players[i + 1];
    }

    players[--playerCount] = null; // Diminui o contador e limpa a última posição
    }

    
    @Override
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
      
    if (position == null) {
        throw new IllegalArgumentException("Posição não pode ser nula");
    }
    if (this.getPlayerCount() == 0) {
        throw new IllegalStateException("Clube vazio"); 
    }
    IPlayer player = selector.selectPlayer(this, position); //this refere-se exatamente ao objeto Team 
    
    if (player == null) {
        throw new IllegalStateException("Nenhum jogador encontrado");
    }
    
    return player; //ira devolver o player com base na posição (caso seja encontrado)

    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    
    public String squadJsonFile(){
        
        return getCode() + ".json"; //Devolve o ficheiro onde esta a squad
    }
    
    
     public static int getTeamCount() {
        return teamCount;
    }

    
    
    
 @Override
  public void exportToJson() throws IOException {
     File teamFile = new File(file); 
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(teamFile, true))) {
      writer.write("{\n");
      writer.write("  \"code\": \"" + this.code + "\",\n");
      writer.write("  \"country\": \"" + this.country + "\",\n");
      writer.write("  \"logo\": \"" + this.logo + "\",\n");
      writer.write("  \"foundedYear\": " + this.foundedYear + ",\n");
      writer.write("  \"name\": \"" + this.name + "\",\n");
      writer.write("  \"stadiumName\": \"" + this.stadiumName + "\"\n");
      writer.write("}\n");
    } catch (IOException e) {
      System.out.println("Erro ao exportar equipa para JSON: " + e.getMessage());
    }
  }
      
     
    
}
