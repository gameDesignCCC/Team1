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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
     * Loads a dialog tree from a file
     *
     * @param filename The path to the dialog file
     * @return
     */
    public ArrayList<DialogNode> loadtree(String filename) {
        ArrayList<DialogNode> dialogNodes = new ArrayList<>();

        try {
            File f = new File(filename);

            Scanner in = new Scanner(f);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                dialogNodes.add(new DialogNode(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dialogNodes;
    }

    /**
     * Creates a button and places it somewhere on the screen
     *
     * @param text The text in the button
     * @param handler The handler that is called when the button is pressed
     * @param pos The position of the button on the screen
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
     * Destroys the dialog box
     *
     * @param root The pane where the box is
     */
    public void destroyBox(Pane root) {
        root.getChildren().removeAll(btns[0], btns[1], txt, img);
    }

    /**
     * Creates a dialog box
     *
     * @param text The text inside the box
     * @param option1 The text shown as the first option
     * @param option2 The text shown as the second option
     * @param handler1 The handler for the first option button
     * @param handler2 The handler for the second option button
     * @param root The root pane in which the box is drawn
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
        root.getChildren().addAll(img, txt, btns[0]);
        if (option2 != null) {
            btns[1] = makeButton(option2, handler2, pos2);
            root.getChildren().add(btns[1]);
        }
    }

    /**
     * Shows a dialog tree on screen
     * @param firstNode The first node to start at in the list
     * @param nodeList The list of nodes
     * @param root The root pane to show the dialog boxes on
     */
    public void showTree(DialogNode firstNode, ArrayList<DialogNode> nodeList, Pane root) {
        DialogNode currentNode = firstNode;
        createBox(currentNode.getText(),
                currentNode.getOption1(),
                currentNode.getOption2(),
                event -> {
                    destroyBox(root);
                    if (currentNode.getNextNode1() != 0) {
                        showTree(nodeList.get(currentNode.getNextNode1() - 1), nodeList, root);
                    }
                },
                event -> {
                    destroyBox(root);
                    if (currentNode.getNextNode2() != 0) {
                        showTree(nodeList.get(currentNode.getNextNode2() - 1), nodeList, root);
                    }
                },
                root);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        BoxPreset preset = new BoxPreset();
        preset.text = "1";

        ArrayList<DialogNode> nodes = loadtree("Game/src/assets/dialog_trees/dialog1.txt");

        showTree(nodes.get(0), nodes, root);

        primaryStage.setScene(new Scene(root, screensize[0], screensize[1]));
        primaryStage.show();

    }
}
