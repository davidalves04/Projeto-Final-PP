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
 * Representa uma equipa de futebol (squad) composta por jogadores,
 * associada a um clube e a uma formação tática.
 * Implementa a interface {@link ITeam}.
 * <p>
 * Permite adicionar e remover jogadores, consultar jogadores por posição,
 * obter força média da equipa e exportar os dados para um ficheiro JSON.
 * </p>
 * 
 * @author david
 */
public class Squad implements ITeam {

    /**
     * Número máximo de jogadores na equipa (11 titulares + banco).
     */
    private static final int MAX_TEAM = 23;

    /**
     * Clube a que a equipa pertence.
     */
    private IClub club;

    /**
     * Formação tática atual da equipa.
     */
    private IFormation formation;

    /**
     * Contador de posições na equipa (não utilizado diretamente no código atual).
     */
    private int positionCount;

    /**
     * Força total da equipa (não utilizado diretamente no código atual).
     */
    private int teamStrengh;

    /**
     * Array que contém os jogadores da equipa.
     */
    private IPlayer[] players;

    /**
     * Contador do número de jogadores na equipa.
     */
    private int playerCount;

    /**
     * Ficheiro onde será feita a exportação dos dados da equipa.
     */
    private String file;

    /**
     * Construtor que cria uma equipa associada a um clube e a uma formação tática.
     * 
     * @param club clube a que a equipa pertence
     * @param formation formação tática da equipa
     */
    public Squad(IClub club, IFormation formation) {
        this.club = club;
        this.formation = formation;
        this.players = new IPlayer[MAX_TEAM];
    }

    /**
     * Obtém o clube associado a esta equipa.
     * 
     * @return o clube da equipa
     */
    @Override
    public IClub getClub() {
        return this.club;
    }

