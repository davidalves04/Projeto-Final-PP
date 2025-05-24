/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

/**
 *
 * @author Utilizador
 */
public class BestPlayerSelector implements IPlayerSelector{
    
     @Override
    public IPlayer selectPlayer(IClub iclub, IPlayerPosition ipp) {
        
        
        if (iclub == null || ipp == null) {
            throw new IllegalArgumentException("Clube ou posição não podem ser nulos.");
        }

        IPlayer[] players = iclub.getPlayers(); 
        int bestStats = 0; 
        IPlayer bestPlayer = null;
        int i = 0;
        
        
        if (players.length == 0) {
            throw new IllegalStateException("O clube está vazio.");
        }

        
        
        while (i < players.length) {
             IPlayer player = players[i];
             
        if (player.getPosition().equals(ipp)) {
           int playerStats = (player.getPassing() + player.getShooting() + player.getSpeed() + player.getStamina()) / 4;
           
           if (playerStats > bestStats) {
            bestStats = playerStats;
            bestPlayer = player;
        }
        }
        i++;
      }  
    
       if (bestPlayer == null) {
            throw new IllegalStateException("Nenhum jogador encontrado para essa posição.");
        }
       
              return bestPlayer;

    }
    
    
}
