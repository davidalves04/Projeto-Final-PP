/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.team;


import com.ppstudios.footballmanager.api.contracts.team.IFormation;

/**
 *
 * @author Utilizador
 */
public class Formation implements IFormation{
     private String displayName;

    public Formation(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    
    
    @Override
    public int getTacticalAdvantage(IFormation formation) {
       if (this.displayName == null) {
           throw new IllegalStateException("Home team formation not set."); 
       }
       
        String home = this.displayName;  
    String away = formation.getDisplayName();        

    // Regras fictícias simples
    if (home.equals(away)) {
        return 0; // Igualdade tática
    }

    if (home.equals("5-3-2") && away.equals("4-3-3")) return 1; //1- Vantagosa 2- bastante vantajosa 0- Sem vantagem  -1 - desfavorecida -2 - bastante desfavorecida
    if (home.equals("4-3-3") && away.equals("5-3-2")) return -1;
    if (home.equals("4-4-2") && away.equals("4-3-3")) return -2;
    if (home.equals("4-3-3") && away.equals("4-4-2")) return 2;
    if (home.equals("4-4-2") && away.equals("5-3-2")) return 0;
    if (home.equals("5-3-2") && away.equals("4-4-2")) return 0;

    // Caso nenhuma regra específica se aplique
    return 0;
       
       
    }

    @Override
    public String toString() {
        return this.displayName;
    }
     
     
    
    
    
}
