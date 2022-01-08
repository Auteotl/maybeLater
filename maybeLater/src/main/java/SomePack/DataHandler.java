package SomePack;

import com.example.maybelater.HelloController;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Properties;
import java.time.LocalDate;


public class DataHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);
        return dbConnection;
    }


    public void addNewCatInDB(String catName, int chaptType) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.CATEGORY_TABLE + "(\"" + Const.TYPE_CAT + "\",\"" + Const.TYPE_CHAPT + "\")" +
                " VALUES (?,?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, catName);
        prSt.setInt(2, chaptType);

        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Получение id категории по имени
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

    //Получение имени категории по id
    public String takeCatNameWithCatId(int id) throws SQLException {
        String select = "SELECT" + "(\"" + Const.TYPE_CAT + "\")" + " FROM " + Const.CATEGORY_TABLE +
                " WHERE " + "\"" + Const.ID_CAT + "\" = " + id;
        String catName = "";
        PreparedStatement prSt = dbConnection.prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            catName = resultSet.getString(Const.TYPE_CAT);
        }
        return catName;
    }

    //Получение id раздела по имени
    public int takeChaptWithChaptId(String chaptName) throws SQLException {
        String select = "SELECT" + "(\"" + Const.ID_CHAPT + "\")" + " FROM " + "\"" + Const.CHAPTER_TABLE + "\"" +
                " WHERE " + "\"" + Const.CHAPT_TEXT + "\" = " + "(\'" + chaptName + "\')";
        int chaptId = 0;
        PreparedStatement prSt = dbConnection.prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            chaptId = resultSet.getInt(Const.ID_CHAPT);
        }
        return chaptId;
    }

     //Добавление строки в urlTab
    public void addInfoInURLTab(String url, String urlDescr, int catName) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + "\"" + Const.URLTAB_TABLE + "\"" + "(\"" + Const.SOME_URL + "\",\"" +
                Const.DESCRIPTION + "\",\"" + Const.URL_CAT + "\",\"" + Const.DATE + "\")" +
                "VALUES (?, ?, ?, ?)";
        LocalDate localDate = LocalDate.now();
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, url);
        prSt.setString(2, urlDescr);
        prSt.setInt(3, catName);
        prSt.setObject(4, localDate);

        prSt.executeUpdate();

    }

    //Метод для передачи списка категорий в ChoiseBox
    public ArrayList<String> takeCatNameForChoise() throws SQLException, ClassNotFoundException {
        String select = "SELECT" + "(\"" + Const.TYPE_CAT + "\")" + " FROM " + Const.CATEGORY_TABLE;
        ArrayList<String> catNameForChoise = new ArrayList<>();

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            catNameForChoise.add(resultSet.getString(Const.TYPE_CAT));
        }

        return catNameForChoise;
    }

    //Метод для передачи списка категорий в ChoiseBox для Table
    public ArrayList<String> takeCatNameForChoise(String chapt) throws SQLException, ClassNotFoundException {
        String select = "SELECT" + "(\"" + Const.TYPE_CAT + "\")" + " FROM " + Const.CATEGORY_TABLE
                + " WHERE " + "\"" + Const.TYPE_CHAPT + "\" = " + "(\'" + takeChaptId(chapt) + "\')";
        ArrayList<String> catNameForTable = new ArrayList<>();

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            catNameForTable.add(resultSet.getString(Const.TYPE_CAT));
        }

        return catNameForTable;
    }

    //CHAPTER TABLE
    public int takeChaptId(String chaptName) throws SQLException {
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

    //Добавление нового раздела
    public void addNewChaptInDB(String chaptName) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.CHAPTER_TABLE + "(\"" + Const.CHAPT_TEXT + "\")" +
                " VALUES (?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, chaptName);

        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Метод для передачи списка разделов в ChoiseBox
    public ArrayList<String> takeChaptNameForChoise() throws SQLException, ClassNotFoundException {
        String select = "SELECT" + "(\"" + Const.CHAPT_TEXT + "\")" + " FROM " + Const.CHAPTER_TABLE;
        ArrayList<String> chaptNameForChoise = new ArrayList<>();

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            chaptNameForChoise.add(resultSet.getString(Const.CHAPT_TEXT));
        }

        return chaptNameForChoise;
    }

    //Метод для получения списка категорий (для Treeview)
    public ArrayList<String> takeCatArrayForTree(String chaptName) throws SQLException {
        String select = "SELECT" + "(\"" + Const.TYPE_CAT + "\")" + " FROM " + Const.CATEGORY_TABLE +
                " WHERE " + "\"" + Const.TYPE_CHAPT + "\" = " + "(\'" + takeChaptId(chaptName) + "\')";
        ArrayList<String> catArrayForTree = new ArrayList<>();
        PreparedStatement prSt = dbConnection.prepareStatement(select);
        ResultSet resultSet = prSt.executeQuery();
        while (resultSet.next()) {
            catArrayForTree.add(resultSet.getString(Const.TYPE_CAT));
        }
        return catArrayForTree;
    }

    //Метод удаление раздела
    public void deleteChaptFromDB(String chaptName) throws SQLException {
        String delete = "DELETE FROM " + "\"" + Const.CHAPTER_TABLE + "\""
                + " WHERE " + "\"" + Const.ID_CHAPT + "\"" + " = "
                + takeChaptWithChaptId(chaptName);
        PreparedStatement prSt = dbConnection.prepareStatement(delete);
        prSt.executeUpdate();
    }

    //Delete URL String
    public void deleteStringFromDB(int urlId) throws SQLException {
        String delete = "DELETE FROM " + "\"" + Const.URLTAB_TABLE + "\""
                + " WHERE " + "\"" + Const.ID_URL + "\"" + " = "
                + urlId;
        PreparedStatement prSt = dbConnection.prepareStatement(delete);
        prSt.executeUpdate();
    }

    //Delete Category
    public void deleteCatFromDB(String catName) throws SQLException {
        String delete = "DELETE FROM " + "\"" + Const.CATEGORY_TABLE + "\""
                + " WHERE " + "\"" + Const.ID_CAT + "\"" + " = "
                + takeCatWithCatId(catName);
        PreparedStatement prSt = dbConnection.prepareStatement(delete);
        prSt.executeUpdate();
    }

    //Методы для главной таблицы
    //Список ссылок
    public ArrayList<TableBody> URLListView(String catName) {
        ArrayList<TableBody> urlList = new ArrayList<>();
        try {
            String select = "SELECT * FROM" + "\"" + Const.URLTAB_TABLE + "\""
                    + "WHERE " + "\"" + Const.URL_CAT + "\"" + " = " + takeCatWithCatId(catName);
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            int columns = resultSet.getMetaData().getColumnCount();
            ArrayList<String> buff = new ArrayList<>();
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    String current = resultSet.getString(i);
                    buff.add(current);
                    if (buff.size() == columns) {
                        urlList.add(new TableBody(Integer.parseInt(buff.get(0)), buff.get(1), buff.get(2),
                                Integer.parseInt(buff.get(3)), buff.get(4), Boolean.getBoolean(buff.get(5))));
                        buff.clear();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return urlList;
    }

    //Обновление значения isVisited
    public void updateIsVisited(int idUrl) throws SQLException {
        String update = "UPDATE " + "\"" + Const.URLTAB_TABLE + "\""
                + "SET " + "\"" + Const.ISVISITED + "\"" + " = " + "\'true\'"
                + " WHERE " + "\"" + Const.ID_URL + "\"" + " = " + idUrl;
        PreparedStatement prSt = dbConnection.prepareStatement(update);
        prSt.executeUpdate();
    }

    //Обновление названия категории
    public void updateCategoryName(int idCat, String catName) throws SQLException {
        String update = "UPDATE " + "\"" + Const.CATEGORY_TABLE + "\"" +
                "SET " + "\"" + Const.TYPE_CAT + "\"" + " = " + "\'" + catName + "\'"
                + " WHERE " + "\"" + Const.ID_CAT + "\"" + " = " + idCat;
        PreparedStatement prSt = dbConnection.prepareStatement(update);
        prSt.executeUpdate();
    }
    //Обновление раздела категории
    public void updateCategoryChapt(int idCat, String chaptName) throws SQLException {
        String update = "UPDATE " + "\"" + Const.CATEGORY_TABLE + "\""
                + "SET " + "\"" + Const.TYPE_CHAPT + "\"" + " = " + takeChaptId(chaptName)
                + " WHERE " + "\"" + Const.ID_CAT + "\"" + " = " + idCat;
        PreparedStatement prSt = dbConnection.prepareStatement(update);
        prSt.executeUpdate();
    }
    //Обновление описания в таблице
    public void updateTableDescription(int idUrl, String description) throws SQLException {
        String update = "UPDATE " + "\"" + Const.URLTAB_TABLE + "\""
                + "SET " + "\"" + Const.DESCRIPTION + "\"" + " = " + "\'" + description + "\'"
                + " WHERE " + "\"" + Const.ID_URL + "\"" + " = " + idUrl;
        PreparedStatement prSt = dbConnection.prepareStatement(update);
        prSt.executeUpdate();
    }

    //Обновление url в таблице
    public void updateTableUrl(int idUrl, String url) throws SQLException {
        String update = "UPDATE " + "\"" + Const.URLTAB_TABLE + "\""
                + "SET " + "\"" + Const.SOME_URL + "\"" + " = " + "\'" + url + "\'"
                + " WHERE " + "\"" + Const.ID_URL + "\"" + " = " + idUrl;
        PreparedStatement prSt = dbConnection.prepareStatement(update);
        prSt.executeUpdate();
    }

    //Обновление category в таблице
    public void updateTableCat(int idUrl, int category) throws SQLException {
        String update = "UPDATE " + "\"" + Const.URLTAB_TABLE + "\""
                + "SET " + "\"" + Const.URL_CAT + "\"" + " = " + category
                + " WHERE " + "\"" + Const.ID_URL + "\"" + " = " + idUrl;
        PreparedStatement prSt = dbConnection.prepareStatement(update);
        prSt.executeUpdate();
    }

}
