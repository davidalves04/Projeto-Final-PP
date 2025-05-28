/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package user_interface;


import api.data.LeagueExporterJSON;
import api.data.LeagueImporterJSON;
import api.data.PlayerExporterJSON;
import api.data.PlayerImporterJSON;
import api.data.TeamImporterJSON;
import api.league.League;
import api.league.Season;
import api.player.Player;
import api.player.PlayerStats;
import api.player.Position;
import api.team.Squad;
import api.team.Team;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;
import java.time.LocalDate;


/**
 *
 * @author Utilizador
 */
public class mainTeste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Team[] totalTeams = TeamImporterJSON.teamsFromJson("clubs.json");
        IPlayer[] players = totalTeams[0].getPlayers();
        for(int i = 0; i < players.length;i++){
            System.out.println(players[i].getName()
            );
        }

        Squad[] totalSquads = TeamImporterJSON.squadsFromJson("squad.json", totalTeams);
        IPlayer[] squadPlayers = totalSquads[0].getPlayers();
        for(int i = 0; i < squadPlayers.length;i++){
            System.out.println(squadPlayers[i].getName()
            );
        }
    }
    }
    
    

