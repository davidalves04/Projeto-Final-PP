package api.league;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class League implements ILeague {
    private String name;
    private ISeason[] seasons;
    private int seasonCount;
    
    private String file;

    private static final int MAX_SEASONS = 10; // Podes ajustar conforme necessário

    public League(String name) {
        this.name = name;
        this.seasons = new ISeason[MAX_SEASONS];
        this.seasonCount = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ISeason[] getSeasons() {
        ISeason[] result = new ISeason[seasonCount];
        for (int i = 0; i < seasonCount; i++) {
            result[i] = seasons[i];
        }
        return result;
    }

    @Override
    public boolean createSeason(ISeason season) {
        if (seasonCount >= MAX_SEASONS || season == null) {
            return false;
        }
        seasons[seasonCount++] = season;
        return true;
    }

    @Override
    public ISeason removeSeason(int index) {
        if (index < 0 || index >= seasonCount) {
            return null;
        }
        ISeason removed = seasons[index];
        for (int i = index; i < seasonCount - 1; i++) {
            seasons[i] = seasons[i + 1];
        }
        seasons[--seasonCount] = null;
        return removed;
    }

    @Override
    public ISeason getSeason(int index) {
        if (index < 0 || index >= seasonCount) {
            return null;
        }
        return seasons[index];
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    
    
    
    @Override
    public void exportToJson() {
        File fileLeague = new File(file); // ou o nome que quiser

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileLeague,true))) {
        
        writer.write("{\n");
        writer.write("  \"name\": \"" + name + "\",\n");
        writer.write("  \"seasons\": ");
        for(int i = 0;i < seasons.length;i++){
            seasons[i].exportToJson();
            
        }
        writer.write("}\n");
        
    } catch (IOException e) {
        e.printStackTrace(); // ou logging apropriado
    }
    
    }
    
}
    
