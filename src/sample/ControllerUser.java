package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.*;

public class ControllerUser {
    Connection con=null;
    private String indexEvent;
    private String query_event="Select * from wydarzenia";

    public ControllerUser(){
        this.con=ConnectionDB.connection();
    }

    MainScene mainScene=null;
    @FXML
    private ComboBox<String> foodComboBox;

    @FXML
    private ComboBox<String> selectTypeComboBox;

    @FXML
    private ComboBox<String> selectEventComboBox;

    @FXML
    private TextArea agendaTextArea;

    @FXML
    private Label terminLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addEventButton;

    @FXML
    void initialize(){
        mainScene=new MainScene();

        try {
            Statement wydarzenia=con.createStatement();
            ResultSet resultSet=wydarzenia.executeQuery(query_event);
            while (resultSet.next()){
                selectEventComboBox.getItems().add(resultSet.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        selectTypeComboBox.getItems().addAll("Sluchacz","Autor","Sponsor","Organizator");
        foodComboBox.getItems().addAll("Bez preferencji","Wegetarianskie","Bez glutenu");
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        mainScene.changeScene(event);
    }

    @FXML
    void addEvent(ActionEvent event) {
        String query="INSERT INTO zapisy ("
                + " id_wydarzenia,"
                + " id_uzytkownika,"
                + " typ_uczestictwa,"
                + " wyzywienie) VALUES ("
                + "?, ?, ?, ?)";
        try{
            PreparedStatement addEventPreparedStatement=con.prepareStatement(query);
            addEventPreparedStatement.setString(1,indexEvent);
            addEventPreparedStatement.setString(2,Controller.getUserIndex());
            addEventPreparedStatement.setString(3,selectTypeComboBox.getValue());
            addEventPreparedStatement.setString(4,foodComboBox.getValue());
            addEventPreparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    @FXML
    void selectEvent(ActionEvent event) {
        ResultSet resultSet=null;
        try{
            PreparedStatement preparedStatement=con.prepareStatement("Select agenda,termin_wydarzenia,id_wydarzenia from wydarzenia where nazwa_wydarzenia=?");
            preparedStatement.setString(1,selectEventComboBox.getValue());
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                agendaTextArea.setText(resultSet.getString("agenda"));
                terminLabel.setText(resultSet.getString("termin_wydarzenia"));
                indexEvent=resultSet.getString("id_wydarzenia");

            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}