    /**
     * Obtém uma cópia do array de jogadores atualmente na equipa.
     * 
     * @return array contendo os jogadores da equipa
     */
    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] copy = new IPlayer[playerCount];
        for (int i = 0; i < playerCount; i++) {
            copy[i] = this.players[i];
        }
        return copy; // Retorna uma cópia dos jogadores que estão na equipa
    }

    /**
     * Procura a posição de um jogador no array da equipa.
     * 
     * @param player jogador a procurar
     * @return índice do jogador no array ou -1 se não encontrado
     */
    private int findPlayer(IPlayer player) {
        int i = 0, pos = -1;
        while (i < playerCount && pos == -1) {
            if (players[i].getNumber() == player.getNumber()) {  // procura pelo número do jogador
                pos = i;
            }
            i++;
        }
        return pos; // Devolve a posição caso seja encontrado, senão retorna -1
    }

    /**
     * Adiciona um jogador à equipa, verificando várias condições de validade.
     * 
     * @param player jogador a adicionar
     * @throws IllegalArgumentException se o jogador for null
     * @throws IllegalStateException se o jogador não pertencer ao clube,
     * se a equipa estiver cheia, se o jogador já estiver na equipa,
     * ou se a formação não estiver definida
     */
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
        players[playerCount++] = player; // adiciona o jogador à equipa
    }

    /**
     * Remove um jogador da equipa, se existir.
     * 
     * @param player jogador a remover
     */
    public void removePlayer(IPlayer player) {
        int pos = findPlayer(player);
        if (pos == -1) {
            return; // Jogador não encontrado, não remove nada
        }
        // Remove o jogador deslocando os restantes para a esquerda
        for (int i = pos; i < playerCount - 1; i++) {
            players[i] = players[i + 1];
        }
        players[--playerCount] = null; // diminui o contador e limpa a última posição
    }

    /**
     * Define a formação tática da equipa.
     * 
     * @param formation formação a definir
     * @throws IllegalArgumentException se a formação for null
     */
    @Override
    public void setFormation(IFormation formation) {
        if (formation == null) {
            throw new IllegalArgumentException("Formation cannot be null");
        }
        this.formation = formation;
    }

    /**
     * Obtém a formação tática atual da equipa.
     * 
     * @return formação da equipa
     * @throws IllegalArgumentException se a formação ainda não estiver definida
     */
    @Override
    public IFormation getFormation() {
        if (formation == null) {
            throw new IllegalArgumentException("Formação ainda não defenida");
        }
        return this.formation;
    }

    /**
     * Obtém o número de jogadores do clube que ocupam uma determinada posição.
     * 
     * @param position posição a contar
     * @return número de jogadores nessa posição
     */
    @Override
    public int getPositionCount(IPlayerPosition position) {
        int count = this.club.getPlayerCount();
        int total = 0;
        IPlayer[] players = this.club.getPlayers();

        for (int i = 0; i < count; i++) {
            if (players[i].getPosition().equals(position)) {
                total++;
            }
        }
        return total;
    }

    /**
     * Obtém uma lista de jogadores da equipa que ocupam uma determinada posição.
     * 
     * @param position posição dos jogadores a obter
     * @return array com jogadores na posição indicada (pode conter posições null)
     */
    public IPlayer[] getPositionPlayersList(IPlayerPosition position) {
        IPlayer[] totalPlayersPos = new IPlayer[MAX_TEAM]; // array para armazenar os jogadores encontrados
        int count = 0; // contador de quantos jogadores foram adicionados

        for (int i = 0; i < playerCount; i++) {
            if (players[i].getPosition().equals(position)) { // compara a posição corretamente
                totalPlayersPos[count++] = players[i]; // adiciona jogador ao array e incrementa o contador
            }
        }
        return totalPlayersPos;
    }

    /**
     * Calcula a força média da equipa com base nos atributos dos jogadores.
     * 
     * @return força média da equipa
     */
    @Override
    public int getTeamStrength() {
        int count = this.club.getPlayerCount();
        int totalStrength = 0;
        IPlayer[] players = this.club.getPlayers();

        for (int i = 0; i < count; i++) {
            IPlayer player = players[i];
            if (player == null) continue; // ignora se for null (por segurança)

            // Soma os atributos do jogador
            int totalAtributes = player.getShooting() + player.getStamina() + player.getSpeed() + player.getPassing();
            int playerStrength = totalAtributes / 4;

            totalStrength += playerStrength; // adiciona à força total da equipa
        }
        return totalStrength / count; // Média de todos os atributos dos jogadores
    }

    /**
     * Verifica se uma posição de jogador é válida para a formação.
     * 
     * @param position posição do jogador
     * @return true se a posição for válida (GK, DEF, MID, FWD), false caso contrário
     */
    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        String pos = position.getDescription();

        if (pos.equals("GK") || pos.equals("DEF") || pos.equals("MID") || pos.equals("FWD")) {
            return true;
        }
        return false;
    }

    /**
     * Obtém o caminho do ficheiro para exportação dos dados.
     * 
     * @return caminho do ficheiro
     */
    public String getFile() {
        return file;
    }

    /**
     * Define o caminho do ficheiro para exportação dos dados.
     * 
     * @param fileClub caminho do ficheiro
     */
    public void setFile(String fileClub) {
        this.file = fileClub;
    }

    /**
     * Exporta os dados da equipa para um ficheiro JSON.
     * O formato inclui nome do clube, formação, lista de jogadores e força da equipa.
     * 
     * @throws IOException se ocorrer algum erro na escrita do ficheiro
     */
    @Override
    public void exportToJson() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("{\n");
            writer.write("  \"clubName\": \"" + club.getName() + "\",\n");
            writer.write("  \"formation\": \"" + formation.getDisplayName() + "\",\n");
            

    writer.write("  \"players\": [\n");

    for (int i = 0; i < playerCount; i++) {
        writer.write("    {\n");
        writer.write("      \"name\": \"" + players[i].getName() + "\",\n");
        writer.write("      \"birthDate\": \"" + players[i].getBirthDate() + "\",\n");
        writer.write("      \"nationality\": \"" + players[i].getNationality() + "\",\n");
        writer.write("      \"basePosition\": \"" + players[i].getPosition() + "\",\n");
        writer.write("      \"photo\": \"" + players[i].getPhoto() + "\",\n");
        writer.write("      \"number\": " + players[i].getNumber() + ",\n");
        writer.write("      \"age\": " + players[i].getAge() + ",\n");
        writer.write("      \"height\": " + players[i].getHeight() + ",\n");
        writer.write("      \"weight\": " + players[i].getWeight() + ",\n");
        writer.write("      \"shootingstats\": " + players[i].getShooting() + ",\n");
        writer.write("      \"staminastats\": " + players[i].getStamina() + ",\n");
        writer.write("      \"speedstats\": " + players[i].getSpeed() + ",\n");
        writer.write("      \"passingstats\": " + players[i].getPassing() + ",\n");
        if (players[i] instanceof Player) {
         Player p = (Player) players[i];
         writer.write("      \"defensestats\": " + p.getDefense() + "\n");
        }
        
        writer.write("    }");

        if (i < playerCount - 1) writer.write(",");
        writer.write("\n");
    }

    writer.write("  ],\n");
    writer.write("  \"teamStrength\": " + getTeamStrength() + "\n");
    writer.write("}");
        
    } catch (IOException e) {
        System.out.println("Erro ao exportar jogadores: " + e.getMessage());

    }
}
    
}
