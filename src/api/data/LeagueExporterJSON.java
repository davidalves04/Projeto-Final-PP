/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;


import api.league.League;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author Utilizador
 */
public class LeagueExporterJSON {
   public void exportLeaguesArrayToJson(League[] leagues, String leagueFile) throws IOException {
 File file = new File(leagueFile);
boolean existeConteudo = false;

// 1) Le o conteúdo para verificar se já tem conteúdo JSON
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
        // remover o ] para continuar o array
        sConteudo = sConteudo.substring(0, sConteudo.length() - 1);
        
        // Sobrescreve o arquivo, sem o ] e mete uma ,
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(sConteudo);
            writer.write(",\n"); // pronto para adicionar novos objetos
        }
    }
}

// 2) Se arquivo esta vazio ou não existe, cria com [
if (!existeConteudo) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
        writer.write("[\n");
    }
}

// 3) Agora abre o arquivo para append, e para cada equipa chama exportToJson()
try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
    for (int i = 0; i < leagues.length; i++) {
        leagues[i].exportToJson(); // supondo que exportToJson agora recebe BufferedWriter

        if (i < leagues.length - 1) {
            writer.write(",\n");
        }
    }

    // 4) Fecha o array
    writer.write("\n]");
}
}
}
