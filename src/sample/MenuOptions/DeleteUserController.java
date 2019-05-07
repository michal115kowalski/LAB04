package sample.MenuOptions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.ConnectionDB;

import java.sql.*;

public class DeleteUserController {
    Connection conn;

    public DeleteUserController() {
        this.conn = ConnectionDB.connection();
    }

    private String textDelete;

    @FXML
    private TextField deleteTextField;
    @FXML
    private Button changePasswordButton;
    @FXML
    private TextField newPasswordField;

    @FXML
    private Button deleteButton;

    @FXML
    private ChoiceBox<String> choiceBoxDelete;

    @FXML
    void initialize() {
        changePasswordButton.setDisable(true);
        deleteButton.setDisable(true);
        ResultSet resultSet = null;
        String query = "Select * from uzytkownicy";
        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                choiceBoxDelete.getItems().add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        choiceBoxDelete.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> textDelete = newvalue);
    }

    @FXML
    void deleteButtonAction(ActionEvent event) {
        String queryDelete = "Delete from uzytkownicy where " + textDelete + "=?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(queryDelete);
            preparedStatement.setString(1, deleteTextField.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
        deleteTextField.setText("");
        deleteButton.setDisable(true);
    }

    @FXML
    void changePassword(ActionEvent event) {
        String queryChangePassword="Update uzytkownicy set haslo=? where "+textDelete+"=?";
        try{
            PreparedStatement preparedStatement=conn.prepareStatement(queryChangePassword);
            preparedStatement.setString(1,newPasswordField.getText());
            preparedStatement.setString(2,deleteTextField.getText());
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
        newPasswordField.setText("");
        changePasswordButton.setDisable(true);

    }

    public void keyReleasedProperty() {
        boolean isDisabledDelete=deleteTextField.getText().isEmpty();
        boolean isDisabled = (deleteTextField.getText().isEmpty() || newPasswordField.getText().isEmpty());
        changePasswordButton.setDisable(isDisabled);
        deleteButton.setDisable(isDisabledDelete);
    }
}

