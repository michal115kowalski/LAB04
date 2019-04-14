package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javax.swing.*;
import java.sql.Connection;
import java.io.IOException;
import java.sql.*;

public class Controller {
    private int checkLogin=0;
    private String data=null;
    Connection conn=null;
    private String text;

    public Controller(){
        this.conn=ConnectionDB.connection();
    }

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private Button buttonLogIn;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField maskPasswordField;

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    void initialize(){
        maskPasswordField.setVisible(false);
        buttonLogIn.setDisable(true);
    }

    public void onKeyReleased(){
        boolean isDisable=(loginTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()); //|| (loginTextField.getText().isEmpty() || maskPasswordField.getText().isEmpty());
        buttonLogIn.setDisable(isDisable);
    }

    @FXML
    void LogIn(ActionEvent event) throws IOException {
        String username=loginTextField.getText();
        String password=passwordTextField.getText();
        boolean admin=adminCheckBox.isSelected();
        if(admin) login(username,password,"admin",event);
        else login(username,password,"user",event);
    }


    @FXML
    void showPassword(ActionEvent event) {
        if (showPasswordCheckBox.isSelected()) {
            text=passwordTextField.getText();
            maskPasswordField.setVisible(true);
            maskPasswordField.setText(text);
        }else {
            text=maskPasswordField.getText();
            maskPasswordField.setVisible(false);
            passwordTextField.setText(text);
        }
    }

    @FXML
    void changeScene(ActionEvent event) throws IOException {
        loginAdmin(event,"Register.fxml");
    }

    public void login(String username,String password,String uprawnienia,ActionEvent event) throws IOException{
        ResultSet myRes=null;
        try{
            PreparedStatement preparedStatement=conn.prepareStatement("Select login,haslo from uzytkownicy where login=? and haslo=? and uprawnienia=?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,uprawnienia);
            myRes=preparedStatement.executeQuery();

            if (myRes.next())
            {
                if(uprawnienia.equals("admin")) {
                    loginAdmin(event,"Admin.fxml");
                }else loginAdmin(event,"User.fxml");
            }else {
                JOptionPane.showMessageDialog(null,"Podano złe dane do logowania!");
                checkLogin++;
                System.out.println(checkLogin);
                if(checkLogin==3){
                    JOptionPane.showMessageDialog(null,"Wyczerpano limit prób");
                    myRes.close();
                    preparedStatement.close();
                    conn.close();
                    System.exit(0);
                }

            }
        }catch (SQLException e){
            System.out.println(e);
        }

    }

   public void loginAdmin(ActionEvent event,String controller) throws IOException{
       Parent adminParent= FXMLLoader.load(getClass().getResource(controller));
       Scene admnScene=new Scene(adminParent);
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(admnScene);
       window.show();
   }
}
