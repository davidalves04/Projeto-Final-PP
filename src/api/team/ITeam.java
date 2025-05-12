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
public interface ITeam {
    
    void addPlayer(IPlayer player);

    IClub getClub();

    IFormation getFormation();

    IPlayer[] getPlayers(); // cópia dos jogadores

    int getPositionCount(IPlayerPosition position);

    int getTeamStrength(); // força da equipa (0-100)

    boolean isValidPositionForFormation(IPlayerPosition position);

    void setFormation(IFormation formation);
}
