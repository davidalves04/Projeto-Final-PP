/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import utils.JsonAccumulator;

/**
 * Implementação da interface {@link ILeague}, representando uma liga de futebol
 * que pode conter múltiplas épocas (seasons).
 */
public class League implements ILeague {

    /** Nome da liga. */
    private String name;

    /** Array de épocas associadas à liga. */
    private ISeason[] seasons;

    /** Contador do número atual de épocas registadas. */
    private int seasonCount;

    /** Caminho do ficheiro de exportação. */
    private String file;

    /** Número máximo de épocas permitidas na liga. */
    private static final int MAX_SEASONS = 10;

    /**
     * Construtor da classe League.
     *
     * @param name o nome da liga
     */
    public League(String name) {
        this.name = name;
        this.seasons = new ISeason[MAX_SEASONS];
        this.seasonCount = 0;
    }

    /**
     * Obtém o nome da liga.
     *
     * @return o nome da liga
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Retorna todas as épocas criadas na liga.
     *
     * @return um array com as épocas registadas
     */
    @Override
    public ISeason[] getSeasons() {
        ISeason[] result = new ISeason[seasonCount];
        for (int i = 0; i < seasonCount; i++) {
            result[i] = seasons[i];
        }
        return result;
    }

    /**
     * Cria uma nova época na liga.
     *
     * @param season a época a adicionar
     * @return true se a época foi adicionada com sucesso, false caso contrário
     */
    @Override
    public boolean createSeason(ISeason season) {
        if (seasonCount >= MAX_SEASONS || season == null) {
            return false;
        }
        seasons[seasonCount++] = season;
        return true;
    }

    /**
     * Remove uma época da liga pelo índice.
     *
     * @param index o índice da época a remover
     * @return a época removida ou null se o índice for inválido
     */
    @Override
    public ISeason removeSeason(int index) {
        if (index < 0 || index >= seasonCount) {
            return null;
        }
        ISeason removed = seasons[index];
        for (int i = index; i < seasonCount - 1; i++) {
            seasons[i] = seasons[i + 1];
        }
        seasons[--seasonCount] = null;
        return removed;
    }

    /**
     * Obtém uma época da liga pelo índice.
     *
     * @param index o índice da época
     * @return a época no índice especificado, ou null se o índice for inválido
     */
    @Override
    public ISeason getSeason(int index) {
        if (index < 0 || index >= seasonCount) {
            return null;
        }
        return seasons[index];
    }

    /**
     * Obtém o caminho do ficheiro onde a liga será exportada.
     *
     * @return o caminho do ficheiro
     */
    public String getFile() {
        return file;
    }

    /**
     * Define o caminho do ficheiro para exportação.
     *
     * @param file o caminho do ficheiro
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Exporta os dados da liga para um ficheiro JSON.
     * <p>
     * Cada época associada será também exportada através do seu próprio método
     * {@code exportToJson()}. Os dados são acumulados usando um {@link JsonAccumulator}
     * e escritos no ficheiro especificado.
     * </p>
     *
     * @throws IOException se ocorrer um erro ao escrever no ficheiro
     */
    public void exportToJson() throws IOException {
        JsonAccumulator acc = new JsonAccumulator();

        if (seasons != null) {
            for (ISeason s : seasons) {
                if (s instanceof Season season) {
                    season.setJsonAccumulator(acc);
                }
            }
        }

        acc.append("{\n");
        acc.append("  \"name\": \"" + name + "\",\n");
        acc.append("  \"seasons\": [\n");

        if (seasons != null) {
            for (int i = 0; i < seasonCount; i++) {
                if (seasons[i] != null) {
                    seasons[i].exportToJson();
                    if (i < seasonCount - 1) {
                        acc.append(",\n");
                    }
                }
            }
        }

        acc.append("\n  ]\n");
        acc.append("}");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(acc.getJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
