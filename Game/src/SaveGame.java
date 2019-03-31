import java.io.Serializable;
import java.util.List;

public class SaveGame implements Serializable {

    private int currentLevelIndex;
    private List<Level> completedLevels;
    private List<Level> levels;

    public SaveGame(int currentLevelIndex, List<Level> completedLevels, List<Level> levels) {
        this.currentLevelIndex = currentLevelIndex;
        this.completedLevels = completedLevels;
        this.levels = levels;
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
}
