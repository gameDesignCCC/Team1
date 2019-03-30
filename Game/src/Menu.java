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

    // Effects
    private static DropShadow dropShadow = new DropShadow();

    // Initialization
    public static void init() {
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

        Button btnStart = new Button("Start");
        Button btnHelp = btnHelp(scene, false);
        Button btnLevelSelect = new Button("Levels");
        Button btnExit = new Button("Exit");

        btnStart.setTranslateY(-80);
        btnStart.setOnAction(e -> MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(MainApplication.currentLevelIndex).getPath())));

        btnLevelSelect.setTranslateY(80);
        btnLevelSelect.setOnAction(e -> MainApplication.getStage().setScene(levelSelect(scene)));

        btnExit.setTranslateY(160);
        btnExit.setOnAction(e -> MainApplication.exit());

        root.getChildren().addAll(ivBG, btnStart, btnHelp, btnExit, btnLevelSelect);

        MainApplication.currentRoot = root;

        return scene;
    }

    private static Scene levelSelect(Scene prevScene) {

        Pane root = new Pane();
        VBox levelListRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        levelListRoot.setLayoutX(40);
        levelListRoot.setLayoutY(40);

        for (int i = 0; i < MainApplication.levels.size(); i++) {

            if (i == 7) {

                Button nxtPageBtn = new Button();
                Button prevPageBtn = new Button();

                nxtPageBtn.setId("button-disabled-small");
                nxtPageBtn.setGraphic(new ImageView(new Image("/assets/ui/icons/down.png")));
                nxtPageBtn.setLayoutX(120);
                nxtPageBtn.setLayoutY(600);
                nxtPageBtn.setOnAction(e -> {
                });

                prevPageBtn.setId("button-disabled-small");
                prevPageBtn.setGraphic(new ImageView(new Image("/assets/ui/icons/up.png")));
                prevPageBtn.setLayoutX(40);
                prevPageBtn.setLayoutY(600);
                prevPageBtn.setOnAction(e -> {
                });

                root.getChildren().addAll(nxtPageBtn, prevPageBtn);
                break;

            } else if (MainApplication.completedLevels.contains(MainApplication.levels.get(i))) {
                Button btn = new Button(MainApplication.levels.get(i).getName());
                int lvl = i;
                btn.setOnAction(e -> {
                    MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(lvl).getPath()));
                    MainApplication.currentLevelIndex = lvl;
                });
                levelListRoot.getChildren().add(btn);

            } else {
                Button btn = new Button(MainApplication.levels.get(i).getName());
                btn.setId("button-disabled");
                levelListRoot.getChildren().add(btn);
            }
        }

        Rectangle levelsBG = new Rectangle(20, 20, 200, MainApplication.WINDOW_SIZE_Y - 40);
        levelsBG.setHeight(MainApplication.levels.size() * 80 + 20);
        levelsBG.setOpacity(0.3);

        Rectangle levelDetailsBG = new Rectangle(240, 20, MainApplication.WINDOW_SIZE_X - 260, MainApplication.WINDOW_SIZE_Y - 40);
        levelDetailsBG.setOpacity(0.3);

        root.getChildren().add(levelDetailsBG);
        root.getChildren().add(levelsBG);
        root.getChildren().add(levelListRoot);

        Button btnBack = new Button("Back");
        btnBack.setLayoutX(260);
        btnBack.setLayoutY(MainApplication.WINDOW_SIZE_Y - 100);
        btnBack.setOnAction(e -> {
            MainApplication.getStage().setScene(prevScene);
        });
        root.getChildren().add(btnBack);

        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                MainApplication.getStage().setScene(mainMenu());
            }
        });

        levelsBG.toBack();
        levelDetailsBG.toBack();

        return scene;
    }

    public static Scene helpMenu(Scene prevScene) {

        Pane root = new Pane();
        VBox subRoot = new VBox();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        ImageView overlay = new ImageView(new Image("/assets/ui/overlays/controls_overlay.png"));
        root.getChildren().add(overlay);

        subRoot.setAlignment(Pos.CENTER);
        subRoot.setPrefWidth(MainApplication.WINDOW_SIZE_X);
        subRoot.setPrefHeight(MainApplication.WINDOW_SIZE_Y);
        root.getChildren().add(subRoot);

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(prevScene));
        btnBack.setLayoutY(MainApplication.WINDOW_SIZE_Y - 80);
        btnBack.setLayoutX(20);

        root.getChildren().add(btnBack);


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

    public static Scene levelCompleted() {

        Pane root = new Pane();
        VBox subRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Label lblHeader = menuHeader("Level Completed");

        Button btnNextLevel = new Button("Next Level");
        btnNextLevel.setId("button-wide");
        btnNextLevel.setOnAction(e -> {
            if (MainApplication.levels.size() > MainApplication.currentLevelIndex) {
                MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(MainApplication.currentLevelIndex).getPath()));
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
            if (e.getCode() == KeyCode.ESCAPE) {
                MainApplication.getStage().setScene(mainMenu());
            }
        });

        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lblHeader, btnResume, btnRestart, btnHelp, btnMainMenu);

        return scene;
    }

    static Scene pauseMenuTransparent(Scene prevScene) {

        Pane root = new Pane();
        VBox subRoot = new VBox();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        // Note: It might be better to just add the menu elements to the already existing game scene and remove them afterwards.
        for (Node node : prevScene.getRoot().getChildrenUnmodifiable()) {
            if (node instanceof ImageView) {
                ImageView iv = ((ImageView) node);
                ImageView imageView = new ImageView(iv.getImage());
                imageView.setX(iv.getX());
                imageView.setY(iv.getY());
                imageView.setFitWidth(iv.getFitWidth());
                imageView.setFitHeight(iv.getFitHeight());
                root.getChildren().add(imageView);
            } else if (node instanceof Rectangle) {
                Rectangle rect = ((Rectangle) node);
                Rectangle rectangle = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                rectangle.setFill(rect.getFill());
                rectangle.setEffect(rect.getEffect());
                root.getChildren().add(rectangle);
            }
        }

        Rectangle rectBG = new Rectangle(0, 0, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);
        rectBG.setFill(Color.color(0.0, 0.0, 0.0, 0.5));

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                MainApplication.getStage().setScene(prevScene);
                MainApplication.startTimer();
            }
        });

        subRoot.setSpacing(20);
        subRoot.setAlignment(Pos.CENTER);
        subRoot.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 100);
        subRoot.setLayoutY((MainApplication.WINDOW_SIZE_Y / 2) - 250);
        subRoot.getChildren().addAll(menuHeader("Paused"), btnResume(prevScene), btnRestart(), btnHelp(scene, true), btnMainMenu());

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

    private static Button btnResume(Scene prevScene) {

        Button btnResume = new Button("Resume");
        btnResume.setOnAction(e -> {
            MainApplication.getStage().setScene(prevScene);
            MainApplication.startTimer();
        });
        btnResume.setId("button-wide");
        btnResume.setEffect(dropShadow);

        return btnResume;
    }

    private static Button btnRestart() {

        Button btnRestart = new Button("Restart");
        btnRestart.setOnAction(e -> {
            MainApplication.collectedPartsCurrent.clear();
            MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(MainApplication.currentLevelIndex).getPath()));
        });
        btnRestart.setId("button-wide");
        btnRestart.setEffect(dropShadow);

        return btnRestart;
    }

    private static Button btnHelp(Scene prevScene, boolean wide) {

        Button btnHelp = new Button("Help");
        btnHelp.setOnAction(e -> MainApplication.getStage().setScene(helpMenu(prevScene)));
        if (wide) btnHelp.setId("button-wide");
        btnHelp.setEffect(dropShadow);

        return btnHelp;
    }

    private static Button btnMainMenu() {

        Button btnMainMenu = new Button("Main Menu");
        btnMainMenu.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));
        btnMainMenu.setId("button-wide");
        btnMainMenu.setEffect(dropShadow);

        return btnMainMenu;

    }

}
