package SomePack;

import java.sql.*;
import java.util.Properties;


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



       /* String url = "jdbc:postgresql://localhost/testdata";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","Assassin1");

       Connection conn = DriverManager.getConnection(url, props);

 if ( != null) {
        System.out.println("Connected to the database!");
    } else {
        System.out.println("Failed to make connection!");
    }
        */



      // String SQL_SELECT = "Select * from category";

      //PreparedStatement preparedStatement = conn.prepareStatement();


        //ResultSet resultSet = preparedStatement.executeQuery();
        //System.out.println(resultSet.next());


    public void addNewCatInDB (String catName) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.CATEGORY_TABLE+ "(\"" + Const.TYPE_CAT + "\")" +
                " VALUES (?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1,catName);

        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
