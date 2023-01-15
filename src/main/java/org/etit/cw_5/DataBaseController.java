package org.etit.cw_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.etit.cw_5.Classes.Exhibit;
import org.etit.cw_5.Classes.Hall;
import org.etit.cw_5.Classes.Staff;
import org.etit.cw_5.Classes.User;

import java.sql.*;
import java.time.LocalDate;

public class DataBaseController {
    public static Connection connection;
    public static Statement statement;

    public static void connectToDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //Проверяем наличие JDBC драйвера для работы с БД
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;encrypt=false;databaseName=museum_cw;user=oleg_remote;password=Robot123");//соединениесБД
            System.out.println("Соединение с СУБД выполнено.");
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
    }

    public static ObservableList<Exhibit> getExhibit(){
        ObservableList<Exhibit> exhibits = FXCollections.observableArrayList();
        String sql = "SELECT E_ID, H_NAME, E_TITLE, E_AUTHOR, E_IN_POS, E_EOW, E_TYPE FROM EXHIBITS JOIN HALLS ON E_HALL=H_ID";
        try {
            ResultSet rS = statement.executeQuery(sql);
            while (rS.next()){
                var x = new Exhibit();
                x.setId(rS.getInt(1));
                x.setHall(rS.getString(2));
                x.setTitle(rS.getString(3));
                x.setAuthor((rS.getString(4)==null) ? "Неизвестен" : rS.getString(4));
                x.setInPos(rS.getDate(5));
//                x.setEow((rS.getInt(6)==0) ? -100 : rS.getInt(6));
                x.setEow(rS.getInt(6));
                x.setType(rS.getString(7));
                exhibits.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exhibits;
    }

    public static void deleteExhibit(int id){
        try {
            statement.executeUpdate("DELETE FROM EXHIBITS WHERE E_ID = "+id+";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static byte searchHall(String name){
//        byte x=0;
//        try {
//            ResultSet rs = statement.executeQuery("SELECT H_ID FROM HALLS WHERE H_NAME='"+name+"';");
//            x = (byte) rs.getInt(1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return x;
//    }

//    public static void addExhibit(int id, ){
//        String result;
//        try {
//            statement.executeUpdate("INSERT INTO EXHIBITS VALUES (WHERE E_ID = "+id+";");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static ObservableList<Hall> getHall(){
        ObservableList<Hall> halls = FXCollections.observableArrayList();
        String sql = "SELECT H_ID, H_NAME FROM HALLS";
        try {
            ResultSet rS = statement.executeQuery(sql);
            while (rS.next()){
                var x = new Hall();
                x.setId(rS.getInt(1));
                x.setName(rS.getString(2));
                halls.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return halls;
    }

    public static boolean deleteHall(int id){
        String result;
        try {
            statement.executeUpdate("DELETE FROM HALLS WHERE H_ID = "+id+";");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editExhibit(int id, String title, String author, int hall, String type, int eow, LocalDate inPos){
        Integer eow_true=0;
        eow_true = eow==0 ? null : eow;
        String sql = String.format("UPDATE EXHIBITS SET E_TITLE='%s', E_AUTHOR='%s'," +
                " E_TYPE='%s', E_HALL=%d, E_EOW=%d, E_IN_POS='%s' WHERE E_ID=%d;",
                title, author, type, hall, eow_true, Date.valueOf(inPos), id);
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addHall(int id, String hallName){
        String sql ="INSERT INTO HALLS (H_ID, H_NAME) VALUES ("+id+", '"+hallName+"');";
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addExhibit(String title, String author, int hall, String type, int eow, LocalDate inPos){
        Integer eow_true=0;
        eow_true = eow==0 ? null : eow;
        String sql = String.format("INSERT INTO EXHIBITS (E_HALL, E_AUTHOR, E_TITLE, E_EOW, E_IN_POS, E_TYPE)" +
                        " VALUES (%d, '%s', '%s', %d, '%s', '%s');",
                hall, author, title, eow_true, Date.valueOf(inPos), type);
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean editHall(int id, int editId, String hallName){
        String sql ="UPDATE HALLS SET H_NAME='"+hallName+"', H_ID="+editId+" WHERE H_ID="+id+";";
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static void addUser(User user){
        String sql ="INSERT INTO USERS (U_USERNAME, U_PASSWORD, U_PRIVILEGE) VALUES ('"+user.getUsername()+"', " +
                "'"+user.getPassword()+"', '"+user.getPrivilege()+"');";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int authorisation(String username, String password){
        String sql = "SELECT U_PRIVILEGE FROM USERS WHERE U_USERNAME = '"+username+"' AND U_PASSWORD = '"+password+"';";
        int x = -1;
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                x = rs.getByte("U_PRIVILEGE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }
}
