import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {

    public static Scene menu(){

        StackPane root = new StackPane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/style.css");

        Button btnStart = new Button();
        Button btnOptions = new Button();
        Button btnExit = new Button();
        ImageView title = new ImageView(new Image("/assets/ui/pTitleArt.png"));

        btnStart.setText("Start");
        btnOptions.setText("Options");
        btnExit.setText("Exit");

        btnStart.setTranslateY(-80);
        btnExit.setTranslateY(80);


        btnStart.setOnAction(e ->
            MainApplication.getStage().setScene(MainApplication.getGameScene())
        );

        btnOptions.setOnAction(e ->
            MainApplication.getStage().setScene(optionsMenu())
        );

        btnExit.setOnAction(e ->
            MainApplication.exit()
        );

        title.setTranslateY(-200);

        root.getChildren().addAll(title, btnStart, btnOptions, btnExit);

        return scene;

    }

    public static Scene optionsMenu(){

        Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
        scene.getStylesheets().add("/assets/ui/style.css");
        return scene;
    }

    public static Scene deathMenu(){

        Pane root = new Pane();

        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
        scene.getStylesheets().add("/assets/ui/style.css");

        Label label = new Label("YOU DIED");
        label.setLayoutX(MainApplication.WINDOW_SIZE_X / 2 - 100);
        label.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - 100);
        label.setTextFill(Color.RED);
        label.setFont(Font.font("Arial", 50));

        Button btn = new Button();
        btn.setText("Back");
        btn.setId("mainMenuBtn");
        btn.setLayoutX(MainApplication.WINDOW_SIZE_X/2);
        btn.setLayoutY(MainApplication.WINDOW_SIZE_Y/2);
        btn.setOnAction(e -> MainApplication.getStage().setScene(Menu.menu()));

        root.getChildren().addAll(label, btn);

        return scene;

    }

}
