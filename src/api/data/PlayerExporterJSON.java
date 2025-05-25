/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;

import api.player.Player;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Utilizador
 */
public class PlayerExporterJSON {
    
    
    
    
    public void exportPlayersArrayToJson(Player[] players, String playerFile) throws IOException {
        File file = new File(playerFile);
    try {
        
        //Substitui o "]" final por uma , caso ja tenho info no fichiero
        if (file.exists() && file.length() > 0) {
            String conteudo = new String(Files.readAllBytes(Paths.get(playerFile)));
            if (conteudo.trim().endsWith("]")) {
                conteudo = conteudo.trim();
                conteudo = conteudo.substring(0, conteudo.length() - 1) + ",\n";
                Files.write(Paths.get(playerFile), conteudo.getBytes());
            }
        }
        
        //Adiciona [ caso nao haja info no ficheiro
            if (!file.exists()|| file.length() == 0) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile,true))) {
            writer.write(" plantel: [\n"); //Escreve o plantel : [ inicial
        }
            }
        
        for (int i = 0; i < players.length; i++) {
            players[i].exportToJson();  

            
            if (i < players.length - 1) { //Assim o ultimo nao tem virgula
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile, true))) {
                    writer.write(",\n");
                }
            }
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile, true))) {
            writer.write("\n]"); //Escreve o ] final
        }
        
        
    } catch (IOException e) {
        System.out.println("Erro ao exportar lista para JSON: " + e.getMessage());
    }
}
    
}
