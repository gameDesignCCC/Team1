/*
 * Author(s):
 * Date: 6/10/2018
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Scanner;

public class Textbox extends Application {

    // Begin Nested BoxPreset Class
    private class BoxPreset {
        public String text;
        public String option1;
        public String option2;
        public EventHandler<ActionEvent> handler1;
        public EventHandler<ActionEvent> handler2;
    } // End Nested BoxPreset Class

    public static void main ( String[] args ) {
        launch(args);
    }

    private double [] screensize = {1280, 720};
    private ImageView img;
    private Text txt;
    private Button[] btns = new Button[2];

    /**
     *  TODO Add descriptions
     *
     * @param filename
     * @return
     */
    public BoxPreset loadtree(String filename) {
        try {
            Scanner in = new Scanner("/assets/decision_trees/" + filename);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return new BoxPreset();
    }

    /**
     * TODO Add descriptions
     *
     * @param text
     * @param handler
     * @param pos
     * @return
     */
    public static Button makeButton(String text, EventHandler<ActionEvent> handler, double[] pos) {
        Button tmp = new Button();
        tmp.setLayoutX(pos[0]);
        tmp.setLayoutY(pos[1]);
        tmp.setText(text);
        tmp.setOnAction(handler);
        return tmp;
    }

    /**
     * TODO Add descriptions
     *
     * @param root
     */
    public void destroy(Pane root) {
        root.getChildren().removeAll(btns[0], btns[1], txt, img);
    }

    /**
     * TODO Add descriptions
     *
     * @param box
     */
    public void updateBox(BoxPreset box) {
        txt.setText(box.text);
        btns[0].setText(box.option1);
        btns[1].setText(box.option2);
        btns[0].setOnAction(box.handler1);
        btns[1].setOnAction(box.handler2);

    }

    /**
     * TODO Add descriptions
     *
     * @param text
     * @param option1
     * @param option2
     * @param handler1
     * @param handler2
     * @param root
     */
    public void createBox(String text,
        String option1,
        String option2,
        EventHandler<ActionEvent> handler1,
        EventHandler<ActionEvent> handler2,
        Pane root) {

        double w = screensize[0] / 2;
        double h = screensize[1] / 5;
        double x = screensize[0] * 0.2;
        double y = screensize[1] - h;

        Image txtbx = new Image("/assets/ui/text-box.png");
        img = new ImageView(txtbx);
        img.setX(x);
        img.setY(y);

        txt = new Text();
        txt.setFill(Color.WHEAT);
        txt.setText(text);
        txt.setX(x + (w / 20));
        txt.setY(y + (h / 5));

        double[] pos2 = {x + w - (screensize[0] * 0.05), y + (h - 70)};
        double[] pos1 = {x + w - (screensize[0] * 0.05), y + (h - 120)};

        btns[0] = makeButton(option1, handler1, pos1);
        btns[1] = makeButton(option2, handler2, pos2);
        root.getChildren().addAll(img, txt, btns[0], btns[1]);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        BoxPreset preset = new BoxPreset();
        preset.text = "1";

        createBox("Hello world",
                "Option 1",
                "Option 2",
                event -> System.out.println("This loaded the level"),
                event -> updateBox(preset),
                root);

        primaryStage.setScene(new Scene(root, screensize[0], screensize[1]));
        primaryStage.show();

    }
}
