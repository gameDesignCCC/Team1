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
        Button btnHelp = new Button();
        Button btnExit = new Button();
        ImageView title = new ImageView(new Image("/assets/ui/pTitleArt.png"));

        btnStart.setText("Start");
        btnHelp.setText("Help");
        btnExit.setText("Exit");

        btnStart.setTranslateY(-80);
        btnExit.setTranslateY(80);

        btnStart.setOnAction(e ->
            MainApplication.getStage().setScene(MainApplication.getGameScene("./Game/src/assets/levels/level-1"))
        );

        btnHelp.setOnAction(e ->
            MainApplication.getStage().setScene(helpMenu())
        );

        btnExit.setOnAction(e ->
            MainApplication.exit()
        );

        title.setTranslateY(-200);

        root.getChildren().addAll(title, btnStart, btnHelp, btnExit);

        return scene;

    }

    public static Scene helpMenu(){

        Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
        scene.getStylesheets().add("/assets/ui/style.css");

        Text txt = new Text("placeholder text");
        txt.setFill(Color.WHITE);
        txt.setY(MainApplication.WINDOW_SIZE_Y / 2 - 10);
        txt.setX(MainApplication.WINDOW_SIZE_X / 2 - 100);

        root.getChildren().add(txt);

        return scene;
    }

    public static Scene deathMenu(){

        Pane root = new Pane();

        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
        scene.getStylesheets().add("/assets/ui/style.css");

        Label label = new Label("You Died");
        label.setLayoutX(MainApplication.WINDOW_SIZE_X / 2 - 110);
        label.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - 100);
        label.setTextFill(Color.RED);
        label.setId("deathscreen-label");

        Button btn = new Button();
        btn.setText("Back");
        btn.setId("mainMenuBtn");
        btn.setLayoutX(MainApplication.WINDOW_SIZE_X/2 - 80);
        btn.setLayoutY(MainApplication.WINDOW_SIZE_Y/2);
        btn.setOnAction(e -> MainApplication.getStage().setScene(Menu.menu()));

        root.getChildren().addAll(label, btn);

        return scene;

    }

    public static Scene pauseMenu(){

        Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/style.css");

        Button btnResume = new Button();
        Button btnBack = new Button();
        Button btnHelp = new Button();

        btnResume.setText("Resume");
        btnResume.setLayoutX(MainApplication.WINDOW_SIZE_X / 2 - 80);
        btnResume.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - 30);
        btnBack.setText("Back To Menu");
        btnBack.setLayoutX(MainApplication.WINDOW_SIZE_X / 2 - 80);
        btnBack.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - 30);
        btnHelp.setText("Help");
        btnHelp.setLayoutX(MainApplication.WINDOW_SIZE_X / 2 - 80);
        btnHelp.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - 30);
        root.getChildren().addAll(btnResume, btnBack, btnHelp);

        return scene;

    }

}
