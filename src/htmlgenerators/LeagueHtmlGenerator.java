package htmlgenerators;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsável por gerar um ficheiro HTML simples para uma liga.
 * 
 * O ficheiro gerado contém um título com o nome da liga e um parágrafo
 * com conteúdo adicional padrão.
 * 
 * Exemplo de uso: gerar uma página HTML com o nome da liga e guardá-la
 * num ficheiro indicado.
 */
public class LeagueHtmlGenerator {

    /**
     * Gera um ficheiro HTML com o nome da liga e guarda no caminho especificado.
     * 
     * @param leagueName O nome da liga a ser exibido no título da página.
     * @param outputPath O caminho do ficheiro onde o HTML será guardado.
     * @throws IOException Se ocorrer algum erro ao escrever no ficheiro.
     */
    public static void generate(String leagueName, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>").append(leagueName).append("</h1>");
        html.append("<p>Conteúdo adicional da liga pode ser inserido aqui.</p>");
        html.append("</body></html>");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        }
    }
}
