package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
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
    private boolean disname=true,dislastname=true,dislogin=true,dispassword=true,dispasstwo=true,dismail=true;
    private String name,lastName,login,password,repeatPassword,email,type;
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
            sendingEmail(emailField.getText());
            clear();

        }catch (SQLException  | NullPointerException e){
            e.printStackTrace();
        }
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
        getData();

        if(nameField.isFocused()){
             pattern=Pattern.compile("[A-Z][a-z]{3,15}");
             matcher=pattern.matcher(name);
            if(!matcher.matches() || name.isEmpty()){
                nameField.setStyle("-fx-background-color: #ff4500;");
                disname=true;}
            else {
                nameField.setStyle("-fx-background-color: #78dead;");
                disname=false;}

        }

        if(lastNameField.isFocused()){
            Pattern pattern=Pattern.compile("[A-Z][a-z]{3,15}");
            Matcher matcher=pattern.matcher(lastName);
            if(!matcher.matches() || lastName.isEmpty()){
                lastNameField.setStyle("-fx-background-color: #ff4500;");
                dislastname=true;}
            else {
                lastNameField.setStyle("-fx-background-color: #78dead;");
                dislastname=false;}
        }

        if(loginField.isFocused()) {
            type="Select * from uzytkownicy where login=?";
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{6,15}$");
            Matcher matcher = pattern.matcher(login);

            if (!matcher.matches() || checkLogin(login,type) ||login.isEmpty()) {
                loginField.setStyle("-fx-background-color: #ff4500;");
                dislogin = true;
            } else {
                loginField.setStyle("-fx-background-color: #78dead;");
                dislogin=false;}
        }
        if(passwordField.isFocused()) {
            Pattern pattern = Pattern.compile("^[a-z+A-Z+0-9]{6,15}$");
            Matcher matcher = pattern.matcher(password);
            if (!matcher.matches() || password.isEmpty()) {
                passwordField.setStyle("-fx-background-color: #ff4500;");
                dispassword = true;
            }else {
                passwordField.setStyle("-fx-background-color: #78dead;");
                dispassword=false;}
        }

        if(repeatPasswordField.isFocused()) {
            if (!passwordField.getText().equals(repeatPassword) || repeatPassword.isEmpty()) {
                repeatPasswordField.setStyle("-fx-background-color: #ff4500;");
                dispasstwo = true;
            }else{
                repeatPasswordField.setStyle("-fx-background-color: #78dead;");
                dispasstwo=false;}
        }

        if(emailField.isFocused()){
            type="Select * from uzytkownicy where email=?";
            String email="^[a-zA-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            pattern=Pattern.compile(email,Pattern.CASE_INSENSITIVE);
            matcher=pattern.matcher(emailField.getText());
            if(!matcher.matches() || checkLogin(email,type) ||email.isEmpty()){
                emailField.setStyle("-fx-background-color: #ff4500;");
                dismail = true;
            }else {
                emailField.setStyle("-fx-background-color: #78dead;");
                dismail=false;}
        }
        boolean isDisabled=(disname || dislastname || dislogin||dispasstwo||dispassword||dismail);
        registerButton.setDisable(isDisabled);
    }

    public void sendingEmail(String toEmail){
        final String user="michal00040@gmail.com";
        final String password="korale343";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true"); //SMTP.AUTH pozwala na autentykację użytkownika korzystając z polecenia AUTH
        properties.put("mail.smtp.starttls.enable", "true"); //dzięki czemu ustanawiamy szyfrowanie TLS w połączeniu sieciowym. Zapewnia nam to poufność danych.
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); //ustawiam host i port

        Session session=Session.getInstance(properties,new javax.mail.Authenticator(){ //inicjalizacja sesji
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,password);
            }
        });
            try{
                Message message=new MimeMessage(session); //tworzenie wiadomosci email
                message.setFrom(new InternetAddress(user));
                message.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
                message.setSubject("Rejestracje na wydarzenie");
                message.setText("Informuje ze zostales zarejserowany w serwisie");
                Transport.send(message);
                JOptionPane.showMessageDialog(null,"Wiadomosc zostala wyslana!");
            }catch (MessagingException e){
                e.printStackTrace();
            }
    }

    @FXML
    void backLogin(ActionEvent event) throws IOException{
        mainScene.changeScene(event);
    }

    public void changeScene(ActionEvent event) throws IOException{
        mainScene.changeScene(event);
    }
    public boolean checkLogin(String value,String query){
        boolean is =false;
        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,value);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) is=true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return is;
    }

    public void clear(){
        nameField.setText("");
        lastNameField.setText("");
        loginField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
        emailField.setText("");
        registerButton.setDisable(true);
    }

}
