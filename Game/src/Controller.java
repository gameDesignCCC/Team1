import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Scene prevScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void btnEventBack(ActionEvent event){
        MainApplication.getStage().setScene(prevScene);
    }

    @FXML
    private void btnEventRestart(ActionEvent event){
        MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levelQueue.peek()));
    }

    @FXML
    private void btnEventMainMenu(ActionEvent event){
        MainApplication.getStage().setScene(Menu.mainMenu());
    }

    @FXML
    private void btnEventNextLevel(ActionEvent event) {
        MainApplication.levelQueue.remove();
        if (MainApplication.levelQueue.peek() != null) {
            MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levelQueue.peek()));
        } else {
            System.out.println("No next level.");
            MainApplication.queueLevels();
            MainApplication.getStage().setScene(Menu.mainMenu());
        }
    }
}
