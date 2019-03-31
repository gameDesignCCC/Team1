/*
 * Name: CCC 2018-2019 Platformer Game
 * Date: 10/6/2018 - 31/3/2019
 * Team: Advanced Game Development Team 1
 * Author(s):
 * Repo: https://github.com/gameDesignCCC/Team1.git
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainApplication extends Application {

    // Stage Size
    static final double WINDOW_SIZE_X = 1280.0;
    static final double WINDOW_SIZE_Y = 720.0;

    // Installation Directory (set in loadResources())
    static File installDIR;

    // Game Saving
    static File defaultSaveDIR;
    static File defaultSaveFile;
    static String defaultSaveName = "save";
    static String saveExt = ".gs";
    static SaveGame savedGame;
    static boolean autoSave = true;

    // The Stage
    private static Stage stage;

    // Framerate Limiter
    private static final double MAX_FRAME_RATE = 16.67;
    private static long time = System.currentTimeMillis();

    //For FPS Display
    private static final long[] frameTimes = new long[100];
    private static int frameTimeIndex = 0;
    private static boolean arrayFilled = false;
    private static Label fpsCounter = new Label();
    static boolean displayFPS = true;

    // Key(s) pressed for player movement.
    private static HashMap<KeyCode, Boolean> keys;

    // The Player
    static Player player;

    // Distance Traveled From Level Start
    static double distanceScrolled = 0.0;

    // Placeholder Map Background
    private static ImageView levelBG = new ImageView("/assets/levels/backgrounds/alt_level_bg_extended.png");

    // Level Decoration (fog, lava glow)
    static final boolean LEVEL_DECORATION = true;

    // Game Loop Timer
    private static AnimationTimer timer;

    // Map Objects
    static ArrayList<Object> sceneObjects = new ArrayList<>();

    // Enemies
    static ArrayList<Enemy> enemies;

    // Player Inventory
    static ArrayList<StaticRect> collectedParts = new ArrayList<>();
    static ArrayList<StaticRect> collectedPartsCurrent = new ArrayList<>();

    // Current Scene Parent
    static Pane currentRoot;

    // Level List
    static List<Level> levels = new ArrayList<>();
    static List<Level> completedLevels = new ArrayList<>();
    static int currentLevelIndex = 0;

    // Music Player
    static AudioClip musicPlayer = new AudioClip("file:C:/Users/Quack/IdeaProjects/Team1/Game/src/assets/audio/music.mp3");

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadResources();
        queueLevels();
        Menu.init();
        loadGame();

        // completedLevels.addAll(levels);

        stage = primaryStage;

        stage.getIcons().add(new Image("/assets/application/favicon_placeholder128.png"));
        stage.setTitle("Placeholder Title");
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> exit());
        stage.setScene(Menu.mainMenu()); // Load Main Menu
        stage.show();

    }

    public static boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getGameScene(Level level) {
        // Reset Scene

        Pane root = new Pane();
        Scene gameScene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);

        gameScene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        keys = new HashMap<>();
        sceneObjects = new ArrayList<>();
        enemies = new ArrayList<>();

        distanceScrolled = 0.0;

        // Timer for game loop. / Should stay at ~60 UPS
        MainApplication.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                update();
            }
        };

        // Load Next Level
        MapLoader mapLoader = new MapLoader();

        for (Object obj : mapLoader.load(level)) {
            if (obj instanceof StaticObject) {
                StaticObject staticObject = (StaticObject) obj;

                sceneObjects.add(staticObject);
                root.getChildren().add(staticObject.getSprite());

            } else if (obj instanceof Rectangle) {
                Rectangle rectangle = ((Rectangle) obj);

                sceneObjects.add(rectangle);
                root.getChildren().add(rectangle);
                if (rectangle.getId().equals("effect-lava-glow")) {
                    rectangle.toBack();
                }

            } else if (obj instanceof Enemy) {
                Enemy enemy = ((Enemy) obj);

                sceneObjects.add(enemy);
                enemies.add(enemy);
                root.getChildren().addAll(enemy, enemy.hpBar);
            }
        }

        for (StaticObject sr : collectedPartsCurrent) {
            root.getChildren().add(sr.getSprite());
        }

        // Spawn Player
        player = new Player(mapLoader.playerX, mapLoader.playerY, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE);
        root.getChildren().add(player);
        root.getChildren().add(player.hpBarBG);
        root.getChildren().add(player.hpBar);
        sceneObjects.add(player);

        // Get key(s) pressed for player movements.
        gameScene.setOnKeyPressed(e -> {
            keys.put(e.getCode(), true);
            if (e.getCode() == KeyCode.ESCAPE) {
                stopTimer();
                keys.clear();
                stage.setScene(Menu.pauseMenu(stage.getScene()));
                musicPlayer.stop();
            }
        });
        gameScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        // Add Background
        levelBG.setX(-100);
        root.getChildren().add(levelBG);
        levelBG.toBack();

        // Add Level Decoration (fog, lava glow)
        if (LEVEL_DECORATION) root.getChildren().add(new ImageView(new Image("/assets/ui/overlays/fog_overlay.png")));

        // Add FPS Display
        if (displayFPS) {
            root.getChildren().add(fpsCounter);
        }

        MainApplication.timer.start();

        currentRoot = root;
        // musicPlayer.play();
        return gameScene;
    }

    /**
     * Main Game Loop
     */
    private static void update() {
        long now = System.currentTimeMillis();
        if (time + MAX_FRAME_RATE <= now) {

            // FPS Display
            if (displayFPS) {

                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true;
                }

                if (arrayFilled) {
                    long elapsedMills = now - oldFrameTime;
                    long elapsedMillsPerFrame = elapsedMills / frameTimes.length;
                    double frameRate = 1000.0 / elapsedMillsPerFrame;
                    fpsCounter.setText(String.format("FPS: %.2f", frameRate));

                    if (frameRate > 70.00 || frameRate < 50.00) {
                        fpsCounter.setTextFill(Color.RED);
                    } else {
                        fpsCounter.setTextFill(Color.GREEN);
                    }

                }

            }

            // Player Update
            player.onUpdate();

            // Animations
            for (Object obj : sceneObjects) {
                if (obj instanceof AnimatedRect) {
                    AnimatedRect animatedRect = (AnimatedRect) obj;
                    animatedRect.onUpdate();
                }
            }

            if (player.getX() < (WINDOW_SIZE_X / 2) - 100) {
                scrollScene();
                player.setX(WINDOW_SIZE_X / 2 - 100);

            } else if (player.getX() > (WINDOW_SIZE_X / 2) + 100) {
                scrollScene();
                player.setX(WINDOW_SIZE_X / 2 + 100);
            }

            for (Enemy enemy : enemies) {
                enemy.onUpdate();
            }

            time = System.currentTimeMillis();

        }

    }

    /**
     * Scrolling
     */
    private static void scrollScene() {
        for (Object obj : sceneObjects) {
            if (obj instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) obj;
                staticRect.setX(staticRect.getX() + player.getVX() * -1);
            } else if (obj instanceof Rectangle) {
                Rectangle rectangle = ((Rectangle) obj);
                rectangle.setX(rectangle.getX() + player.getVX() * -1);
            } else if (obj instanceof Enemy) {
                Enemy enemies = ((Enemy) obj);
                enemies.setX(enemies.getX() + player.getVX() * -1);
            }
        }

        levelBG.setX(levelBG.getX() + player.getVX() * 0.1 * -1);
        distanceScrolled += player.getVX();
    }

    /**
     * Start Game Loop Timer
     */
    static void startTimer() {
        timer.start();
    }

    /**
     * Stop Game Loop Timer
     */
    static void stopTimer() {
        timer.stop();
    }

    /**
     * Load Fonts and stuff and things
     */
    private static void loadResources() {
        installDIR = new File(System.getProperty("user.dir").replace("\\", "/"));
        defaultSaveDIR = new File(installDIR.getPath() + "/saves");
        defaultSaveFile = new File(defaultSaveDIR.getPath() + "/" + defaultSaveName + saveExt);

        if (!defaultSaveDIR.exists()) {
            boolean successful = defaultSaveDIR.mkdirs();
            if (successful) {
                log("No saves directory was located, a new directory was created at \"" + defaultSaveDIR.getPath() + "\".");
            } else {
                log("No saves directory was located, and was unable to be created.", 1);
            }
        }
    }

    static void loadGame(File saveFile) {
        if (saveFile.exists()) {
            try {
                FileInputStream fin = new FileInputStream(saveFile.getPath());
                ObjectInputStream oin = new ObjectInputStream(fin);

                savedGame = (SaveGame) oin.readObject();

                currentLevelIndex = savedGame.getCurrentLevelIndex();
                completedLevels = savedGame.getCompletedLevels();
                levels = savedGame.getLevels();

                oin.close();
                fin.close();

                log("Loaded game save from \"" + saveFile.getPath() + "\".");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log("FileNotFound exception caught loading game. This should be impossible so idk how you managed to break it.", 1);
            } catch (InvalidClassException e) {
                e.printStackTrace();
                log("InvalidClass exception caught loading game. It's possible the game save is invalid, or for a previous version.", 1);
            } catch (IOException e) {
                e.printStackTrace();
                log("IO exception caught while loading game.", 1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                log("ClassNotFoundException exception caught loading game.", 1);
            }
        } else {
            log("No game save was found at saves directory \"" + saveFile.getParentFile() + "\".");
        }

    }

    static void loadGame() {
        loadGame(defaultSaveFile);
    }

    static void saveGame(File saveFile) {
        try {
            FileOutputStream fout = new FileOutputStream(saveFile.getPath());
            ObjectOutputStream oout = new ObjectOutputStream(fout);

            oout.writeObject(new SaveGame(currentLevelIndex, completedLevels, levels));

            oout.close();
            fout.close();

            log("Saved game to \"" + defaultSaveFile + "\".");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log("FileNotFound exception caught saving game.", 1);
        } catch (IOException e) {
            e.printStackTrace();
            log("IO exception caught saving game.", 1);
        }
    }

    static void saveGame() {
        saveGame(defaultSaveFile);
    }

    /**
     * Load levels in /assets/levels into level queue
     */
    static void queueLevels() {

        try {
            File levelsDIR = new File(MainApplication.class.getResource("/assets/levels").getFile());
            File[] files = levelsDIR.listFiles();

            if (files != null) Arrays.sort(files);
            /*Arrays.sort(files, (f0, f1) -> Integer.compare(f0.getPath().length(), f1.getPath().length()));*/

            int i = 0;
            for (File file : files) {
                if (file.getName().matches("^level_\\d{2}$")) {
                    boolean fileExists = false;
                    for (Level level : levels) {
                        if (level.getPath().equals(file.getPath())) fileExists = true;
                    }
                    if (!fileExists) levels.add(new Level("Level " + (i + 1), file.getPath(), file, i));
                    i++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log("Exception caught loading levels. Quitting...", 1);
            System.exit(-1);
        }
    }

    /**
     * Exit Application
     */
    static void exit() {
        // Save something or whatever.
        if (autoSave) saveGame();

        stage.close();
        log("Quitting...");
        System.exit(0);
    }

    // scuffed logger
    public static void log(String msg, int type) {
        SimpleDateFormat df = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss] ");
        if (type == 0) {
            System.out.println(df.format(new Date()) + "INFO: " + msg);
        } else if (type == 1) {
            System.err.println(df.format(new Date()) + "ERROR: " + msg);
        }
    }

    public static void log(String msg) {
        log(msg, 0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

