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
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

import java.util.HashMap;

public class MainApplication extends Application {

    private static Stage stage;

    //For FPS Display
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    // Key(s) pressed for player movement. I don't know if this is better than using separate booleans, I just wanted to try this out.
    private static HashMap<KeyCode, Boolean> keys = new HashMap<>();

    public static final double WINDOW_SIZE_X = 1280.0;
    public static final double WINDOW_SIZE_Y = 720.0;

    //Temporary Player Sprites
    private Image playerSprite = new Image("/assets/sprites/playerSprite.png");
    private Image pPlayerSprite = new Image("/assets/sprites/pPlayerSprite.png");

    public Player player = new Player(0, WINDOW_SIZE_Y - pPlayerSprite.getHeight(), pPlayerSprite.getWidth(), pPlayerSprite.getHeight(), pPlayerSprite );

    // Temporary Map Background
    private Image mapBg = new Image("/assets/maps/00/bg.png");
    private ImageView mapBgView = new ImageView(mapBg);

    private static Label fpsCounter = new Label();

    // Root and Scene
    private static Pane root = new Pane();
    private static Scene gameScene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);

    // Temporary for collision detection.
    static StaticRect box2 = new StaticRect(500, WINDOW_SIZE_Y - 100, 100, 100, new Image("/assets/sprites/pPlayerSprite.png"));
    static Rectangle box = new Rectangle(700, WINDOW_SIZE_Y - 500, 100, 100);

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        gameScene.getStylesheets().add("/assets/ui/style.css");

        // Get key(s) pressed for player movements.
        gameScene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
        gameScene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        // Timer for game loop. / Should stay at ~60 UPS unless something went wrong, which happens often.
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();

                // FPS Display

                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true;
                }

                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1000000000.0 / elapsedNanosPerFrame;
                    fpsCounter.setText(String.format("UPS: %.2f", frameRate));

                    if(frameRate > 70.00 || frameRate < 50.00){
                        fpsCounter.setTextFill(Color.RED);
                    }else {
                        fpsCounter.setTextFill(Color.GREEN);
                    }

                }

            }
        };

        timer.start();

        root.getChildren().addAll(mapBgView, fpsCounter, player);
        box2.toFront();

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

    public static void addToRoot(Node node){
        root.getChildren().add(node);
    }

    public static void rmFromRoot(Node node){
        root.getChildren().remove(node);
    }

    public static void toFront(Node node){
        node.toFront();
    }


    public static Stage getStage(){
        return stage;
    }

    public static Scene getGameScene(){
        return gameScene;
    }

    /**
     * Main Game Loop
     */
    private void update() {
        player.onUpdate(isPressed(KeyCode.UP), isPressed(KeyCode.LEFT), isPressed(KeyCode.RIGHT));
        if(isPressed(KeyCode.ESCAPE)){
            exit();
        }

        System.out.println(box2.checkCollision(player));

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
