package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    Pattern pattern;
    Matcher matcher;
    Connection conn=null;
    LocalDate localDate=null;
    private String name,lastName,login,password,repeatPassword,email;
    MainScene mainScene;

    public RegisterController(){
        this.conn=ConnectionDB.connection();
    }

    @FXML
    private Button registerButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private void initialize(){
        registerButton.setDisable(true);
        mainScene=new MainScene();
    }

    @FXML
    void registerAction(ActionEvent event) throws IOException{
        getData();
        String queryInsert="INSERT INTO uzytkownicy ("
                + " imie,"
                + " nazwisko,"
                + " login,"
                + " haslo,"
                + " email,"
                + " data_rejestracji ) VALUES ("
                + "?, ?, ?, ?, ?,?)";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(queryInsert);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,login);
            preparedStatement.setString(4,password);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

        }catch (SQLException  | NullPointerException e){
            e.printStackTrace();
        }
        changeScene(event);
    }

    public void getData(){
        name=nameField.getText();
        lastName=lastNameField.getText();
        login=loginField.getText();
        password=passwordField.getText();
        repeatPassword=repeatPasswordField.getText();
        email=emailField.getText();
        localDate=LocalDate.now();
    }

    public void keyReleasedProperty(){
        ResultSet myRes=null;
        getData();
        boolean isDisabled=(name.isEmpty() || lastName.isEmpty() || login.isEmpty()||password.isEmpty()||repeatPassword.isEmpty()||email.isEmpty());

        if(nameField.isFocused()){
             pattern=Pattern.compile("[A-Z][a-z]{3,15}");
             matcher=pattern.matcher(nameField.getText());
            if(!matcher.matches()){
                nameField.setStyle("-fx-background-color: #ff4500;");
                isDisabled=true;}
            else nameField.setStyle("-fx-background-color: #78dead;");
        }

        if(lastNameField.isFocused()){
            Pattern pattern=Pattern.compile("[A-Z][a-z]{3,15}");
            Matcher matcher=pattern.matcher(lastNameField.getText());
            if(!matcher.matches()){
                lastNameField.setStyle("-fx-background-color: #ff4500;");
                isDisabled=true;}
            else lastNameField.setStyle("-fx-background-color: #78dead;");
        }

        if(loginField.isFocused()) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{6,15}$");
            Matcher matcher = pattern.matcher(loginField.getText());

            if (!matcher.matches()) {
                loginField.setStyle("-fx-background-color: #ff4500;");
                isDisabled = true;
            } else loginField.setStyle("-fx-background-color: #78dead;");
        }
        if(passwordField.isFocused()) {
            Pattern pattern = Pattern.compile("^[a-z+A-Z+0-9]{6,15}$");
            Matcher matcher = pattern.matcher(passwordField.getText());
            if (!matcher.matches()) {
                passwordField.setStyle("-fx-background-color: #ff4500;");
                isDisabled = true;
            } else passwordField.setStyle("-fx-background-color: #78dead;");
        }

        if(repeatPasswordField.isFocused()) {
            if (!passwordField.getText().equals(repeatPasswordField.getText())) {
                repeatPasswordField.setStyle("-fx-background-color: #ff4500;");
                isDisabled = true;
            }else repeatPasswordField.setStyle("-fx-background-color: #78dead;");
        }

        if(emailField.isFocused()){
            String email="^[a-zA-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            pattern=Pattern.compile(email,Pattern.CASE_INSENSITIVE);
            matcher=pattern.matcher(emailField.getText());
            if(!matcher.matches()){
                emailField.setStyle("-fx-background-color: #ff4500;");
                isDisabled = true;
            } else emailField.setStyle("-fx-background-color: #78dead;");
        }
        registerButton.setDisable(isDisabled);
    }
/*
    public void sendingEmail(){
        final String user="michal00040@gmail.com";
        final String password="korale343";

        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", user);

        Session session=Session.getDefaultInstance(properties);
        try{
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress("michal00040@gmail.com"));
            message.setSubject("Ping");
            message.setText("Siema tu majkel");
            Transport t = session.getTransport("smtp");
            t.connect(user, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            System.out.println("Wiadomosc zostala wyslana");
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }*/

    @FXML
    void backLogin(ActionEvent event) throws IOException{
        mainScene.changeScene(event);
    }

    public void changeScene(ActionEvent event) throws IOException{
        mainScene.changeScene(event);

    }

}
