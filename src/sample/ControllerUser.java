package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerUser {
    MainScene mainScene=null;

    @FXML
    private Button logoutButton;

    @FXML
    void initialize(){
        mainScene=new MainScene();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        mainScene.changeScene(event);
    }
}

