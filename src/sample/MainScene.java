package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScene{
        public void changeScene(ActionEvent event) throws IOException {
            Parent parent= FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene=new Scene(parent);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
}
