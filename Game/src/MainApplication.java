import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private static boolean up = false;
    private static boolean right = false;
    private static boolean left = false;

    public static double windowSizeX = 600.0;
    public static double windowSizeY = 400.0;

    Player player = new Player(0, windowSizeY - 100, 100, 100);

    public static void main ( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root, windowSizeX, windowSizeY);

        scene.setOnKeyPressed(e ->{
            KeyCode key = e.getCode();

            if(key == KeyCode.RIGHT){
                right = true;
            }else if(key == KeyCode.LEFT){
                left = true;
            }else if(key == KeyCode.UP){
                up = true;
            }

        });

        scene.setOnKeyReleased(e ->{
            KeyCode key = e.getCode();

            if(key == KeyCode.RIGHT){
                right = false;
            }else if(key == KeyCode.LEFT){
                left = false;
            }else if(key == KeyCode.UP){
                up = false;
            }

        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        root.getChildren().add(player);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.setTitle("Title");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    void update(){

        // Player Controls
        if(right){
            if(!player.isCollidingRight()){
                player.moveRight();
            }
        }
        if (left){
            if(!player.isCollidingLeft()) {
                player.moveLeft();
            }
        }
        if(up){
            if(!player.isCollidingTop()) {
                player.jump();
            }
        }

        player.enforceGravity(true);

        System.out.println(player.g + " " + player.getVelocityY());

    }

}
