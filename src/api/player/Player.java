/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
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
 * Implementação da interface {@link IPlayer} que representa um jogador de futebol.
 * Esta classe armazena os dados pessoais, atributos técnicos e físicos do jogador,
 * bem como permite a exportação das informações em formato JSON.
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

    /**
     * Construtor que inicializa os dados de um jogador.
     *
     * @param name Nome do jogador
     * @param birth Data de nascimento
     * @param age Idade do jogador
     * @param nationality Nacionalidade
     * @param number Número na camisola
     * @param photo Caminho ou URL da foto
     * @param stats Estatísticas do jogador
     * @param position Posição principal em campo
     * @param preferredFoot Pé dominante
     * @param height Altura em metros
     * @param weight Peso em quilogramas
     */
    public Player(String name, LocalDate birth, int age, String nationality, int number, String photo,
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

    /**
     * Devolve o nome do jogador.
     * 
     * @return nome do jogador
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Devolve a data de nascimento do jogador.
     * 
     * @return data de nascimento
     */
    @Override
    public LocalDate getBirthDate() {
        return this.birth;
    }

    /**
     * Devolve a idade do jogador.
     * 
     * @return idade do jogador
     */
    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * Devolve a nacionalidade do jogador.
     * 
     * @return nacionalidade
     */
    @Override
    public String getNationality() {
        return this.nationality;
    }

    /**
     * Define a posição principal do jogador.
     * 
     * @param position posição a definir
     * @throws IllegalArgumentException se a posição for null
     */
    @Override
    public void setPosition(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = position;
    }

    /**
     * Devolve o caminho ou URL da foto do jogador.
     * 
     * @return caminho ou URL da foto
     */
    @Override
    public String getPhoto() {
        return this.photo;
    }

    /**
     * Devolve o número do jogador na camisola.
     * 
     * @return número na camisola
     */
    @Override
    public int getNumber() {
        return this.number;
    }

    /**
     * Devolve a capacidade de remate do jogador.
     * 
     * @return valor da capacidade de remate
     */
    @Override
    public int getShooting() {
        return stats.getShooting();
    }

    /**
     * Devolve a capacidade de passe do jogador.
     * 
     * @return valor da capacidade de passe
     */
    @Override
    public int getPassing() {
        return stats.getPassing();
    }

    /**
     * Devolve a resistência física do jogador.
     * 
     * @return valor da resistência
     */
    @Override
    public int getStamina() {
        return stats.getStamina();
    }

    /**
     * Devolve a velocidade do jogador.
     * 
     * @return valor da velocidade
     */
    @Override
    public int getSpeed() {
        return stats.getSpeed();
    }
    
    public int getDefense(){
        return stats.getDefense();
    }
    

    /**
     * Devolve a posição principal do jogador.
     * 
     * @return posição principal
     */
    @Override
    public IPlayerPosition getPosition() {
        return this.position;
    }

    /**
     * Devolve a altura do jogador em metros.
     * 
     * @return altura em metros
     */
    @Override
    public float getHeight() {
        return this.height;
    }

    /**
     * Devolve o peso do jogador em quilogramas.
     * 
     * @return peso em quilogramas
     */
    @Override
    public float getWeight() {
        return this.weight;
    }

    /**
     * Devolve o pé preferencial do jogador.
     * 
     * @return pé preferencial
     */
    @Override
    public PreferredFoot getPreferredFoot() {
        return this.preferredFoot;
    }

    /**
     * Devolve o caminho do ficheiro para exportação.
     *
     * @return caminho do ficheiro
     */
    public String getFile() {
        return file;
    }

    /**
     * Define o caminho do ficheiro para exportação JSON.
     *
     * @param file caminho do ficheiro
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Exporta os dados do jogador para um ficheiro em formato JSON.
     *
     * @throws IOException se ocorrer erro na escrita do ficheiro
     */
    @Override
    public void exportToJson() throws IOException {
        File playerFile = new File(file);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile, true))) {

            writer.write("{\n");
            writer.write("  \"name\": \"" + this.name + "\",\n");
            writer.write("  \"birthDate\": \"" + this.birth + "\",\n");
            writer.write("  \"nationality\": \"" + this.nationality + "\",\n");
            writer.write("  \"basePosition\": \"" + this.position.getDescription() + "\",\n");
            writer.write("  \"photo\": \"" + this.photo + "\",\n");
            writer.write("  \"number\": " + this.number + ",\n");
            writer.write("  \"age\": " + this.age + ",\n");
            writer.write("  \"height\": " + this.height + ",\n");
            writer.write("  \"weight\": " + this.weight + ",\n");
            writer.write("  \"shootingstats\": " + getShooting() + ",\n");
            writer.write("  \"staminastats\": " + getStamina() + ",\n");
            writer.write("  \"speedstats\": " + getSpeed() + ",\n");
            writer.write("  \"passingstats\": " + getPassing() + ",\n");
            writer.write("  \"defensestats\": " + getDefense() + "\n");
            
            writer.write("}\n");

        } catch (IOException e) {
            System.out.println("Erro ao exportar para JSON: " + e.getMessage());
        }
    }
}
