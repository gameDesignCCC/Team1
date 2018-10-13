import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextBox extends Application {

    static double windowSizeX = MainApplication.windowSizeX;
    static double windowSizeY = MainApplication.windowSizeY;

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Pane root = new Pane();
        Scene scene = new Scene(root, windowSizeX, windowSizeY);

        Text txt = new Text();
        txt.setText("This is some dialog.");
        txt.setX(20);
        txt.setY(windowSizeY - 180);
        txt.setFont(Font.font("Arial", 20));
        txt.setFill(Color.WHITE);

        Rectangle txtBG = new Rectangle(10, windowSizeY -210, 500, 200);
        txtBG.setStroke(Color.RED);

        Button btnA = new Button();
        btnA.setText("Option A");
        btnA.setLayoutX(340);
        btnA.setLayoutY(windowSizeY - 50);

        Button btnB = new Button();
        btnB.setText("Option B");
        btnB.setLayoutX(425);
        btnB.setLayoutY(windowSizeY - 50);

        root.getChildren().addAll(txtBG, txt, btnA, btnB);

        window.setScene(scene);
        window.setTitle("Title");
        window.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
