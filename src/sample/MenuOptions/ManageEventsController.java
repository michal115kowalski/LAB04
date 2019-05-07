package sample.MenuOptions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.ConnectionDB;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class ManageEventsController {

    private Connection conn;
    private int idEvent;

    public ManageEventsController(){
        this.conn= ConnectionDB.connection();
    }

    @FXML
    private CheckBox allowEventDelete;

    @FXML
    private TextArea selectEventAgenda;

    @FXML
    private Button deleteEventButton;

    @FXML
    private Button modifyEventButton;

    @FXML
    private ChoiceBox<String> selectEventChoiceBox;

    @FXML
    private DatePicker selectEventDate;

    @FXML
    private TextField selectEventName;

    @FXML
    void initialize(){
        modifyEventButton.setDisable(true);
        getNames();
        selectEventChoiceBox.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)-> showData(newvalue));
    }

    @FXML
    void modifyEvent(ActionEvent event) {
        LocalDate localDate=selectEventDate.getValue();

        try{
            PreparedStatement preparedStatement=conn.prepareStatement("Update wydarzenia SET nazwa_wydarzenia=?, agenda=?, termin_wydarzenia=? where id_wydarzenia='"+idEvent+"'");
            preparedStatement.setString(1,selectEventName.getText());
            preparedStatement.setString(2,selectEventAgenda.getText());
            preparedStatement.setString(3,localDate.toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
        clear();

        getNames();

    }



    @FXML
    void deleteEvent(ActionEvent event) {
        if(allowEventDelete.isSelected()) {
            try {
                String a = "Delete FROM wydarzenia WHERE id_wydarzenia='" + idEvent + "'";
                Statement statement = conn.createStatement();
                statement.executeUpdate(a);

            } catch (SQLException e) {
                System.out.println(e);
            }
            selectEventChoiceBox.getItems().remove(selectEventChoiceBox.getValue());
            clear();
            getNames();
        }else   JOptionPane.showMessageDialog(null,"Prosze potwierdzic!");

        modifyEventButton.setDisable(true);
    }



    public void showData(String selectEVT){
        modifyEventButton.setDisable(false);
        ResultSet resultSet=null;
        try{
            PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM wydarzenia WHERE nazwa_wydarzenia=?");
            preparedStatement.setString(1,selectEVT);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                idEvent=resultSet.getInt("id_wydarzenia");
                selectEventName.setText(resultSet.getString(2));
                selectEventAgenda.setText(resultSet.getString(3));
                selectEventDate.setValue(resultSet.getDate(4).toLocalDate());
            }


        }catch (SQLException e){
            System.out.println(e);
        }

    }
    public void clear(){

        selectEventName.setText(null);
        selectEventDate.setValue(null);
        selectEventAgenda.setText(null);
    }

    public void getNames(){
        selectEventChoiceBox.getItems().clear();
        modifyEventButton.setDisable(true);

        try{
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery("select nazwa_wydarzenia from wydarzenia");
            while (resultSet.next()){
                selectEventChoiceBox.getItems().add(resultSet.getString("nazwa_wydarzenia"));
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

}

