package orders.controller.queries;

import orders.controller.utils.DBConnector;
import orders.model.entities.Gallery;
import orders.model.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class GalleryController {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static ArrayList<Gallery> getGalleries(User user) {
        ArrayList<Gallery> galleries = new ArrayList<>();
        String query = "SELECT * FROM gallery WHERE artistID=\'" + user.getId() + "\';";
        System.out.println(query);
        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String site = resultSet.getString("site");
                String link = resultSet.getString("link");
                Gallery gallery = new Gallery(id, site, link, user);
                galleries.add(gallery);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException se) {
            }
            try {
                statement.close();
            } catch (SQLException se) {
            }
            try {
                resultSet.close();
            } catch (SQLException se) {
            }
        }
        return galleries;
    }

    public static void addGallery(String site, String link, User user) {
        String query = "INSERT INTO gallery (site, link, artistID) VALUES (\'" + site + "\',\'" + link + "\',\'" + user.getId() + "\');";
        System.out.println(query);
        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException se) {
            }
            try {
                statement.close();
            } catch (SQLException se) {
            }

        }
    }

    public static void remove(Gallery gallery) {
        String query = "DELETE FROM gallery WHERE id = \'" + gallery.getId() + "\'";

        System.out.println(query);
        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException se) {
            }
            try {
                statement.close();
            } catch (SQLException se) {
            }

        }
    }

}
