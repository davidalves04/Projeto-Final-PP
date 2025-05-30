/*  
* Nome: David Sérgio Ferreira Alves
* Número: 8240231
* Turma: LSIRC T2
*  
* Nome: Gabriel Alexandre Meireles Moreira 
* Número: 8240266  
* Turma: LSIRC T2
*/
package htmlgenerators;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe responsável por gerar um ficheiro HTML com o conteúdo de um ficheiro JSON representando uma época.
 */
public class LeagueHtmlGenerator {

    /**
     * Construtor por omissão da classe {@code LeagueHtmlGenerator}.
     */
    public LeagueHtmlGenerator() {}

    /**
     * Gera um ficheiro HTML com o conteúdo de um ficheiro JSON especificado.
     *
     * @param jsonFilePath Caminho para o ficheiro JSON que contém os dados da época.
     * @param outputPath Caminho relativo (dentro da pasta "html/") onde o ficheiro HTML será guardado.
     * @throws IOException Se ocorrer um erro ao ler o ficheiro JSON ou ao escrever o HTML.
     */
    public static void generate(String jsonFilePath, String outputPath) throws IOException {
        String jsonContent = Files.readString(Paths.get(jsonFilePath));

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Época</title></head><body>");
        html.append("<h1>Dados da Época (JSON)</h1>");
        html.append("<pre>").append(jsonContent).append("</pre>");
        html.append("</body></html>");

        Files.write(Paths.get("html/" + outputPath), html.toString().getBytes(StandardCharsets.UTF_8));
    }
}
