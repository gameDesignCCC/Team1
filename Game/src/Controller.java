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
        MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.peek()));
    }

    @FXML
    private void btnEventMainMenu(ActionEvent event){
        MainApplication.getStage().setScene(Menu.mainMenu());
    }

    @FXML
    private void btnEventNextLevel(ActionEvent event) {
        MainApplication.levels.remove();
        if (MainApplication.levels.peek() != null) {
            MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.levels.peek()));
        } else {
            System.out.println("No next level.");
            MainApplication.queueLevels();
            MainApplication.getStage().setScene(Menu.mainMenu());
        }
    }
}
