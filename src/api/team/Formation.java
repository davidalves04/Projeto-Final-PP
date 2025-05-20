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

    @Override
    public int getTacticalAdvantage(IFormation formation) {
        //Ainda por implementar
        return 0;
    }
     
     
    
    
    
}
