package user_interface;



import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;


import java.io.IOException;

public class TeamView {
     public static void showSquad(ITeam mySquad) {
        System.out.println("=== Plantel do Clube: " + mySquad.getClub().getName() + " ===");
        IPlayer[] squadPlayers = mySquad.getPlayers();

        for (int i = 0; i < squadPlayers.length; i++) {
            IPlayer jogador = squadPlayers[i];
            System.out.println("Número: " + jogador.getNumber()+ " - " + jogador.getName() +
                " (" + jogador.getPosition().getDescription() + ") - " +
                " (Energia: " + jogador.getStamina() +")");
        }
     }   
    
    public static void showLineup(IPlayer[] lineupPlayers) throws IOException {
         String gk = "";
    String def = "";
    String mid = "";
    String att = "";

    for (IPlayer p : lineupPlayers) {
        if (p == null) continue;

        String pos = p.getPosition().getDescription().toUpperCase();
        String playerInfo =  p.getNumber() + " " +  p.getName() + "(Energia: " + p.getStamina() + ")" ;

        if (pos.equals("GOALKEEPER") ) {
            if (!gk.isEmpty()) gk += " | ";
            gk += playerInfo;
        } else if (pos.equals("DEFENDER")) {
            if (!def.isEmpty()) def += " | ";
            def += playerInfo;
        } else if (pos.equals("MIDFIELDER") ) {
            if (!mid.isEmpty()) mid += " | ";
            mid += playerInfo;
        } else if (pos.equals("FORWARD")) {
            if (!att.isEmpty()) att += " | ";
            att += playerInfo;
        }
    }

    System.out.println("(GK) " + gk);
    System.out.println("(DEF) " + def);
    System.out.println("(MID) " + mid);
    System.out.println("(ATT) " + att);
    }
    
    public static void showBench(IPlayer[] benchPlayers, String positionFilter){
        StringBuilder output = new StringBuilder();
    String upperFilter = positionFilter.toUpperCase();

    for (IPlayer p : benchPlayers) {
        if (p == null) continue;

        String pos = p.getPosition().getDescription().toUpperCase();
        if (!pos.equals(upperFilter)) continue;

        String playerInfo = p.getNumber() + " " + p.getName() + " (Energia: " + p.getStamina() + ")";
        if (output.length() > 0) {
            output.append(" | ");
        }
        output.append(playerInfo);
    }

    if (output.length() == 0) {
        System.out.println("Nenhum jogador no banco para a posição: " + positionFilter);
    } else {
        System.out.println("(BENCH - " + upperFilter + ") " + output);
    }
    }
    
    public static void showNonSelectedPlayers(IPlayer[] nonSelectedPlayers, String positionFilter){
        StringBuilder output = new StringBuilder();
    String upperFilter = positionFilter.toUpperCase();

    for (IPlayer p : nonSelectedPlayers) {
        if (p == null) continue;

        String pos = p.getPosition().getDescription().toUpperCase();
        if (!pos.equals(upperFilter)) continue;

        String playerInfo = p.getNumber() + " " + p.getName() + " (Energia: " + p.getStamina() + ")";
        if (output.length() > 0) {
            output.append(" | ");
        }
        output.append(playerInfo);
    }

    if (output.length() == 0) {
        System.out.println("Nenhum jogador no banco para a posição: " + positionFilter);
    } else {
        System.out.println("JOGADORES NÃO CONVOCADOS - " + upperFilter + ") " + output);
    }
    }
    
}
