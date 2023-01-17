package org.etit.cw_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.etit.cw_5.Classes.Exhibit;
import org.etit.cw_5.Classes.Hall;
import org.etit.cw_5.Classes.Tour;
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

    public static boolean editExhibit(int id, String title, String author, String hall, String type, int eow, LocalDate inPos){
        Integer eow_true=0;
        eow_true = eow==0 ? null : eow;
        String author_true = author.matches("[Н|н]еизвестен") ? "Неизвестен" : author;
        String sql = String.format("UPDATE EXHIBITS SET E_TITLE='%s', E_AUTHOR='%s'," +
                " E_TYPE='%s', E_HALL=(SELECT H_ID FROM HALLS WHERE H_NAME='%s'), E_EOW=%d, E_IN_POS='%s' WHERE E_ID=%d;",
                title, author_true, type, hall, eow_true, Date.valueOf(inPos), id);
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addExhibit(String title, String author, String hall, String type, int eow, LocalDate inPos){
        Integer eow_true=0;
        eow_true = eow==0 ? null : eow;
        String author_true = author.matches("[Н|н]еизвестен") ? "Неизвестен" : author;
        String sql = String.format("INSERT INTO EXHIBITS (E_HALL, E_AUTHOR, E_TITLE, E_EOW, E_IN_POS, E_TYPE)" +
                        " VALUES ((SELECT H_ID FROM HALLS WHERE H_NAME='%s'), '%s', '%s', %d, '%s', '%s');",
                hall, author_true, title, eow_true, Date.valueOf(inPos), type);
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    public static boolean isGuide(int staff){
        String sql = "SELECT S_ID, P_NAME FROM STAFF JOIN POSTS ON S_POST=P_ID WHERE P_NAME='Экскурсовод' AND S_ID="+staff+";";
        try {
            ResultSet rS = statement.executeQuery(sql);
            if(rS.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getStaffFromINN(long inn){
        String sql = "SELECT S_ID FROM STAFF WHERE S_INN="+inn+";";
        try {
            ResultSet rS = statement.executeQuery(sql);
            if(rS.next()){
                return rS.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -100;
    }

    public static ObservableList<Tour> getTours(int staff){
        ObservableList<Tour> tours = FXCollections.observableArrayList();
        String sql = "SELECT T_ID, S_SURNAME, T_DATE, T_PEOPLE, T_TYPE FROM TOURS JOIN STAFF ON T_GUIDE=S_ID WHERE T_GUIDE="+staff+";";
        try {
            ResultSet rS = statement.executeQuery(sql);
            while (rS.next()){
                var x = new Tour();
                x.setId(rS.getInt(1));
                x.setGuide(rS.getString(2));
                x.setDate(rS.getDate(3));
                x.setPeople(rS.getInt(4));
                x.setType(rS.getString(5));
                tours.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    public static ObservableList<Tour> getTours(){
        ObservableList<Tour> tours = FXCollections.observableArrayList();
        String sql = "SELECT T_ID, S_SURNAME, T_DATE, T_PEOPLE, T_TYPE FROM TOURS JOIN STAFF ON T_GUIDE=S_ID";
        try {
            ResultSet rS = statement.executeQuery(sql);
            while (rS.next()){
                var x = new Tour();
                x.setId(rS.getInt(1));
                x.setGuide(rS.getString(2));
                x.setDate(rS.getDate(3));
                x.setPeople(rS.getInt(4));
                x.setType(rS.getString(5));
                tours.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    public static boolean addTour(String guide, LocalDate date, int people, String type){
        String sql = String.format("INSERT INTO TOURS (T_GUIDE, T_DATE, T_PEOPLE, T_TYPE)" +
                        " VALUES ((SELECT S_ID FROM STAFF WHERE S_SURNAME='%s'), '%s', %d, '%s');",
                guide, Date.valueOf(date), people, type);
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateTour(int id, String guide, LocalDate date, int people, String type){
        String sql = String.format("UPDATE TOURS SET T_GUIDE=(SELECT S_ID FROM STAFF WHERE S_SURNAME='%s'), T_DATE='%s', T_PEOPLE=%d, T_TYPE='%s' WHERE T_ID=%d;",
                guide, Date.valueOf(date), people, type, id);
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static ObservableList<String> getGuides(){
        ObservableList<String> guides = FXCollections.observableArrayList();
        String sql = "SELECT S_SURNAME FROM STAFF WHERE S_POST=3";
        try {
            ResultSet rS = statement.executeQuery(sql);
            while (rS.next()){
                var x = new String();
                x = rS.getString(1);
                guides.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guides;
    }

    public static boolean deleteTour(int id) {
        String result;
        try {
            statement.executeUpdate("DELETE FROM TOURS WHERE T_ID = "+id+";");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String showHalls(int tour_id) {
        StringBuilder result= new StringBuilder();
        try {
            ResultSet rS = statement.executeQuery("SELECT H_NAME FROM HALLS" +
                    " JOIN HALLS_TOURS ON HALLS.H_ID=HALLS_TOURS.H_ID WHERE HALLS_TOURS.T_ID="+tour_id+";");
            while (rS.next()){
                result.append(rS.getString(1)).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void addUser(User user){
        String sql ="INSERT INTO USERS (U_USERNAME, U_PASSWORD, U_PRIVILEGE, U_STAFF) VALUES ('"+user.getUsername()+"', " +
                "'"+user.getPassword()+"', '"+user.getPrivilege()+"', "+user.getStaff()+");";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User authorisation(String username, String password){
        String sql = "SELECT U_PRIVILEGE, U_STAFF FROM USERS WHERE U_USERNAME = '"+username+"' AND U_PASSWORD = '"+password+"';";
        User x = new User(username, password);
        try {
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                x.setPrivilege(rs.getByte("U_PRIVILEGE"));
                x.setStaff(rs.getInt("U_STAFF"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }


}
