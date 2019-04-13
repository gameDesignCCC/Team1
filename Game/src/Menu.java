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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import util.Format;
import util.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu {

    /**
     * Initialization
     */
    static void init() {
        // https://fonts.google.com/specimen/Russo+One
        Font.loadFont(MainApplication.class.getResource("/assets/ui/fonts/font_russo_one_regular.ttf").toExternalForm(), 10);
    }

    /*
     *     -- SCENES --
     */

    /**
     * Main Menu Scene
     *
     * @return Main Menu Scene
     */
    static Scene mainMenu() {

        Pane root = new Pane();
        VBox listRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        ImageView ivBG = new ImageView(new Image("/assets/ui/backgrounds/alt_main_menu_bg_placeholder.png"));

        Button btnStart = new Button("Start");
        Button btnHelp = btnHelp(scene, false);
        Button btnSettings = new Button("Settings");
        Button btnLevelSelect = new Button("Levels");
        Button btnExit = new Button("Exit");

        btnStart.setOnAction(e -> {
            if (MainApplication.currentLevelIndex < MainApplication.levels.size() - 1) {
                MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(MainApplication.currentLevelIndex)));
            } else {
                MainApplication.logger.log("No next level in queue for loading, resetting to level 0.");
                MainApplication.currentLevelIndex = 0;
                MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(MainApplication.currentLevelIndex)));
            }
        });
        btnSettings.setOnAction(e -> MainApplication.getStage().setScene(settingsMenu(scene)));
        btnLevelSelect.setOnAction(e -> MainApplication.getStage().setScene(levelSelect(scene, 0, MainApplication.levels.get(0), true)));
        btnExit.setOnAction(e -> MainApplication.exit());

        listRoot.getChildren().addAll(btnStart, btnSettings, btnHelp, btnLevelSelect, btnExit);
        listRoot.setLayoutX(MainApplication.WINDOW_SIZE_X / 2 - 80);
        listRoot.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - listRoot.getChildren().size() * 20);

        root.getChildren().addAll(ivBG, listRoot);

        MainApplication.currentRoot = root;

        return scene;
    }

    /**
     * Level Select Menu Scene
     *
     * @param prevScene     Previous Scene
     * @param page          Page of Level Selection
     * @param selectedLevel Selected Level When Opening
     * @param userSelected  If the user selected the level or it was done automatically.
     * @return Level Select Scene
     */
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
                btn.setOnAction(e -> MainApplication.getStage().setScene(levelSelect(prevScene, page, MainApplication.levels.get(lvl), true)));
                if (selectedLevel == MainApplication.levels.get(i)) {
                    if (userSelected) btn.setId("button-selected");
                    btn.setOnAction(null);
                    btn.setOnMouseClicked(e -> {
                        if (e.getClickCount() >= 2)
                            MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(lvl)));
                    });
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
                MainApplication.getStage().setScene(MainApplication.getGameScene(selectedLevel));
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
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(prevScene));
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

    /**
     * Help Menu Scene
     *
     * @param prevScene Previous Scene
     * @return Help Menu Scene
     */
    static Scene helpMenu(Scene prevScene) {

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

    /**
     * Settings Menu Scene
     *
     * @param prevScene Previous Scene
     * @return Settings Menu Scene
     */
    static Scene settingsMenu(Scene prevScene) {

        Pane root = new Pane();
        VBox listRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Button btnLoadGame = new Button("Load Game");
        btnLoadGame.setId("button-wide-smallfont");
        btnLoadGame.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(MainApplication.workingSaveDIR);
            fc.setTitle("Load Game Save");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game save file", "*" + MainApplication.SAVE_EXT));
            File f = fc.showOpenDialog(MainApplication.getStage());
            if (f != null) MainApplication.loadGame(f);
        });

        Button btnToggleDisplayFPS = new Button("Show FPS: " + Format.boolAsOnOff(MainApplication.displayFPS));
        btnToggleDisplayFPS.setId("button-wide-smallfont");
        btnToggleDisplayFPS.setOnAction(e -> {
            MainApplication.displayFPS = !MainApplication.displayFPS;
            btnToggleDisplayFPS.setText("Show FPS: " + Format.boolAsOnOff(MainApplication.displayFPS));
            MainApplication.logger.log("(User Settings) Toggled FPS Display " + Format.boolAsOnOff(MainApplication.displayFPS));
        });

        Button btnToggleAutoSave = new Button("AutoSave: " + Format.boolAsOnOff(MainApplication.autoSave));
        btnToggleAutoSave.setId("button-wide-smallfont");
        btnToggleAutoSave.setOnAction(e -> {
            MainApplication.autoSave = !MainApplication.autoSave;
            btnToggleAutoSave.setText("AutoSave: " + Format.boolAsOnOff(MainApplication.autoSave));
            MainApplication.logger.log("(User Settings) Toggled AutoSave " + Format.boolAsOnOff(MainApplication.autoSave));
        });

        Button btnClearLogs = new Button("Clear Logs");
        btnClearLogs.setId("button-wide-smallfont");
        btnClearLogs.setOnAction(e -> {
            if (MainApplication.LOG_OUTPUT_DIR.listFiles() != null) {
                for (File f : MainApplication.LOG_OUTPUT_DIR.listFiles()) {
                    if (!f.getPath().equals(MainApplication.logger.getOutputFile().getPath())) {
                        boolean success = f.delete();
                        if (success) {
                            MainApplication.logger.log("(User Settings) Removed old log output file at \"" + f.getPath() + "\".");
                        } else {
                            MainApplication.logger.log("(User Settings) Could not remove old log output at \"" + f.getPath() + "\".", Logger.Type.ERROR);
                        }
                    }
                }
            }
        });

        Button btnViewLogs = new Button("View Logs");
        btnViewLogs.setId("button-wide-smallfont");
        btnViewLogs.setOnAction(e ->{
            try {
                Desktop.getDesktop().open(MainApplication.LOG_OUTPUT_DIR);
            } catch (IOException ex) {
                ex.printStackTrace();
                MainApplication.logger.log(ex);
                MainApplication.logger.log("Could not open file explorer at log output directory.", Logger.Type.ERROR);
            }
        });

        listRoot.getChildren().addAll(menuHeader("Settings"), btnLoadGame, btnToggleAutoSave, btnToggleDisplayFPS, btnClearLogs, btnViewLogs, btnBack(prevScene, true));
        listRoot.setLayoutX(MainApplication.WINDOW_SIZE_X / 2 - 100);
        listRoot.setLayoutY(MainApplication.WINDOW_SIZE_Y / 2 - listRoot.getChildren().size() * 40);

        root.getChildren().add(listRoot);

        return scene;
    }

    /**
     * Level Completed Scene
     *
     * @return Level Completed Scene
     */
    static Scene levelCompleted() {

        Pane root = new Pane();
        VBox subRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Label lblHeader = menuHeader("Level Completed");

        Button btnNextLevel = new Button("Next Level");
        btnNextLevel.setId("button-wide");
        btnNextLevel.setOnAction(e -> {
            if (MainApplication.levels.size() > MainApplication.currentLevelIndex) {
                MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(MainApplication.currentLevelIndex)));
            } else {
                MainApplication.getStage().setScene(gameCompleted());
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

    /**
     * Pause Menu Scene
     *
     * @param prevScene Previous Scene
     * @return Pause Menu Scene
     */
    static Scene pauseMenu(Scene prevScene) {

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
                MainApplication.musicPlayer.play();
                MainApplication.startTimer();
            }
        });

        subRoot.setSpacing(20);
        subRoot.setAlignment(Pos.CENTER);
        subRoot.setLayoutX((MainApplication.WINDOW_SIZE_X / 2) - 100);
        subRoot.setLayoutY((MainApplication.WINDOW_SIZE_Y / 2) - 250);
        subRoot.getChildren().addAll(menuHeader("Paused"), btnResume(prevScene), btnRestart(), btnSettings(scene, true), btnHelp(scene, true), btnMainMenu());

        root.getChildren().addAll(rectBG, subRoot);

        return scene;
    }

    static Scene gameCompleted() {

        Pane root = new Pane();
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Label lbl = new Label("gg ez");
        lbl.setTextFill(Color.WHITE);
        lbl.setLayoutX(100);
        lbl.setLayoutY(100);
        lbl.setScaleX(2);
        lbl.setScaleY(2);

        Button btn = new Button("Main Menu");
        btn.setId("button-wide");
        btn.setOnAction(e -> {
            MainApplication.getStage().setScene(mainMenu());
        });
        btn.setLayoutX(100);
        btn.setLayoutY(200);
        root.getChildren().addAll(lbl, btn);

        return scene;
    }

    /**
     * Death Menu Scene
     *
     * @return Death Menu Scene
     */
    static Scene deathMenu() {

        Pane root = new Pane();
        VBox subRoot = new VBox(20);
        Scene scene = new Scene(root, MainApplication.WINDOW_SIZE_X, MainApplication.WINDOW_SIZE_Y);

        scene.getStylesheets().add("/assets/ui/stylesheets/style.css");

        Label lblHeader = menuHeader("You Died");
        lblHeader.setId("death-screen-header");

        Button btnMainMenu = btnMainMenu();
        Button btnRestart = btnRestart();

        subRoot.setAlignment(Pos.CENTER);
        subRoot.setPrefWidth(MainApplication.WINDOW_SIZE_X);
        subRoot.setPrefHeight(MainApplication.WINDOW_SIZE_Y);
        subRoot.getChildren().addAll(lblHeader, btnRestart, btnMainMenu);

        root.getChildren().add(subRoot);

        return scene;
    }

    /**
     * Pause Menu Scene Non-transparent [DEPRECATED]
     *
     * @param prevScene Previous Scene
     * @return Pause Menu Screen Non-transparent
     */
    public static Scene pauseMenuLegacy(Scene prevScene) {

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

    /*
     *     -- INDIVIDUAL ELEMENTS --
     */

    private static Label menuHeader(String text) {

        Label label = new Label(text);
        label.setId("menu-header");
        label.setPadding(new Insets(0, 0, 20, 0));
        label.setEffect(dropShadow());

        return label;
    }

    private static Button btnResume(Scene prevScene) {

        Button btnResume = new Button("Resume");
        btnResume.setOnAction(e -> {
            MainApplication.getStage().setScene(prevScene);
            MainApplication.musicPlayer.play();
            MainApplication.startTimer();
        });
        btnResume.setId("button-wide");
        btnResume.setEffect(dropShadow());

        return btnResume;
    }

    private static Button btnSettings(Scene prevScene, boolean wide) {
        Button btnSetting = new Button("Settings");
        if (wide) btnSetting.setId("button-wide");
        btnSetting.setEffect(dropShadow());
        btnSetting.setOnAction(e -> MainApplication.getStage().setScene(settingsMenu(prevScene)));
        return btnSetting;
    }

    private static Button btnRestart() {

        Button btnRestart = new Button("Restart");
        btnRestart.setOnAction(e -> {
            MainApplication.collectedPartsCurrent.clear();
            MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.get(MainApplication.currentLevelIndex)));
        });
        btnRestart.setId("button-wide");
        btnRestart.setEffect(dropShadow());

        return btnRestart;
    }

    private static Button btnHelp(Scene prevScene, boolean wide) {

        Button btnHelp = new Button("Help");
        btnHelp.setOnAction(e -> MainApplication.getStage().setScene(helpMenu(prevScene)));
        if (wide) btnHelp.setId("button-wide");
        btnHelp.setEffect(dropShadow());

        return btnHelp;
    }

    private static Button btnMainMenu() {

        Button btnMainMenu = new Button("Main Menu");
        btnMainMenu.setOnAction(e -> MainApplication.getStage().setScene(mainMenu()));
        btnMainMenu.setId("button-wide");
        btnMainMenu.setEffect(dropShadow());

        return btnMainMenu;

    }

    private static Button btnBack(Scene prevScene, boolean wide) {
        Button btnBack = new Button("Back");
        if (wide) btnBack.setId("button-wide");
        btnBack.setEffect(dropShadow());
        btnBack.setOnAction(e -> MainApplication.getStage().setScene(prevScene));
        return btnBack;
    }

    /*
     *     -- EFFECTS --
     */

    private static DropShadow dropShadow() {
        DropShadow ds = new DropShadow();
        ds.setRadius(20.0);
        return ds;
    }

}
