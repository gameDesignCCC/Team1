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

    Player player = new Player(0, windowSizeY - 100, 100, 100);
    
    Image playerSprite = new Image("/assets/playerSprite.png");
    ImageView playerSpriteView = new ImageView(playerSprite);

    // !f
    Label fpsCounter = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root, windowSizeX, windowSizeY);

        playerSpriteView.setX(10);
        playerSpriteView.setY(10);

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

        // Timer for game loop. / Should stay at ~60 UPS unless something went wrong (which can and has happened).
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

                }

            }
        };

        timer.start();

        // !f
        root.getChildren().addAll(player, fpsCounter, playerSpriteView);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.getIcons().add(new Image("/assets/favicon128.png"));
        primaryStage.setTitle("Placeholder Title");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Game Loop
    void update() {
        player.onUpdate(up, left, right);
        playerSpriteView.setX(player.getX());
        playerSpriteView.setY(player.getY());
    }

}
