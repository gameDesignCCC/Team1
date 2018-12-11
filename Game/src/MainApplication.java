/*
 * Name: CCC 2018 Platformer Game
 * Date: 6/10/2018 - 20/10/2018
 * Team: Advanced Game Development Team 1
 * Author(s):
 * Repo: https://github.com/gameDesignCCC/Team1
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class MainApplication extends Application {

    private static Stage stage;

    private static final double MAX_FRAME_RATE = 16.67;
    private static long time = System.currentTimeMillis();

    //For FPS Display
    private static final long[] frameTimes = new long[100];
    private static int frameTimeIndex = 0;
    private static boolean arrayFilled = false;
    private static Label fpsCounter = new Label();
    private static final boolean DISPLAY_FPS = true;

    // Key(s) pressed for player movement.
    private static HashMap<KeyCode, Boolean> keys;

    // Stage Size
    public static final double WINDOW_SIZE_X = 1280.0;
    public static final double WINDOW_SIZE_Y = 720.0;

    // Temporary Player Sprites
    private static Image playerSprite = new Image("/assets/sprites_textures/player/player_placeholder.png");

    // The Player
    public static Player player;

    // Placeholder Map Background
    private static ImageView levelBG = new ImageView("/assets/levels/backgrounds/level_bg.png");

    // Game Loop Timer
    private static AnimationTimer timer;

    // Map Objects
    public static ArrayList<Object> sceneObjects = new ArrayList<>();

    // Enemy
    public static ArrayList<Enemy> enemies;

    // Current Level
    public static String currentLevel = "./Game/src/assets/levels/level_1";

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadResources();

        stage = primaryStage;

        stage.getIcons().add(new Image("/assets/application/favicon_placeholder128.png"));
        stage.setTitle("Placeholder Title");
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> exit());
        stage.setScene(Menu.mainMenu()); // Load Main Menu
        stage.show();

    }

    public static boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    public static Stage getStage(){
        return stage;
    }

    public static Scene getGameScene(String level) {
        // Reset Scene

        Pane root = new Pane();
        Scene gameScene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);

        gameScene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        keys = new HashMap<>();
        sceneObjects = new ArrayList<>();
        enemies = new ArrayList<>();

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

            } else if (obj instanceof Enemy) {
                Enemy enemy = ((Enemy) obj);

                sceneObjects.add(enemy);
                root.getChildren().addAll(enemy, enemy.hpBar);
            }
        }

        // Spawn Player
        player = new Player(mapLoader.playerX, mapLoader.playerY, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE, playerSprite);
        root.getChildren().add(player);
        root.getChildren().add(player.hpBarBG);
        root.getChildren().add(player.hpBar);
        sceneObjects.add(player);

        // Get key(s) pressed for player movements.
        gameScene.setOnKeyPressed(e -> {
            keys.put(e.getCode(), true);
            if(e.getCode() == KeyCode.ESCAPE){
                stopTimer();
                keys.clear();
                stage.setScene(Menu.pauseMenuTransparent(stage.getScene()));
            }
        });
        gameScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        // Add Background
        root.getChildren().add(levelBG);
        levelBG.toBack();

        // Add FPS Counter
        if ( DISPLAY_FPS ) {
            root.getChildren().add(fpsCounter);
        }

        MainApplication.timer.start();

        return gameScene;
    }

    /**
     * Main Game Loop
     */
    private static void update() {
        long now = System.currentTimeMillis();
        if (time + MAX_FRAME_RATE <= now ) {

            if (DISPLAY_FPS) {

                // FPS Display
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


            if(player.getX() < (WINDOW_SIZE_X / 2) - 100) {
                scrollScene();
                player.setX(WINDOW_SIZE_X / 2 - 100);

            }else if(player.getX() > (WINDOW_SIZE_X / 2) + 100){
                scrollScene();
                player.setX(WINDOW_SIZE_X / 2 + 100);
            }

            player.onUpdate();

            for(Enemy enemy : enemies){
                enemy.onUpdate();
            }

            time = System.currentTimeMillis();

        }

    }

    public static void startTimer(){
        timer.start();
    }

    public static void stopTimer(){
        timer.stop();
    }

    private static void scrollScene(){
        for (Object obj : sceneObjects) {
            if (obj instanceof StaticRect) {
                StaticRect staticRect = (StaticRect) obj;
                staticRect.setX(staticRect.getX() + player.getVX() * -1);
            }else if(obj instanceof Enemy){
                Enemy enemies = ((Enemy) obj);
                enemies.setX(enemies.getX() + player.getVX() * -1);
            }
        }
    }

    public static void loadResources(){
        // https://fonts.google.com/specimen/Russo+One
        Font.loadFont(MainApplication.class.getResource("/assets/ui/fonts/font_russo_one_regular.ttf").toExternalForm(), 10);
    }

    /**
     * Exit Application
     */

    public static void exit(){

        // Save something or whatever.
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

