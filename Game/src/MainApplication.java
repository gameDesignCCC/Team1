/*
 * Name: CCC 2018 Platformer Game
 * Date: 6/10/2018 - 20/10/2018
 * Team: Advanced Game Development Team 1
 * Author(s):
 * Repo: https://github.com/gameDesignCCC/Team1
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class MainApplication extends Application {

    private static Stage stage;

    private static final double MAX_FRAME_RATE = 16.67;
    private static long time = System.currentTimeMillis();

    //For FPS Display
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    // Key(s) pressed for player movement. I don't know if this is better than using separate booleans, I just wanted to try this out.
    private static HashMap<KeyCode, Boolean> keys = new HashMap<>();

    public static final double WINDOW_SIZE_X = 1280.0;
    public static final double WINDOW_SIZE_Y = 720.0;

    // Temporary Player Sprites
    private static Image playerSprite = new Image("/assets/sprites/playerSprite.png");
    private static Image pPlayerSprite = new Image("/assets/sprites/pPlayerSprite.png");

    // The Player
    public static Player player = new Player(0, WINDOW_SIZE_Y - pPlayerSprite.getHeight(), pPlayerSprite.getWidth(), pPlayerSprite.getHeight(), pPlayerSprite );

    // Temporary Map Background
    private static Image mapBg = new Image("/assets/maps/00/bg.png");
    private static ImageView mapBgView = new ImageView(mapBg);

    private static Label fpsCounter = new Label();

    // Root and Scene
    private static Pane root = new Pane();
    private static Scene gameScene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);

    // Map Objects - added in StaticRect constructor.
    public static ArrayList<Object> sceneObjects = new ArrayList<>();

    // Game Loop Timer
    private static AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        gameScene.getStylesheets().add("/assets/ui/style.css");

        // Get key(s) pressed for player movements.
        gameScene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
        gameScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        // Timer for game loop. / Should stay at ~60 UPS unless something went wrong, which happens often.
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                update();

            }
        };

        timer.start();

        root.getChildren().addAll(mapBgView, fpsCounter, player);

        mapBgView.toBack();

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(e -> exit());


        primaryStage.getIcons().add(new Image("/assets/application/favicon128.png"));
        primaryStage.setTitle("Placeholder Title");

        // Load Main Menu
        primaryStage.setScene(Menu.menu());
        primaryStage.show();

    }

    private static boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }


    public static Stage getStage(){
        return stage;
    }

    public static Scene getGameScene(String level) {

        //Remove Nodes From Scene
        Pane root = new Pane();
        Scene gameScene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);
        sceneObjects = new ArrayList<>();

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
        player = new Player(mapLoader.playerX, mapLoader.playerY, MapLoader.GRID_SIZE, MapLoader.GRID_SIZE, pPlayerSprite );
        sceneObjects.add(player);
        root.getChildren().add(player);

        gameScene.getStylesheets().add("/assets/ui/style.css");

        // Get key(s) pressed for player movements.
        gameScene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
        gameScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        root.getChildren().addAll(mapBgView, fpsCounter);
        mapBgView.toBack();

        return gameScene;
    }

    public static Pane getGameRoot(){
        return root;
    }

    /**
     * Main Game Loop
     */
    private void update() {

        long now = System.currentTimeMillis();
        if (time + MAX_FRAME_RATE <= now ) {

            player.onUpdate(isPressed(KeyCode.UP), isPressed(KeyCode.LEFT), isPressed(KeyCode.RIGHT));
            if (isPressed(KeyCode.ESCAPE)) {
                exit();
            }

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
                double frameRate = 1000 / elapsedMillsPerFrame;
                fpsCounter.setText(String.format("UPS: %.2f", frameRate));

                if(frameRate > 70.00 || frameRate < 50.00){
                    fpsCounter.setTextFill(Color.RED);
                }else {
                    fpsCounter.setTextFill(Color.GREEN);
                }

            }


            time = System.currentTimeMillis();


        }

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
