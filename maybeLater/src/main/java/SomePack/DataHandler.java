package SomePack;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Properties;
import java.time.LocalDate;


public class DataHandler extends Configs{

    Connection dbConnection;
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);
        return dbConnection;
    }


    public void addNewCatInDB (String catName, int chaptType) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.CATEGORY_TABLE+ "(\"" + Const.TYPE_CAT + "\",\"" + Const.TYPE_CHAPT+ "\")" +
                " VALUES (?,?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1,catName);
        prSt.setInt(2,chaptType);

        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int takeCatWithCatId(String catName) throws SQLException {
        String select = "SELECT" + "(\"" + Const.ID_CAT + "\")" + " FROM " + Const.CATEGORY_TABLE +
                " WHERE " + "\"" + Const.TYPE_CAT + "\" = " + "(\'" + catName + "\')";
        int catId = 0;
        PreparedStatement prSt = dbConnection.prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            catId = resultSet.getInt(Const.ID_CAT);
        }
        return catId;
    }

//Добавление строки в urlTab
    public void addInfoInURLTab (String url, String urlDescr, int catName) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + "\"" + Const.URLTAB_TABLE + "\"" + "(\"" + Const.SOME_URL + "\",\"" +
                Const.DESCRIPTION + "\",\"" + Const.URL_CAT + "\",\"" + Const.DATE + "\")" +
                "VALUES (?, ?, ?, ?)";
        LocalDate localDate = LocalDate.now();
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1,url);
        prSt.setString(2,urlDescr);
        prSt.setInt(3,catName);
        prSt.setObject(4, localDate);

        prSt.executeUpdate();

    }

// Метод для передачи списка категорий в ChoiseBox
    public ArrayList<String> takeCatNameForChoise () throws SQLException, ClassNotFoundException {
        String select = "SELECT" + "(\"" + Const.TYPE_CAT + "\")" + " FROM " + Const.CATEGORY_TABLE;
        ArrayList<String> catNameForChoise = new ArrayList<>();

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while(resultSet.next()){
            catNameForChoise.add(resultSet.getString(Const.TYPE_CAT));
        }

        return catNameForChoise;
    }
//CHAPTER TABLE
    public int takeChaptWithCatId(String chaptName) throws SQLException {
        String select = "SELECT" + "(\"" + Const.ID_CHAPT + "\")" + " FROM " + Const.CHAPTER_TABLE +
                " WHERE " + "\"" + Const.CHAPT_TEXT + "\" = " + "(\'" + chaptName + "\')";
        int chaptId = 0;
        PreparedStatement prSt = dbConnection.prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            chaptId = resultSet.getInt(Const.ID_CHAPT);
        }
        return chaptId;
    }
    public void addNewChaptInDB (String chaptName) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.CHAPTER_TABLE+ "(\"" + Const.CHAPT_TEXT + "\")" +
                " VALUES (?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1,chaptName);

        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> takeChaptNameForChoise () throws SQLException, ClassNotFoundException {
        String select = "SELECT" + "(\"" + Const.CHAPT_TEXT + "\")" + " FROM " + Const.CHAPTER_TABLE;
        ArrayList<String> chaptNameForChoise = new ArrayList<>();

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while(resultSet.next()){
            chaptNameForChoise.add(resultSet.getString(Const.CHAPT_TEXT));
        }

        return chaptNameForChoise;
    }

    public ArrayList<String> takeCatArrayForTree (String chaptName) throws SQLException {
        String select = "SELECT" + "(\"" + Const.TYPE_CAT + "\")" + " FROM " + Const.CATEGORY_TABLE  +
                " WHERE " + "\"" + Const.TYPE_CHAPT + "\" = " + "(\'" + takeChaptWithCatId(chaptName) + "\')";
        ArrayList<String> catArrayForTree= new ArrayList<>();
        PreparedStatement prSt = dbConnection.prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while(resultSet.next()){
            catArrayForTree.add(resultSet.getString(Const.TYPE_CAT));
        }
        return catArrayForTree;
    }

}
