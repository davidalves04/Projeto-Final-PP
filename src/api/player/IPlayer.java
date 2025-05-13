/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.player;

import enums.PreferredFoot;
import java.time.LocalDate;

/**
 *
 * @author Utilizador
 */
public interface IPlayer {
    
     String getName();
    LocalDate getBirthDate();
    int getAge();
    String getNationality();
    void setPosition(IPlayerPosition position);
    String getPhoto();
    int getNumber();
    int getShooting();
    int getPassing();
    int getStamina();
    int getSpeed();
    IPlayerPosition getPosition();
    float getHeight();
    float getWeight();
    PreferredFoot getPreferredFoot();
    
}
