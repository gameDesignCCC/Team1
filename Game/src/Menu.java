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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;

public class Menu {

    // Effects
    private static DropShadow dropShadow = new DropShadow();

    // Initialization
    public static void init() {
        dropShadow.setRadius(20.0);
        // https://fonts.google.com/specimen/Russo+One
        Font.loadFont(MainApplication.class.getResource("/assets/ui/fonts/font_russo_one_regular.ttf").toExternalForm(), 10);
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
        btnLevelSelect.setOnAction(e -> MainApplication.getStage().setScene(levelSelect(scene, 0, MainApplication.levels.get(0), true)));

        btnExit.setTranslateY(160);
        btnExit.setOnAction(e -> MainApplication.exit());

        root.getChildren().addAll(ivBG, btnStart, btnHelp, btnExit, btnLevelSelect);

        MainApplication.currentRoot = root;

        return scene;
    }

    private static Scene levelSelect(Scene prevScene, int page, Level selectedLevel, boolean userSelected) {

        Pane root = new Pane();
        VBox levelListRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        levelListRoot.setLayoutX(40);
        levelListRoot.setLayoutY(40);

        Button btnNxtPage = new Button();
        Button btnPrevPage = new Button();

        btnNxtPage.setId("button-small-disabled");
        btnNxtPage.setGraphic(new ImageView(new Image("/assets/ui/icons/down.png")));
        btnNxtPage.setLayoutX(120);
        btnNxtPage.setLayoutY(600);
        if (page * 7 < MainApplication.levels.size() && MainApplication.levels.size() > (page + 1) * 7) {
            btnNxtPage.setOnAction(e -> MainApplication.getStage().setScene(levelSelect(prevScene, page + 1, MainApplication.levels.get((page + 1) * 7), true)));
            btnNxtPage.setId("button-small");
        }

        btnPrevPage.setId("button-small-disabled");
        btnPrevPage.setGraphic(new ImageView(new Image("/assets/ui/icons/up.png")));
        btnPrevPage.setLayoutX(40);
        btnPrevPage.setLayoutY(600);
        if (page != 0) {
            btnPrevPage.setOnAction(e -> MainApplication.getStage().setScene(levelSelect(prevScene, page - 1, MainApplication.levels.get((page - 1) * 7), true)));
            btnPrevPage.setId("button-small");
        }

        root.getChildren().addAll(btnNxtPage, btnPrevPage);

        for (int i = 0; i < MainApplication.levels.size(); i++) {

            if (i == (page + 1) * 7) {
                break;
            } else if (i >= page * 7 && (MainApplication.completedLevels.contains(MainApplication.levels.get(i)) || i == MainApplication.currentLevelIndex)) {
                Button btn = new Button(MainApplication.levels.get(i).getName());
                int lvl = i;
                btn.setOnAction(e -> {
                    MainApplication.getStage().setScene(levelSelect(prevScene, page, MainApplication.levels.get(lvl), true));
                });
                if (userSelected && selectedLevel == MainApplication.levels.get(i)) {
                    btn.setId("button-selected");
                }
                levelListRoot.getChildren().add(btn);
            } else if (i >= page * 7) {
                Button btn = new Button(MainApplication.levels.get(i).getName());
                btn.setId("button-disabled");
                levelListRoot.getChildren().add(btn);
            }
        }

        Rectangle levelsBG = new Rectangle(20, 0, 200, MainApplication.WINDOW_SIZE_Y - 40);
        levelsBG.setHeight(MainApplication.WINDOW_SIZE_Y);
        levelsBG.setOpacity(0.3);

        Rectangle levelDetailsBG = new Rectangle(240, 20, MainApplication.WINDOW_SIZE_X - 260, MainApplication.WINDOW_SIZE_Y - 40);
        levelDetailsBG.setOpacity(0.3);

        root.getChildren().addAll(levelDetailsBG, levelsBG, levelListRoot);

        Label lbl = new Label("Level " + (selectedLevel.getLevelNumber() + 1));
        lbl.setLayoutX(300);
        lbl.setLayoutY(50);
        lbl.setId("menu-text");
        root.getChildren().add(lbl);

        Button btnStartLevel = new Button("Start");
        btnStartLevel.setLayoutX(260);
        btnStartLevel.setLayoutY(620);
        if (MainApplication.completedLevels.contains(selectedLevel) || MainApplication.currentLevelIndex == MainApplication.levels.indexOf(selectedLevel)) {
            btnStartLevel.setOnAction(e -> {
                MainApplication.getStage().setScene(MainApplication.getGameScene(selectedLevel.getPath()));
                MainApplication.currentLevelIndex = MainApplication.levels.indexOf(selectedLevel);
            });
        } else {
            btnStartLevel.setId("button-disabled");
        }
        root.getChildren().add(btnStartLevel);


        Button btnBack = new Button("Back");
        btnBack.setLayoutX(40);
        btnBack.setLayoutY(660);
        btnBack.setId("button-short");
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

        Button b = new Button("settings");
        b.setOnAction(e -> MainApplication.getStage().setScene(settingsMenu()));
        root.getChildren().add(b);

        return scene;
    }

    public static Scene settingsMenu(){
        Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        Button btn = new Button("load game");
        btn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            File f = fc.showOpenDialog(MainApplication.getStage());
            MainApplication.loadGame(f);
            MainApplication.getStage().setScene(mainMenu());
        });
        root.getChildren().add(btn);

        Button btn0 = new Button("back");
        btn0.setLayoutY(40);
        btn0.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));
        root.getChildren().add(btn0);

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
