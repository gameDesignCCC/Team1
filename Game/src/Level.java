import java.io.File;

public class Level {

    private String name;
    private String path;
    private File levelFile;
    private int levelNumber;

    Level(String name, String path, int levelNumber, File levelFile){
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

    public File getLevelFile(){
        return levelFile;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

}
