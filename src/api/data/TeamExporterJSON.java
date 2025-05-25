/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;

import api.team.Team;
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
public class TeamExporterJSON {
    public void exportTeamsArrayToJson(Team[] teams, String teamFile) {
         File file = new File(teamFile);
         
         
    try {
        
         if (file.exists() && file.length() > 0) {
            String conteudo = new String(Files.readAllBytes(Paths.get(teamFile)));
            if (conteudo.trim().endsWith("]")) {
                conteudo = conteudo.trim();
                conteudo = conteudo.substring(0, conteudo.length() - 1) + ",\n";
                Files.write(Paths.get(teamFile), conteudo.getBytes());
            }
        }
        
       if (!file.exists()|| file.length() == 0) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(teamFile,true))) {
            writer.write("[\n"); //Escreve o [ inicial
        }
            }

        
        for (int i = 0; i < teams.length; i++) {
            teams[i].exportToJson();  

            
            if (i < teams.length - 1) { //Assim o ultimo nao tem virgula
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    writer.write(",\n");
                }
            }
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("\n]"); //Escreve o ] final
        }
    } catch (IOException e) {
        System.out.println("Erro ao exportar lista para JSON: " + e.getMessage());
    }
}
}
