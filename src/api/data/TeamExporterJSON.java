package api.data;

import api.team.Squad;
import api.team.Team;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TeamExporterJSON {

    
    //Export do array de clubes 
    public void exportTeamsArrayToJson(Team[] teams, String teamFile) throws IOException {
        File file = new File(teamFile);
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
    for (int i = 0; i < teams.length; i++) {
        teams[i].setFile(teamFile);
        teams[i].exportToJson(); 

        if (i < teams.length - 1) {
            writer.write(",\n");
        }
    }

   
    writer.write("\n]");
}
    }

    
    
  //Export do array de squads de cada clube
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
    
  //A squad do utilizador sera num ficheiro separado  
   public static void exportMySquad(Squad mySquad,String mySquadFile) throws IOException{
       File file = new File(mySquadFile);

    // Abre o arquivo para escrita e vai reescrever o conteúdo
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
       
               

        mySquad.setFile(mySquadFile);
        mySquad.exportToJson(); 

        
    }
   }
    
    
    
}
