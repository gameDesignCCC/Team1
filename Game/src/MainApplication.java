import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApplication extends Application {

    //For FPS Display !f
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    // Key(s) pressed for player movement.
    private static boolean up = false;
    private static boolean right = false;
    private static boolean left = false;

    public static double windowSizeX = 600.0;
    public static double windowSizeY = 400.0;

    Player player = new Player(0, windowSizeY - 93, 40, 93);

    //Temporary Player Sprite !t
    Image playerSprite = new Image("/assets/PlayerSprite.png");
    ImageView playerSpriteView = new ImageView(playerSprite);

    // Temporary Map Background !t
    Image mapBg = new Image("/assets/bg.png");
    ImageView mapBgView = new ImageView(mapBg);

    // !f
    Label fpsCounter = new Label();

    // Root and Scene
    Pane root = new Pane();
    Scene scene = new Scene(root, windowSizeX, windowSizeY);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // !t
        playerSpriteView.setX(10);
        playerSpriteView.setY(10);

        // !f
        fpsCounter.setTextFill(Color.GREEN);

        // Get key(s) pressed for player movements.
        // (Move to player class?)

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

        // Timer for game loop. / Should stay at ~60 UPS unless something went wrong which happens often.
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

                    if(frameRate > 65.00 || frameRate < 55.00){
                        fpsCounter.setTextFill(Color.RED);
                    }else {
                        fpsCounter.setTextFill(Color.GREEN);
                    }

                }

            }
        };

        timer.start();

        // !f !t
        root.getChildren().addAll(mapBgView, player, fpsCounter, playerSpriteView);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        // !t
        primaryStage.getIcons().add(new Image("/assets/favicon128.png"));
        primaryStage.setTitle("Placeholder Title");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Game Loop
    void update() {
        player.onUpdate(up, left, right);

        // !t
        playerSpriteView.setX(player.getX());
        playerSpriteView.setY(player.getY());
    }

}
