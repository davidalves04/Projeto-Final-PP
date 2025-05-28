package user_interface;



import com.ppstudios.footballmanager.api.contracts.player.IPlayer;


import java.io.IOException;

public class TeamView {

    
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
    
}
