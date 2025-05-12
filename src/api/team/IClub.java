/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.team;

import api.player.IPlayer;
import api.player.IPlayerPosition;

/**
 *
 * @author Utilizador
 */
public interface IClub {
    void addPlayer(IPlayer player);

    String getCode();

    String getCountry();

    int getFoundedYear();

    String getLogo();

    String getName();

    int getPlayerCount();

    IPlayer[] getPlayers(); // cópia dos jogadores

    String getStadiumName();

    boolean isPlayer(IPlayer player);

    boolean isValid();

    void removePlayer(IPlayer player);

    IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position);
    
    
}
