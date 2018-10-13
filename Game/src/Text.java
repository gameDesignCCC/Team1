/*
 * Author(s):
 * Date: 13/10/2018
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.*;

public class Text extends Application {
    public static void main ( String[] args ) {
        launch(args);
    }

    public double [] screensize = {1630, 1000};

    public static Button makeButton(String text, EventHandler<ActionEvent> handler, double[] pos) {
        Button tmp = new Button();
        tmp.setLayoutX(pos[0]);
        tmp.setLayoutY(pos[1]);
        tmp.setText(text);
        tmp.setOnAction(handler);
        return tmp;
    }

    public void createBox(String text,
                          String option1,
                          String option2,
                          EventHandler<ActionEvent> handler1,
                          EventHandler<ActionEvent> handler2,
                          Pane root) {

        double w = screensize[0] / 2;
        double h = screensize[1] / 5;
        double x = screensize[0] * 0.01;
        double y = screensize[1] - h;

        Rectangle textbox = new Rectangle(w,h,Color.BLACK);
        textbox.setLayoutX(x);
        textbox.setLayoutY(y);

        Rectangle textbox1 = new Rectangle(w-10,h-10,Color.LIGHTGRAY);
        textbox1.setLayoutX(x+5);
        textbox1.setLayoutY(y+5);


        Label label = new Label();
        label.setTextFill(Color.BLUE);
        label.setLayoutX(x + 20);
        label.setLayoutY(y + 20);
        label.setText(text);

        double bx = w - 100;

        double[] pos1 = {200, 100};
        double[] pos2 = {200, 100};

        Button btn1 = makeButton(option1, handler1, pos1);
        Button btn2 = makeButton(option2, handler2, pos2);
        root.getChildren().addAll(btn1, btn2,textbox,textbox1,label);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();



        createBox("Hello world", "Foo", "bar", event -> {
            System.out.println("1");
        }, event -> {
            System.out.println("2");
        }, root);

        primaryStage.setScene(new Scene(root, screensize[0], screensize[1]));
        primaryStage.show();

    }
}
