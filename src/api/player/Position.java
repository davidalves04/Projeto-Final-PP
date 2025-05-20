/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.player;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;


/**
 *
 * @author Utilizador
 */
public class Position implements IPlayerPosition {
    private String description;

    public Position(String description) {
        this.description = description;
    }

    
    
    @Override
    public String getDescription() {
        return this.description;
    }
}
