import java.io.File;
import java.io.Serializable;

public class Level implements Serializable {

    private String name;
    private String path;
    private File levelFile;
    private int levelNumber;

    Level(String name, String path, File levelFile, int levelNumber) {
        this.name = name;
        this.path = path;
        this.levelFile = levelFile;
        this.levelNumber = levelNumber;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public File getLevelFile() {
        return levelFile;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

}
