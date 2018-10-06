import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static void main ( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();

        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();

    }
}
