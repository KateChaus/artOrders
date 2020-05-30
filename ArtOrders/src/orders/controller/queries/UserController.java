package orders.controller.queries;

import orders.controller.utils.DBConnector;
import orders.model.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class UserController {


    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static User getByID(int id) {
        User user = null;
        String query = "SELECT * FROM user WHERE id=\'" + id + "\'";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String userLogin = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                String info = resultSet.getString("info");
                Boolean isArtist = resultSet.getBoolean("isArtist");
                String avatar = resultSet.getString("image");
                user = new User(id, userLogin, userPassword, info, avatar, isArtist);

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
        return user;
    }

    public static ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("username");
                String password = resultSet.getString("password");
                String info = resultSet.getString("info");
                Boolean isArtist = resultSet.getBoolean("isArtist");
                String avatar = resultSet.getString("image");
                users.add(new User(id, login, password, info,avatar,isArtist));
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

        return users;
    }

    public static void setAvatar(User user){
        String query = "UPDATE user SET image= \'" + user.getAvatar()+ "\' where id = \'"+user.getId()+"\';";
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
    };

    public static void editProfile(User user) {

        String query = "UPDATE  user SET info = \'" + user.getInfo() + "\', isArtist = " + user.isArtist() + " where id = \'"+user.getId()+"\';";
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

    public static void changePassword(User user) {

        String query = "UPDATE user SET password= \'" + user.getPassword()+ "\' where id = \'"+user.getId()+"\';";
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

    public static void register(String login, String password, String info, Boolean isArtist) {
        String query = "INSERT INTO user (username, password, info, isArtist) VALUES (\'" + login + "\',\'" + password + "\',\'" + info + "\'," + isArtist + ");";
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

    public static User login(String login, String password) {
        User user = null;
        String query = "SELECT * FROM user WHERE username=\'" + login+"\'";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()){
                int id = resultSet.getInt("id");
                String userLogin = resultSet.getString("username");
                String userPassword = resultSet.getString("password");
                String info = resultSet.getString("info");
                Boolean isArtist = resultSet.getBoolean("isArtist");
                String avatar = resultSet.getString("image");
                if (userPassword.equals(password)) {
                    user = new User(id, userLogin, userPassword, info, avatar, isArtist);
                }}


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
        return user;
    }
}
