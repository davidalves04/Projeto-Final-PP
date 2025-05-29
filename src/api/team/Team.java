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
 * Implementação da interface IClub que representa um clube de futebol.
 * Esta classe gere a informação do clube, incluindo detalhes como código,
 * país, logo, ano de fundação, nome, estádio e os jogadores associados.
 * 
 * Permite adicionar, remover e selecionar jogadores, bem como exportar os dados do clube para JSON.
 * Mantém também uma contagem estática do número de clubes criados.
 * 
 * @author david
 */
public class Team implements IClub {
    
  /**
   * Número máximo de jogadores permitidos no clube.
   */
  private static final int MAX_PLAYERS = 30;
 
  /**
   * Código único do clube.
   */
   private String code;

  /**
   * País onde o clube está sediado.
   */
   private String country;

  /**
   * Caminho ou referência para o logo do clube.
   */
   private String logo;

  /**
   * Ano em que o clube foi fundado.
   */
   private int foundedYear;

  /**
   * Nome do clube.
   */
   private String name;

  /**
   * Contador atual do número de jogadores no clube.
   */
   private int playerCount;

  /**
   * Nome do estádio do clube.
   */
   private String stadiumName;

  /**
   * Caminho do ficheiro associado ao clube (por exemplo, para exportação).
   */
   private String file;
   
  /**
   * Contador estático do número total de clubes criados.
   */
   private static int teamCount;
   
  /**
   * Array que armazena os jogadores do clube.
   */
   private IPlayer[] players;

   /**
    * Construtor que inicializa o clube com os seus dados principais e
    * cria o array para armazenar jogadores.
    * Incrementa o contador estático de clubes.
    * 
    * @param code Código único do clube.
    * @param country País do clube.
    * @param logo Caminho do logo do clube.
    * @param foundedYear Ano de fundação do clube.
    * @param name Nome do clube.
    * @param stadiumName Nome do estádio do clube.
    */
    public Team(String code, String country, String logo, int foundedYear, String name,String stadiumName) {
        this.code = code;
        this.country = country;
        this.logo = logo;
        this.foundedYear = foundedYear;
        this.name = name;
        this.stadiumName = stadiumName;
        this.players = new IPlayer[MAX_PLAYERS]; // Inicializa o array de jogadores
        teamCount++; // Incrementa o contador de clubes
    }

    /**
     * Adiciona um jogador ao clube se houver espaço e se ele ainda não estiver no clube.
     * 
     * @param player Jogador a adicionar.
     * @throws IllegalArgumentException Se o jogador for null ou já existir no clube.
     * @throws IllegalStateException Se o clube já estiver cheio.
     */
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

    /**
     * Obtém o código do clube.
     * @return Código do clube.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Obtém o país do clube.
     * @return País do clube.
     */
    @Override
    public String getCountry() {
        return this.country;
    }

    /**
     * Obtém o ano de fundação do clube.
     * @return Ano de fundação.
     */
    @Override
    public int getFoundedYear() {
        return this.foundedYear;
    }

    /**
     * Obtém o caminho ou referência para o logo do clube.
     * @return Logo do clube.
     */
    @Override
    public String getLogo() {
        return this.logo;
    }

    /**
     * Obtém o nome do clube.
     * @return Nome do clube.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Define o nome do clube.
     * @param name Novo nome do clube.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o número atual de jogadores no clube.
     * @return Número de jogadores.
     */
    @Override
    public int getPlayerCount() {
        return this.playerCount;
    }

    /**
     * Devolve uma cópia do array com os jogadores atualmente no clube.
     * @return Array de jogadores do clube.
     */
    @Override
    public IPlayer[] getPlayers() {
      IPlayer[] copy = new IPlayer[playerCount];
      for (int i = 0; i < playerCount; i++) {
          copy[i] = this.players[i];
      }
      return copy; // Retorna uma cópia para evitar exposição direta do array interno
    }

    /**
     * Obtém o nome do estádio do clube.
     * @return Nome do estádio.
     */
    @Override
    public String getStadiumName() {
        return this.stadiumName;
    }

