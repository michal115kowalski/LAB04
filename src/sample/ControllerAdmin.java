package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerAdmin {
    AnchorPane newLoadedPane;
    MainScene mainScene;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button logoutButton;

    @FXML
    private MenuBar myMenuBar;

    @FXML
    void initialize(){
        mainScene=new MainScene();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        mainScene.changeScene(event);
    }

    @FXML
    void addUser(ActionEvent event) throws IOException {
        anchorPane.getChildren().clear();
        newLoadedPane =  FXMLLoader.load(getClass().getResource("Register.fxml"));
        anchorPane.getChildren().add(newLoadedPane);

    }

    @FXML
    void deleteUser(ActionEvent event) throws IOException {
        anchorPane.getChildren().clear();
        newLoadedPane =  FXMLLoader.load(getClass().getResource("MenuOptions/DeleteUser.fxml"));
        anchorPane.getChildren().add(newLoadedPane);



    }

    @FXML
    void addEvent(ActionEvent event) throws IOException {
        anchorPane.getChildren().clear();
        newLoadedPane =  FXMLLoader.load(getClass().getResource("MenuOptions/Events.fxml"));
        anchorPane.getChildren().add(newLoadedPane);

    }

    @FXML
    void manageEvent(ActionEvent event) throws IOException {
        anchorPane.getChildren().clear();
        newLoadedPane =  FXMLLoader.load(getClass().getResource("MenuOptions/ManageEvents.fxml"));
        anchorPane.getChildren().add(newLoadedPane);


    }

    @FXML
    void manageRecord(ActionEvent event) throws IOException {
        anchorPane.getChildren().clear();
        newLoadedPane =  FXMLLoader.load(getClass().getResource("MenuOptions/RegistrationEvent.fxml"));
        anchorPane.getChildren().add(newLoadedPane);

    }
}
