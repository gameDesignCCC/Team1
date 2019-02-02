import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

public class Menu {

    private static DropShadow dropShadow = new DropShadow();

    public static void onStart(){
        dropShadow.setRadius(20.0);
    }

    /*
     *     -- SCENES --
     */

    public static Scene mainMenu() {

        StackPane root = new StackPane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        ImageView ivBG = new ImageView(new Image("/assets/ui/backgrounds/alt_main_menu_bg_placeholder.png"));

        Button btnStart = new Button();
        Button btnHelp = btnHelp(scene, false);
        Button btnExit = new Button();

        btnStart.setText("Start");
        btnStart.setTranslateY(-80);
        btnStart.setOnAction(e -> MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levelQueue.peek())));

        btnExit.setText("Exit");
        btnExit.setTranslateY(80);
        btnExit.setOnAction(e -> MainApplication.exit());

        root.getChildren().addAll(ivBG, btnStart, btnHelp, btnExit);

        return scene;
    }

    public static Scene helpMenu(Scene prevScene) {

        Pane root = new Pane();
        VBox subRoot = new VBox();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(prevScene));

        subRoot.setAlignment(Pos.CENTER);
        subRoot.setPrefWidth(MainApplication.WINDOW_SIZE_X);
        subRoot.setPrefHeight(MainApplication.WINDOW_SIZE_Y);
        subRoot.getChildren().add(btnBack);

        root.getChildren().add(subRoot);

        return scene;
    }

    public static Scene deathMenu() {

        Pane root = new Pane();
        VBox subRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Label lblHeader = menuHeader("You Died");
        lblHeader.setId("death-screen-header");

        Button btnRestart = btnRestart();
        Button btnMainMenu = btnMainMenu();

        subRoot.setAlignment(Pos.CENTER);
        subRoot.setPrefWidth(MainApplication.WINDOW_SIZE_X);
        subRoot.setPrefHeight(MainApplication.WINDOW_SIZE_Y);
        subRoot.getChildren().addAll(lblHeader, btnRestart, btnMainMenu);

        root.getChildren().add(subRoot);

        return scene;
    }

    public static Scene levelCompleted(){

        Pane root = new Pane();
        VBox subRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Label lblHeader = menuHeader("Level Completed");

        Button btnNextLevel = new Button("Next Level");
        btnNextLevel.setId("button-wide");
        btnNextLevel.setOnAction(e -> {
            MainApplication.levelQueue.remove();
            if(MainApplication.levelQueue.peek() != null){
                MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levelQueue.peek()));
            } else {
                System.out.println("No next level.");
                MainApplication.queueLevels();
                MainApplication.getStage().setScene(mainMenu());
            }
        });

        Button btnMainMenu = btnMainMenu();

        subRoot.setAlignment(Pos.CENTER);
        subRoot.setPrefWidth(MainApplication.WINDOW_SIZE_X);
        subRoot.setPrefHeight(MainApplication.WINDOW_SIZE_Y);
        subRoot.getChildren().addAll(lblHeader, btnNextLevel, btnMainMenu);
        root.getChildren().add(subRoot);

        return scene;
    }

    public static Scene pauseMenu(Scene prevScene) {

        VBox root = new VBox();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Label lblHeader = menuHeader("Paused");

        Button btnResume = btnResume(prevScene);
        Button btnRestart = btnRestart();
        Button btnMainMenu = btnMainMenu();
        Button btnHelp = btnHelp(scene, true);

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                MainApplication.getStage().setScene(mainMenu());
            }
        });

        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lblHeader, btnResume, btnRestart, btnHelp, btnMainMenu);

        return scene;
    }

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

        Rectangle rectBG = new Rectangle(0, 0, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
        rectBG.setFill(Color.color(0.0, 0.0, 0.0, 0.5));

        Label lblHeader = menuHeader("Paused");

        Button btnResume = btnResume(prevScene);
        Button btnRestart = btnRestart();
        Button btnMainMenu = btnMainMenu();
        Button btnHelp = btnHelp(scene, true);

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                MainApplication.getStage().setScene(prevScene);
                MainApplication.startTimer();
            }
        });

        subRoot.setSpacing(20);
        subRoot.setAlignment(Pos.CENTER);
        subRoot.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 100);
        subRoot.setLayoutY((MainApplication.WINDOW_SIZE_Y / 2) - 250);
        subRoot.getChildren().addAll(lblHeader, btnResume, btnRestart, btnHelp, btnMainMenu);

        root.getChildren().addAll(rectBG, subRoot);

        return scene;
    }

    /*
     *     -- INDIVIDUAL ELEMENTS --
     */

    private static Label menuHeader(String text) {

        Label label = new Label(text);
        label.setId("menu-header");
        label.setPadding(new Insets(0, 0, 20, 0));
        label.setEffect(dropShadow);

        return label;
    }

    private static Button btnResume(Scene prevScene){

        Button btnResume = new Button("Resume");
        btnResume.setOnAction(e ->{
            MainApplication.getStage().setScene(prevScene);
            MainApplication.startTimer();
        });
        btnResume.setId("button-wide");
        btnResume.setEffect(dropShadow);

        return btnResume;
    }

    private static Button btnRestart(){

        Button btnRestart = new Button("Restart");
        btnRestart.setOnAction(e -> MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levelQueue.peek())));
        btnRestart.setId("button-wide");
        btnRestart.setEffect(dropShadow);

        return btnRestart;
    }

    private static Button btnHelp(Scene prevScene, boolean wide){

        Button btnHelp = new Button("Help");
        btnHelp.setOnAction(e -> MainApplication.getStage().setScene(helpMenu(prevScene)));
        if (wide) btnHelp.setId("button-wide");
        btnHelp.setEffect(dropShadow);

        return btnHelp;
    }

    private static Button btnMainMenu(){

        Button btnMainMenu = new Button("Main Menu");
        btnMainMenu.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));
        btnMainMenu.setId("button-wide");
        btnMainMenu.setEffect(dropShadow);

        return btnMainMenu;

    }

}
