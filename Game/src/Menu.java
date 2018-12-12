import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class Menu {

    @SuppressWarnings("Duplicates")
    public static Scene mainMenu() {

        StackPane root = new StackPane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        ImageView ivBG = new ImageView(new Image("/assets/ui/menus/backgrounds/alt_main_menu_bg_placeholder.png"));

        Button btnStart = new Button();
        Button btnHelp = new Button();
        Button btnExit = new Button();

        btnStart.setText("Start");
        btnStart.setTranslateY(-80);
        btnStart.setOnAction(e -> MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.currentLevel)));

        btnHelp.setText("Help");
        btnHelp.setTranslateY(0);
        btnHelp.setOnAction(e -> MainApplication.getStage().setScene(helpMenu(scene)));

        btnExit.setText("Exit");
        btnExit.setTranslateY(80);
        btnExit.setOnAction(e -> MainApplication.exit());

        root.getChildren().addAll(ivBG, btnStart, btnHelp, btnExit);

        return scene;
    }

    @SuppressWarnings("Duplicates")
    public static Scene helpMenu(Scene prevScene) {

        Parent root;

        try {
            root = FXMLLoader.load(Menu.class.getResource("/assets/ui/menus/help_menu.fxml"));
            Controller.prevScene = prevScene;
            Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
            scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

            return scene;
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    @SuppressWarnings("Duplicates")
    public static Scene deathMenu() {

        try {
            Parent root = FXMLLoader.load(Menu.class.getResource("/assets/ui/menus/death_menu.fxml"));
            Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
            scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;

        /*Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

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

        return scene;*/
    }

    @SuppressWarnings("Duplicates")
    public static Scene pauseMenu(Scene prevScene) {

        VBox root = new VBox();

        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

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
        btnRestart.setOnAction(e -> MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.currentLevel)));
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

    @SuppressWarnings("Duplicates")
    public static Scene pauseMenuTransparent(Scene prevScene) {

        Pane root = new Pane();
        VBox subRoot = new VBox();

        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        // Note: It might be better to just add the menu elements to the already existing game scene and remove them afterwards.
        for (Node node : prevScene.getRoot().getChildrenUnmodifiable()) {
            if(node instanceof ImageView){
                ImageView iv = ((ImageView) node);
                ImageView imageView = new ImageView(iv.getImage());
                imageView.setX(iv.getX());
                imageView.setY(iv.getY());
                imageView.setFitWidth(iv.getFitWidth());
                imageView.setFitHeight(iv.getFitHeight());
                root.getChildren().add(imageView);
            }else if(node instanceof Rectangle){
                Rectangle rect = ((Rectangle) node);
                Rectangle rectangle = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                rectangle.setFill(rect.getFill());
                rectangle.setEffect(rect.getEffect());
                root.getChildren().add(rectangle);
            }
        }

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        /*dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);*/

        Rectangle rectBG = new Rectangle(0, 0, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        rectBG.setFill(Color.color(0.0, 0.0, 0.0, 0.5));

        Label lblHeader = new Label();

        lblHeader.setText("Paused");
        lblHeader.setPadding(new Insets(0, 0, 20, 0));
        lblHeader.setId("menu-header");
        lblHeader.setEffect(dropShadow);

        Button btnResume = new Button();
        Button btnRestart = new Button();
        Button btnBack = new Button();
        Button btnHelp = new Button();

        /*subRoot.setPadding(new Insets(20, 20, 20, 20));*/
        subRoot.setSpacing(20);

        subRoot.setAlignment(Pos.CENTER);

        btnResume.setText("Resume");
        btnResume.setOnAction(e -> {
            MainApplication.getStage().setScene(prevScene);
            MainApplication.startTimer();
        });
        btnResume.setId("button-wide");
        btnResume.setEffect(dropShadow);

        btnRestart.setText("Restart");
        btnRestart.setOnAction(e -> MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.currentLevel)));
        btnRestart.setId("button-wide");
        btnRestart.setEffect(dropShadow);

        btnHelp.setText("Help");
        btnHelp.setOnAction(e -> MainApplication.getStage().setScene(helpMenu(scene)));
        btnHelp.setId("button-wide");
        btnHelp.setEffect(dropShadow );

        btnBack.setText("Main Menu");
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));
        btnBack.setId("button-wide");
        btnBack.setEffect(dropShadow);

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                MainApplication.getStage().setScene(prevScene);
                MainApplication.startTimer();
            }
        });

        subRoot.getChildren().addAll(lblHeader, btnResume, btnRestart, btnHelp, btnBack);
        subRoot.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 100);
        subRoot.setLayoutY((MainApplication.WINDOW_SIZE_Y / 2) - 250);

        root.getChildren().add(rectBG);
        root.getChildren().add(subRoot);

        return scene;
    }

}
