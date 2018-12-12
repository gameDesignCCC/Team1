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
        MainApplication.getStage().setScene(MainApplication.getGameScene(MainApplication.currentLevel));
    }

    @FXML
    private void btnEventMainMenu(ActionEvent event){
        MainApplication.getStage().setScene(Menu.mainMenu());
    }

    @FXML
    private void btnEventNextLevel(ActionEvent event){
        MainApplication.currentLevel = "./Game/src/assets/levels/level_2";
        MainApplication.getStage().setScene(MainApplication.getGameScene("./Game/src/assets/levels/level_2"));
    }

}
