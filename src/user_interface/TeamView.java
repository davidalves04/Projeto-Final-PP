/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user_interface;

import api.data.TeamImporterJSON;
import api.team.Squad;
import api.team.Team;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import java.io.IOException;


/**
 *
 * @author david
 */
public class TeamView {
    public static Squad teamView(Team team) throws IOException{
        Squad mySquad = null;
        String jsonFile = null;
        
        if (team != null) {
             jsonFile = team.squadJsonFile();
                        
        } else {
             System.out.println("Nenhuma equipa carregada.");
        }
        
      mySquad = TeamImporterJSON.loadSquadFromJson(jsonFile, team);
        
      return mySquad;              
    }
}
