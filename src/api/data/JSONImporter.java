/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;

import api.player.Player;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author david
 */
public class JSONImporter {

   
  
    private StringBuilder json = new StringBuilder();

    // Lê o ficheiro JSON todo para a StringBuilder
    public void readFile(String filename) throws IOException {
        json.setLength(0);  // limpa conteúdo anterior

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line.trim());
            }
        }
    }

    //Ira returna a String 
    public String getJson() {
        return json.toString();
    }
}