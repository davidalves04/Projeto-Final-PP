/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package api.data;

import api.team.Squad;
import api.team.Team;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsável por exportar objetos do tipo {@link Team} e {@link Squad}
 * para ficheiros no formato JSON.
 * Implementa métodos para lidar com múltiplos clubes, os seus planteis
 * e o plantel personalizado do utilizador.
 * 
 * O ficheiro JSON gerado contém um array de objetos JSON, sendo atualizado
 * de forma incremental.
 * 
 * @author Gabriel
 */
public class TeamExporterJSON {

    /**
     * Exporta um array de objetos {@link Team} para um ficheiro JSON.
     * Se o ficheiro já existir e tiver conteúdo, novos clubes são adicionados ao array existente.
     *
     * @param teams Array de clubes a exportar.
     * @param teamFile Caminho para o ficheiro onde os clubes serão exportados.
     * @throws IOException Se ocorrer um erro ao ler ou escrever no ficheiro.
     */
    public void exportTeamsArrayToJson(Team[] teams, String teamFile) throws IOException {
        File file = new File(teamFile);
        boolean existeConteudo = false;

        // Verifica se já existe conteúdo no ficheiro
        if (file.exists() && file.length() > 0) {
            StringBuilder conteudo = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    conteudo.append(line).append("\n");
                }
            }
            String sConteudo = conteudo.toString().trim();
            if (sConteudo.endsWith("]")) {
                existeConteudo = true;
                sConteudo = sConteudo.substring(0, sConteudo.length() - 1);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                    writer.write(sConteudo);
                    writer.write(",\n"); // prepara para adicionar novos objetos
                }
            }
        }

        // Se o ficheiro estiver vazio, inicializa com [
        if (!existeConteudo) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write("[\n");
            }
        }

        // Escreve os clubes no ficheiro
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (int i = 0; i < teams.length; i++) {
                teams[i].setFile(teamFile);
                teams[i].exportToJson(); // Exporta o clube individualmente
                if (i < teams.length - 1) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]");
        }
    }

    /**
     * Exporta um array de objetos {@link Squad} (plantéis dos clubes) para um ficheiro JSON.
     * Funciona de forma semelhante ao método de exportação dos clubes.
     *
     * @param squads Array de plantéis a exportar.
     * @param squadFile Caminho para o ficheiro onde os plantéis serão exportados.
     * @throws IOException Se ocorrer um erro na leitura ou escrita do ficheiro.
     */
    public void exportSquadToJson(Squad[] squads, String squadFile) throws IOException {
        File file = new File(squadFile);
        boolean existeConteudo = false;

        if (file.exists() && file.length() > 0) {
            StringBuilder conteudo = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    conteudo.append(line).append("\n");
                }
            }
            String sConteudo = conteudo.toString().trim();
            if (sConteudo.endsWith("]")) {
                existeConteudo = true;
                sConteudo = sConteudo.substring(0, sConteudo.length() - 1);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                    writer.write(sConteudo);
                    writer.write(",\n");
                }
            }
        }

        if (!existeConteudo) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write("[\n");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (int i = 0; i < squads.length; i++) {
                squads[i].setFile(squadFile);
                squads[i].exportToJson();
                if (i < squads.length - 1) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]");
        }
    }

    /**
     * Exporta o plantel do utilizador (squad pessoal) para um ficheiro JSON separado.
     * Este método substitui completamente o conteúdo anterior do ficheiro.
     *
     * @param mySquad Plantel do utilizador a exportar.
     * @param mySquadFile Caminho para o ficheiro onde o plantel será guardado.
     * @throws IOException Se ocorrer erro na escrita do ficheiro.
     */
    public static void exportMySquad(Squad mySquad, String mySquadFile) throws IOException {
        File file = new File(mySquadFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            mySquad.setFile(mySquadFile);
            mySquad.exportToJson(); // Exporta o plantel do utilizador
        }
    }
}
