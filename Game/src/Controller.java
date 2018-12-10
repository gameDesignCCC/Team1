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
    private void helpMenuBack(ActionEvent event){
        MainApplication.getStage().setScene(prevScene);
    }

    public void setPrevScene(Scene prevScene){
     this.prevScene = prevScene;
    }

}
