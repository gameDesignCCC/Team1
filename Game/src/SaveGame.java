import java.io.Serializable;
import java.util.List;

public class SaveGame implements Serializable {

    private int currentLevelIndex;
    private List<Level> completedLevels;
    private List<Level> levels;
    private boolean showFPS;
    private boolean autoSave;

    public SaveGame(int currentLevelIndex, List<Level> completedLevels, List<Level> levels, boolean displayFPS, boolean autoSave) {
        this.currentLevelIndex = currentLevelIndex;
        this.completedLevels = completedLevels;
        this.levels = levels;
        this.showFPS = displayFPS;
        this.autoSave = autoSave;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public List<Level> getCompletedLevels() {
        return completedLevels;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public boolean getDisplayFPS(){
        return showFPS;
    }

    public boolean getAutoSave(){
        return autoSave;
    }
}
