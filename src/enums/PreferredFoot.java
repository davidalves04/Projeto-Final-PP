/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 *
 * @author david
 */
public enum PreferredFoot {
    LEFT, RIGHT;
    
        public static String footToString(PreferredFoot foot) {
        switch (foot) {
            case LEFT:
                return "Esquerdo";
            case RIGHT:
                return "Direito";
            default:
                return "Desconhecido";
        }
    }
    
}
