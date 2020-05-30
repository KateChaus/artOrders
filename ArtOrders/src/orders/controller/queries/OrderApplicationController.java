package orders.controller.queries;


import orders.controller.utils.DBConnector;
import orders.model.entities.OrderApplication;
import orders.model.entities.User;
import orders.model.enums.OrderStyle;
import orders.model.enums.OrderType;

import java.sql.*;
import java.util.ArrayList;

public class OrderApplicationController {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static OrderApplication getByID(int id) {
        OrderApplication orderApplication = null;
        String query = "SELECT * FROM orderApplication JOIN artists ON orderApplication.id = artists.orderApplicationID WHERE orderApplication.id=\'" + id + "\'";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                OrderType orderType = OrderType.valueOf(resultSet.getString("orderType").toUpperCase());
                OrderStyle orderStyle = OrderStyle.valueOf(resultSet.getString("orderStyle").toUpperCase());
                String info = resultSet.getString("info");
                int cost = resultSet.getInt("cost");
                int slots = resultSet.getInt("slots");
                User artist = UserController.getByID(resultSet.getInt("artistID"));
                orderApplication = new OrderApplication(id, orderType, orderStyle, info, cost, slots, artist);

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
        return orderApplication;
    }

    public static void placeOrder(OrderType orderType, OrderStyle orderStyle, String info, String costValue, int slotNumber, User user) {
        String query = "INSERT INTO orderApplication (orderType, orderStyle, info, cost, slots) VALUES " +
                "(\'" + orderType.name().toLowerCase() + "\',\'" + orderStyle.name().toLowerCase() + "\',\'" +
                info + "\',\'" + costValue + "\',\'" + slotNumber + "\');";
        System.out.println(query);
        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
            {
                int id = resultSet.getInt(1);
                query = "INSERT INTO artists (artistID, orderApplicationID) VALUES (\'"  + user.getId() + "\',\'"+id+"\')";
                System.out.println(query);
                statement.executeUpdate(query);
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

        }
    }

    public static ArrayList<OrderApplication> getAll() {
        ArrayList<OrderApplication> apps = new ArrayList<>();
        String query = "SELECT * FROM orderApplication JOIN artists ON orderApplication.id = artists.orderApplicationID";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                OrderType type = OrderType.valueOf(resultSet.getString("orderType").toUpperCase());
                OrderStyle style = OrderStyle.valueOf(resultSet.getString("orderStyle").toUpperCase());
                String info = resultSet.getString("info");
                int cost = resultSet.getInt("cost");
                int slots = resultSet.getInt("slots");
                User user = UserController.getByID(resultSet.getInt("artistID"));
                apps.add(new OrderApplication(id, type, style, info, cost, slots, user));
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

        return apps;
    }

    public static ArrayList<OrderApplication> formQuery(boolean[] values, int costLimit) {
        ArrayList<OrderApplication> apps = new ArrayList<>();

        boolean elementExists = false;
        boolean group = false;
        String query = "SELECT * FROM orderApplication JOIN artists on orderApplication.id = artists.orderApplicationID ";
        System.out.println(query);

        if (values[0]) {
            query += "WHERE (orderType = \'free\'";
            elementExists = true;
        }
        if (values[1]) {
            if (elementExists) {
                query += " OR orderType = \'description\')";
            } else {
                query += "WHERE (orderType = \'description\')";
                elementExists = true;
            }
        } else {
            if (values[0])
                query += ")";

        }
        if (values[2]) {
            if (elementExists) {
                query += " AND (orderStyle=\'digital\'";
            } else {
                query += "WHERE (orderStyle=\'digital\'";
                elementExists = true;
            }
            group = true;
        }
        if (values[3]) {
            if (elementExists) {
                if (group) {
                    query += " OR orderStyle=\'traditional\'";
                } else {
                    query += " AND (orderStyle=\'traditional\'";
                }
            } else {
                query += "WHERE (orderStyle=\'traditional\'";
                elementExists = true;
            }
            group = true;
        }
        if (values[4]) {
            if (elementExists) {
                if (group) {
                    query += " OR orderStyle=\'three_dimensional\'";
                } else {
                    query += " AND (orderStyle=\'three_dimensional\'";
                }
            } else {
                query += "WHERE (orderStyle=\'three_dimensional\'";
                elementExists = true;
            }
            group = true;
        }
        if (values[5]) {
            if (elementExists) {
                if (group) {
                    query += " OR orderStyle=\'animation\'";
                } else {
                    query += " AND (orderStyle=\'animation\'";
                }
            } else {
                query += "WHERE (orderStyle=\'animation\'";
                elementExists = true;
            }
            group = true;
        }
        if (values[6]) {
            if (elementExists) {
                if (group) {
                    query += " OR orderStyle=\'other\'";
                } else {
                    query += " AND (orderStyle=\'other\'";
                }
            } else {
                query += "WHERE (orderStyle=\'other\'";
                elementExists = true;
            }
            group = true;
        }
        if (group) {
            query += ")";
            group = false;
        }
        if (costLimit != 0) {
            if (elementExists) {
                query += (" AND cost <=" + costLimit);

            } else {
                query += "WHERE cost <=" + costLimit;
                elementExists = true;
            }
        }

        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                OrderType type = OrderType.valueOf(resultSet.getString("orderType").toUpperCase());
                OrderStyle style = OrderStyle.valueOf(resultSet.getString("orderStyle").toUpperCase());
                String info = resultSet.getString("info");
                int cost = resultSet.getInt("cost");
                int slots = resultSet.getInt("slots");
                User user = UserController.getByID(resultSet.getInt("artistID"));
                apps.add(new OrderApplication(id, type, style, info, cost, slots, user));
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

        return apps;
    }
}
