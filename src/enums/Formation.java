/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 *
 * @author Utilizador
 */
public enum Formation {
    QUATRO_TRES_TRES,QUATRO_QUATRO_DOIS,TRES_CINCO_DOIS,
    CINCO_TRES_DOIS,QUATRO_DOIS_TRES_UM;
    
        public static String formationToString(Formation f) {
        switch (f) {
            case QUATRO_TRES_TRES:
                return "4-3-3";
            case QUATRO_QUATRO_DOIS:
                return "4-4-2";
            case TRES_CINCO_DOIS:
                return "3-5-2";
            case CINCO_TRES_DOIS:
                return "5-3-2";
            case QUATRO_DOIS_TRES_UM:
                return "4-2-3-1";
            default:
                return "Desconhecida";
        }
    }

}
