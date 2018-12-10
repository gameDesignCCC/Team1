import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Menu {

    @SuppressWarnings("Duplicates")
    public static Scene mainMenu() {

        StackPane root = new StackPane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/style.css");

        ImageView ivBG = new ImageView(new Image("/assets/ui/main_menu_bg_placeholder.png"));

        Button btnStart = new Button();
        Button btnHelp = new Button();
        Button btnExit = new Button();


        btnStart.setText("Start");
        btnStart.setTranslateY(-80);
        btnStart.setOnAction(e -> MainApplication.getStage().setScene(MainApplication.getGameScene("./Game/src/assets/levels/level_1")));

        btnHelp.setText("Help");
        btnHelp.setTranslateY(0);

        btnExit.setText("Exit");
        btnExit.setTranslateY(80);

        ivBG.setTranslateY(-200);

        btnHelp.setOnAction(e -> MainApplication.getStage().setScene(helpMenu(scene)));
        btnExit.setOnAction(e -> MainApplication.exit());

        root.getChildren().addAll(ivBG, btnStart, btnHelp, btnExit);

        return scene;
    }

    @SuppressWarnings("Duplicates")
    public static Scene helpMenu(Scene prevScene) {

        Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/style.css");

        Button btnBack = new Button();

        btnBack.setText("Back");
        btnBack.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 80);
        btnBack.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - 30);
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(prevScene));

        root.getChildren().add(btnBack);

        return scene;
    }

    @SuppressWarnings("Duplicates")
    public static Scene deathMenu() {

        Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/style.css");

        Label lblHeader = new Label("You Died");

        lblHeader.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 110);
        lblHeader.setLayoutY((MainApplication.WINDOW_SIZE_Y / 2) - 130);
        lblHeader.setTextFill(Color.RED);
        lblHeader.setId("death-screen-header");

        Button btnBack = new Button();

        btnBack.setText("Back");
        btnBack.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 80);
        btnBack.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - 30);
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));

        root.getChildren().addAll(lblHeader, btnBack);

        return scene;
    }

    @SuppressWarnings("Duplicates")
    public static Scene pauseMenu(Scene prevScene) {

        VBox root = new VBox();

        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/style.css");

        Label lblHeader = new Label();

        lblHeader.setText("Paused");
        lblHeader.setPadding(new Insets(0, 0, 20, 0));
        lblHeader.setId("menu-header");

        Button btnResume = new Button();
        Button btnRestart = new Button();
        Button btnBack = new Button();
        Button btnHelp = new Button();

        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(20);

        root.setAlignment(Pos.CENTER);

        btnResume.setText("Resume");
        btnResume.setOnAction(e -> {
            MainApplication.getStage().setScene(prevScene);
            MainApplication.startTimer();
        });
        btnResume.setId("button-wide");

        btnRestart.setText("Restart");
        btnRestart.setOnAction(e -> {
            MainApplication.getStage().setScene(MainApplication.getGameScene("./Game/src/assets/levels/level_1"));
        });
        btnRestart.setId("button-wide");

        btnHelp.setText("Help");
        btnHelp.setOnAction(e -> MainApplication.getStage().setScene(helpMenu(scene)));
        btnHelp.setId("button-wide");

        btnBack.setText("Main Menu");
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));
        btnBack.setId("button-wide");

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                MainApplication.getStage().setScene(mainMenu());
            }
        });

        root.getChildren().addAll(lblHeader, btnResume, btnRestart, btnHelp, btnBack);

        return scene;
    }

}
