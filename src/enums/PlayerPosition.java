/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 *
 * @author david
 */
public enum PlayerPosition {
    GK, DEF, MID, FWD;
    
      public static String positionToString(PlayerPosition pos) {
        switch (pos) {
            case GK:
                return "Guarda-Redes";
            case DEF:
                return "Defesa";
            case MID:
                return "Médio";
            case FWD:
                return "Avançado";
            default:
                return "Desconhecida";
        }
    }
      
}
