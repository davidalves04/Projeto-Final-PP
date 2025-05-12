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
public interface IPlayerSelector {
        IPlayer selectPlayer(IClub club, IPlayerPosition position);

}
