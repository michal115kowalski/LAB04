package sample;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;

public class ConnectionDB {

    public static Connection connection(){
        try{
            Connection conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/wydarzenie","root","");
            Class.forName("com.mysql.jdbc.Driver");
            return conn;

        }catch (ClassNotFoundException | SQLException e){
            return null;
        }
    }
}
