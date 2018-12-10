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
    private static double currentStageLeft;


    private static final double MAX_FRAME_RATE = 16.67;
    private static long time = System.currentTimeMillis();

    //For FPS Display
    private static final long[] frameTimes = new long[100];
    private static int frameTimeIndex = 0;
    private static boolean arrayFilled = false;
    private static Label fpsCounter = new Label();
    private static final boolean DISPLAY_FPS = true;

    // Key(s) pressed for player movement. I don't know if this is better than using separate booleans, I just wanted to try this out.
    private static HashMap<KeyCode, Boolean> keys;

    public static final double WINDOW_SIZE_X = 1280.0;
    public static final double WINDOW_SIZE_Y = 720.0;

    // Temporary Player Sprites
    private static Image playerSprite = new Image("/assets/sprites/player/player_placeholder.png");

    // The Player
    public static Player player;


    // Temporary Map Background
    private static Image mapBg = new Image("/assets/levels/level_bg.png");
    private static ImageView mapBgView = new ImageView(mapBg);

    // timer
    private static AnimationTimer timer;


    // Map Objects - added in StaticRect constructor.
    public static ArrayList<Enemies> enemies;

    // Temporary for collision detection.
    private static Enemies enemy;

    public static ArrayList<Object> sceneObjects = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

       loadResources();

        stage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(e -> exit());


        primaryStage.getIcons().add(new Image("/assets/application/favicon_placeholder128.png"));
        primaryStage.setTitle("Placeholder Title");

        // Load Main Menu
        primaryStage.setScene(Menu.mainMenu());
        primaryStage.show();

    }

    private static boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }


    public static Stage getStage(){
        return stage;
    }


    public static Scene getGameScene(String level) {

        //Reset Scene
        Pane root = new Pane();
        Scene gameScene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);
        gameScene.getStylesheets().add("/assets/ui/style.css");
        sceneObjects = new ArrayList<>();
        keys = new HashMap<>();
        currentStageLeft = 0.0;

        //Load Next Level
        MapLoader mapLoader = new MapLoader();
        for( Object obj: mapLoader.load(level)) {
            if ( obj instanceof StaticObject ) {
                StaticObject staticObject = (StaticObject) obj;
                sceneObjects.add(staticObject);
                root.getChildren().add(staticObject.getSprite());
            }
        }

        // Update Player Location:
        player = new Player(mapLoader.playerX, mapLoader.playerY, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE, playerSprite);
        sceneObjects.add(player);
        root.getChildren().add(player);
        root.getChildren().add(player.hpBar);

        // Update Enemy location
        // TMP
        enemy = new Enemies(500, WINDOW_SIZE_Y - 180, 30, 30,
                new Image("/assets/sprites/enemies/enemy_placeholder.png"));
        
        enemies = new ArrayList<>();
        enemies.add(enemy);
        for ( Enemies e : enemies ) {
            root.getChildren().add(e);
            root.getChildren().add(e.hpBar);
        }


        // Get key(s) pressed for player movements.
        gameScene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
        gameScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        // Add Background
        root.getChildren().add(mapBgView);
        mapBgView.toBack();

        // Add FPS Counter
        if ( DISPLAY_FPS ) {
            root.getChildren().add(fpsCounter);
        }

        // Timer for game loop. / Should stay at ~60 UPS unless something went wrong, which happens often.
        MainApplication.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                update();

            }
        };

        MainApplication.timer.start();

        return gameScene;
    }

    /**
     * Main Game Loop
     */
    private static void update() {
        long now = System.currentTimeMillis();
        if (time + MAX_FRAME_RATE <= now ) {

            player.onUpdate(isPressed(KeyCode.UP), isPressed(KeyCode.LEFT), isPressed(KeyCode.RIGHT));
            enemy.onUpdate();
            if (isPressed(KeyCode.ESCAPE)) {
                MainApplication.getStage().setScene(Menu.pauseMenu());
                MainApplication.timer.stop();
            }

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

            time = System.currentTimeMillis();

        }

        if(player.getX() < (WINDOW_SIZE_X / 2) - 100) {
            for (Object obj : sceneObjects) {
                if (obj instanceof StaticRect) {
                    StaticRect staticRect = (StaticRect) obj;
                    staticRect.setX(staticRect.getX() + player.getVX() * -1);
                }else if(obj instanceof Enemies){
                    Enemies enemies = ((Enemies) obj);
                    enemies.setX(enemies.getX() + player.getVX() * -1);
                }
            }
            player.setX(WINDOW_SIZE_X / 2 - 100);

        }else if(player.getX() > (WINDOW_SIZE_X / 2) + 100){
            for (Object obj : sceneObjects) {
                if (obj instanceof StaticRect) {
                    StaticRect staticRect = (StaticRect) obj;
                    staticRect.setX(staticRect.getX() + player.getVX() * -1);
                }else if(obj instanceof Enemies){
                    Enemies enemies = ((Enemies) obj);
                    enemies.setX(enemies.getX() + player.getVX() * -1);
                }
            }
            player.setX(WINDOW_SIZE_X / 2 + 100);
        }

    }

    public static void loadResources(){
        // https://fonts.google.com/specimen/Russo+One
        Font.loadFont(MainApplication.class.getResource("/assets/ui/font_russo_one_regular.ttf").toExternalForm(), 10);
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

