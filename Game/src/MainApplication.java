/*
 * Name: CCC 2018 Platformer Game (Fork)
 * Date: 6/10/2018 - 10/10/2018
 * Team: Advanced Game Development Team 1
 * Author(s):
 * Repo: https://github.com/JacobDixon0/Team1
 * Original Repo: https://github.com/gameDesignCCC/Team1
 */

// Everything may be broken, but at least it's not as broken as before.
// Probably going to end up re-writing all of this. - JD


import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

    //For FPS Display !f
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    // Key(s) pressed for player movement.
    private static boolean up = false;
    private static boolean right = false;
    private static boolean left = false;

    public static final double WINDOW_SIZE_X = 1280.0;
    public static final double WINDOW_SIZE_Y = 720.0;

    public final Collision c = new Collision();

    //Temporary Player Sprite
    Image playerSprite = new Image("/assets/playerSprite.png");
    Image PplayerSprite = new Image("/assets/PlaceholderPlayerSprite.png");

    Player player = new Player(700, WINDOW_SIZE_Y - PplayerSprite.getHeight(), PplayerSprite.getWidth(), PplayerSprite.getHeight(), PplayerSprite );

    // Temporary Map Background
    Image mapBg = new Image("/assets/bg.png");
    ImageView mapBgView = new ImageView(mapBg);

    // !f
    Label fpsCounter = new Label();

    // Root and Scene
    Pane root = new Pane();
    Scene scene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);

    static Rectangle box = new Rectangle(500, WINDOW_SIZE_Y -100, 100, 100);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        box.setFill(Color.RED);

        // !f
        fpsCounter.setTextFill(Color.GREEN);

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

                // FPS Display !f

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

        // !f !t
        root.getChildren().addAll(mapBgView, fpsCounter, player, box);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(e -> exit());

        // !t
        primaryStage.getIcons().add(new Image("/assets/favicon128.png"));
        primaryStage.setTitle("Placeholder Title");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Main Game Loop
     */
    void update() {
        player.onUpdate(up, left, right);
        System.out.println(c.isCollidingRight(box,player) + " " + c.isCollidingLeft(box,player));
    }

    /**
     * TODO Add descriptions
     */
    public void exit(){
        // Save something or whatever.
        stage.close();
    }

}
