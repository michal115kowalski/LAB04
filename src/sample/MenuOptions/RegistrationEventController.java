package sample.MenuOptions;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.ConnectionDB;
import java.sql.*;

public class RegistrationEventController {
    private Connection conn;
    private RegistrationRecords registrationRecords;


    public RegistrationEventController(){
        this.conn= ConnectionDB.connection();
    }

    @FXML
    private Button rejectButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TableColumn<RegistrationRecords,String> columnIdEvent;

    @FXML
    private TableView<RegistrationRecords> tableView;

    @FXML
    private TableColumn<RegistrationRecords,String> columnIdRegistration;

    @FXML
    private TableColumn<RegistrationRecords,String> columnIdUser;

    @FXML
    private TableColumn<RegistrationRecords,String> columnFoodType;

    @FXML
    private TableColumn<RegistrationRecords,String> columnFood;

    @FXML
    void initialize(){
        rejectButton.setDisable(true);
        confirmButton.setDisable(true);
        //ustawiam kolumny w tabeli
        columnIdRegistration.setCellValueFactory(new PropertyValueFactory<>("id_zapisu"));
        columnIdEvent.setCellValueFactory(new PropertyValueFactory<>("id_wydarzenia"));
        columnIdUser.setCellValueFactory(new PropertyValueFactory<>("id_uzytkownika"));
        columnFoodType.setCellValueFactory(new PropertyValueFactory<>("typ_uczestnictwa"));
        columnFood.setCellValueFactory(new PropertyValueFactory<>("wyzywienie"));
        tableView.setItems(records());


    }

    public ObservableList<RegistrationRecords> records(){
        ObservableList<RegistrationRecords> records= FXCollections.observableArrayList();
        try{
            String query="Select id_zapisu,id_wydarzenia,id_uzytkownika,typ_uczestictwa,wyzywienie from zapisy where status=?";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,"Oczekujace");
            ResultSet resultSet=preparedStatement.executeQuery();
            RegistrationRecords registrationRecords;
            while (resultSet.next()){
                registrationRecords=new RegistrationRecords(
                        resultSet.getInt("id_zapisu"),
                        resultSet.getInt("id_wydarzenia"),
                        resultSet.getInt("id_uzytkownika"),
                        resultSet.getString("typ_uczestictwa"),
                        resultSet.getString("wyzywienie"));
                records.add(registrationRecords);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return records;
    }

    @FXML
    void rejectRecord(ActionEvent event) {
        String query=query="UPDATE zapisy set status='odrzucony' where id_zapisu=?";
        manageRecords(query);
    }

    @FXML
    void confirmRecord(ActionEvent event) {
        String query="UPDATE zapisy set status='zatwierdzony' where id_zapisu=?";
        manageRecords(query);
    }

    public void manageRecords(String query){
        rejectButton.setDisable(false);
        confirmButton.setDisable(false);
        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setInt(1,registrationRecords.getId_zapisu());
            preparedStatement.executeUpdate();
            tableView.getItems().remove(registrationRecords);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void example(){
        registrationRecords=tableView.getSelectionModel().getSelectedItem();
        if(registrationRecords!=null || records().size()!=0){
            confirmButton.setDisable(false);
            rejectButton.setDisable(false);
        }else {
            confirmButton.setDisable(true);
            rejectButton.setDisable(true);
        }

    }
}