    /**
     * Verifica se um dado jogador pertence ao clube.
     * 
     * @param player Jogador a verificar.
     * @return true se o jogador pertencer ao clube, false caso contrário.
     */
    @Override
    public boolean isPlayer(IPlayer player) {
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getNumber() == player.getNumber()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se os dados do clube são válidos, incluindo nome, código,
     * país, estádio, número de jogadores e ano de fundação.
     * 
     * @return true se o clube for válido, false caso contrário.
     */
    @Override
    public boolean isValid() {
        if (this.name == null) {
            return false; // Nome inválido
        }
        if (this.code == null) {
            return false; // Código inválido
        }
        if (this.country == null) {
            return false; // País inválido
        }
        if (this.stadiumName == null) {
            return false; // Estádio inválido
        }
        if (this.playerCount < 0 || this.playerCount > MAX_PLAYERS) {
            return false; // Número de jogadores inválido
        }
        if (this.foundedYear < 1800 || this.foundedYear > 2025) {
            return false; // Ano de fundação inválido
        }
        return true;
    }

    /**
     * Procura a posição de um jogador no array interno.
     * 
     * @param player Jogador a procurar.
     * @return Índice do jogador no array ou -1 se não encontrado.
     */
    private int findPlayer(IPlayer player) {
        int i = 0, pos = -1;
        while (i < playerCount && pos == -1) {
            if (players[i].getNumber() == player.getNumber()) {
                pos = i;
            }
            i++;
        }
        return pos;
    }
    
    /**
     * Remove um jogador do clube, se existir.
     * Os jogadores seguintes no array são deslocados para preencher o espaço.
     * 
     * @param player Jogador a remover.
     */
    @Override
    public void removePlayer(IPlayer player) {
        int pos = findPlayer(player);
        if (pos == -1) {
            return; // Jogador não encontrado
        }
        for (int i = pos; i < playerCount - 1; i++) {
            players[i] = players[i + 1];
        }
        players[--playerCount] = null; // Remove referência do último elemento
    }

    /**
     * Seleciona um jogador do clube com base num critério dado por um selector e numa posição específica.
     * 
     * @param selector Critério para selecionar o jogador.
     * @param position Posição do jogador a selecionar.
     * @return Jogador selecionado.
     * @throws IllegalArgumentException se a posição for nula.
     * @throws IllegalStateException se o clube estiver vazio ou nenhum jogador for encontrado.
     */
    @Override
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("Posição não pode ser nula");
        }
        if (this.getPlayerCount() == 0) {
            throw new IllegalStateException("Clube vazio");
        }
        IPlayer player = selector.selectPlayer(this, position);
        if (player == null) {
            throw new IllegalStateException("Nenhum jogador encontrado");
        }
        return player;
    }

    /**
     * Obtém o caminho do ficheiro associado ao clube.
     * @return Caminho do ficheiro.
     */
    public String getFile() {
        return file;
    }

    /**
     * Define o caminho do ficheiro associado ao clube.
     * @param file Caminho do ficheiro.
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Obtém o nome do ficheiro JSON padrão para guardar dados dos jogadores do clube.
     * @return Nome do ficheiro JSON.
     */
    public String playerJsonFile(){
        return getCode() + ".json";
    }
    
    /**
     * Obtém o número total de clubes criados.
     * @return Contador estático de clubes.
     */
    public static int getTeamCount() {
        return teamCount;
    }

    /**
     * Exporta os dados do clube para um ficheiro JSON.
     * Se o ficheiro já existir, os dados são acrescentados no final.
     * 
     * @throws IOException Caso ocorra erro na escrita do ficheiro.
     */
    @Override
    public void exportToJson() throws IOException {
        File teamFile = new File(file); 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(teamFile, true))) {
            writer.write("{\n");
            writer.write("  \"code\": \"" + this.code + "\",\n");
            writer.write("  \"country\": \"" + this.country + "\",\n");
            writer.write("  \"logo\": \"" + this.logo + "\",\n");
            writer.write("  \"founded\": " + this.foundedYear + ",\n");
            writer.write("  \"name\": \"" + this.name + "\",\n");
            writer.write("  \"stadium\": \"" + this.stadiumName + "\"\n");
            writer.write("}\n");
        } catch (IOException e) {
            System.out.println("Erro ao exportar equipa para JSON: " + e.getMessage());
        }
    }
    
}
