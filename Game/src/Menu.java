import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Menu {

    public static Scene mainMenu(){

        StackPane root = new StackPane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/style.css");

        Button btnStart = new Button();
        Button btnHelp = new Button();
        Button btnExit = new Button();
        ImageView ivBG = new ImageView(new Image("/assets/ui/main_menu_bg_placeholder.png"));

        btnStart.setText("Start");
        btnHelp.setText("Help");
        btnExit.setText("Exit");

        btnStart.setTranslateY(-80);
        btnHelp.setTranslateY(0);
        btnExit.setTranslateY(80);

        ivBG.setTranslateY(-200);

        btnStart.setOnAction(e ->
            MainApplication.getStage().setScene(MainApplication.getGameScene("./Game/src/assets/levels/level_1"))
        );

        btnHelp.setOnAction(e ->
            MainApplication.getStage().setScene(helpMenu())
        );

        btnExit.setOnAction(e ->
            MainApplication.exit()
        );

        root.getChildren().addAll(ivBG, btnStart, btnHelp, btnExit);

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

        Label lblDied = new Label("You Died");

        lblDied.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 110);
        lblDied.setLayoutY((MainApplication.WINDOW_SIZE_Y / 2) - 100);
        lblDied.setTextFill(Color.RED);
        lblDied.setId("death-screen-label");

        Button btnBack = new Button();

        btnBack.setText("Back");
        btnBack.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 80);
        btnBack.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2);
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));

        root.getChildren().addAll(lblDied, btnBack);

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
