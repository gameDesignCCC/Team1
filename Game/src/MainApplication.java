/*
 * Name: CCC 2018 Platformer Game (Fork)
 * Date: 6/10/2018 - 13/10/2018
 * Team: Advanced Game Development Team 1
 * Author(s):
 * Repo: https://github.com/JacobDixon0/Team1
 * Original Repo: https://github.com/gameDesignCCC/Team1
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

public class MainApplication extends Application {

    Stage stage;

    //For FPS Display
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    // Key(s) pressed for player movement.
    private static boolean up = false;
    private static boolean right = false;
    private static boolean left = false;

    public static final double WINDOW_SIZE_X = 1280.0;
    public static final double WINDOW_SIZE_Y = 720.0;

    //Temporary Player Sprites
    public Image playerSprite = new Image("/assets/playerSprite.png");
    public Image pPlayerSprite = new Image("/assets/placeholderPlayerSprite.png");

    public Player player = new Player(0, WINDOW_SIZE_Y - pPlayerSprite.getHeight(), pPlayerSprite.getWidth(), pPlayerSprite.getHeight(), pPlayerSprite );

    // Temporary Map Background
    public Image mapBg = new Image("/assets/bg.png");
    public ImageView mapBgView = new ImageView(mapBg);

    public Label fpsCounter = new Label();

    // Root and Scene
    private Pane root = new Pane();
    private Scene scene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);

    // Temporary for collision detection.
    static Rectangle box = new Rectangle(500, WINDOW_SIZE_Y - 100, 100, 100);

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        // Temporary for testing collision detection.
        box.setFill(Color.RED);

        // Get key(s) pressed for player movements.

        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();

            if (key == KeyCode.RIGHT) {
                right = true;
            } else if (key == KeyCode.LEFT) {
                left = true;
            } else if (key == KeyCode.UP) {
                up = true;
            }

        });

        scene.setOnKeyReleased(e -> {
            KeyCode key = e.getCode();

            if (key == KeyCode.RIGHT) {
                right = false;
            } else if (key == KeyCode.LEFT) {
                left = false;
            } else if (key == KeyCode.UP) {
                up = false;
            }

        });

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

        root.getChildren().addAll(mapBgView, fpsCounter, player, box);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(e -> exit());

        primaryStage.getIcons().add(new Image("/assets/favicon128.png"));
        primaryStage.setTitle("Placeholder Title");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void addToRoot(Node node){
        root.getChildren().add(node);
    }

    /**
     * Main Game Loop
     */
    private void update() {
        player.onUpdate(up, left, right);

    }

    /**
     * Exit Application
     */
    public void exit(){
        // Save something or whatever.
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
