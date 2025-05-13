/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.player;

import enums.PlayerPosition;

/**
 *
 * @author Utilizador
 */
public class Position implements IPlayerPosition {
      private String descrition;

    public Position(String descrition) {
        this.descrition = descrition;
    }

 

    @Override
    public String getDescrition() {
        return this.descrition;
    }
    
    
    
    
}
