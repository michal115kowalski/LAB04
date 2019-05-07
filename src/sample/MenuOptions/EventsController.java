package sample.MenuOptions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.ConnectionDB;
import java.sql.*;
import java.time.LocalDate;

public class EventsController {
    Connection conn;

    public EventsController(){
        this.conn= ConnectionDB.connection();
    }

    @FXML
    private TextArea agendaEvent;

    @FXML
    private DatePicker dateEvent;

    @FXML
    private TextField nameEvent;

    @FXML
    private Button addEventButton;

    @FXML
    void initialize(){
        addEventButton.setDisable(true);
    }

    @FXML
    void addEvent(ActionEvent event) {
        ResultSet resultSet=null;
        String addEventQuery="INSERT INTO wydarzenia ("
                + " nazwa_wydarzenia,"
                + " agenda,"
                + " termin_wydarzenia ) VALUES ("
                + "?, ?, ?)";
        LocalDate localDate=dateEvent.getValue();

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(addEventQuery);
            preparedStatement.setString(1,nameEvent.getText());
            preparedStatement.setString(2,agendaEvent.getText());
            preparedStatement.setString(3,localDate.toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
        clear();
    }

    @FXML
    void releasedKey(){
        addEventButton.setDisable(nameEvent.getText().trim().isEmpty() || agendaEvent.getText().trim().isEmpty() || dateEvent.getValue()==null);
    }

    public void clear(){
        nameEvent.setText("");
        agendaEvent.setText("");
        dateEvent.getEditor().clear();
    }
}
