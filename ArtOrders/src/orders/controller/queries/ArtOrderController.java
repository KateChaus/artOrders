package orders.controller.queries;

import orders.controller.utils.DBConnector;
import orders.model.entities.ArtOrder;
import orders.model.entities.OrderApplication;
import orders.model.entities.User;
import orders.model.enums.OrderStatus;

import java.sql.*;
import java.util.ArrayList;

public class ArtOrderController {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static ArrayList<ArtOrder> getByCustomer(User user) {
        ArrayList<ArtOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM artOrder JOIN customers ON customers.orderID = artOrder.id JOIN orders ON orders.artOrderID = artOrder.id WHERE customers.customerID=\'" + user.getId() + "\'" +
                ";";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("artOrder.id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String image = resultSet.getString("image");
                OrderApplication orderApplication = OrderApplicationController.getByID(resultSet.getInt("orderApplicationID"));
                ArtOrder order = new ArtOrder(id, status, image, user, orderApplication);
                orders.add(order);
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
        return orders;
    }

    public static ArrayList<ArtOrder> outOrders(User user) {
        ArrayList<ArtOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM artOrder JOIN customers ON customers.orderID = artOrder.id " +
                "JOIN orders ON orders.artOrderID = artOrder.id " +
                "WHERE customers.customerID=\'" + user.getId() + "\' and artOrder.orderStatus = \'applied\';";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("artOrder.id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String image = resultSet.getString("image");
                OrderApplication orderApplication = OrderApplicationController.getByID(resultSet.getInt("orderApplicationID"));
                ArtOrder order = new ArtOrder(id, status, image, user, orderApplication);
                orders.add(order);
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
        return orders;
    }

    public static ArrayList<ArtOrder> inOrders(User user) {
        ArrayList<ArtOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM artOrder JOIN orders ON artOrder.id = orders.artOrderID " +
                "JOIN orderApplication ON orders.orderApplicationID = orderApplication.id " +
                "JOIN artists ON orderApplication.id = artists.orderApplicationID " +
                "JOIN customers ON artOrder.id = customers.orderID " +
                "WHERE orderStatus = 'applied' AND artistID = \'" + user.getId() + "\'";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("artOrder.id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String image = resultSet.getString("image");
                User customer = UserController.getByID(resultSet.getInt("customerID"));
                OrderApplication orderApplication = OrderApplicationController.getByID(resultSet.getInt("orderApplicationID"));
                ArtOrder order = new ArtOrder(id, status, image, customer, orderApplication);
                orders.add(order);
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
        return orders;
    }

    public static ArrayList<ArtOrder> processOrders(User user) {
        ArrayList<ArtOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM artOrder " +
                "JOIN customers ON artOrder.id = customers.orderID " +
                "JOIN orders ON artOrder.id = orders.ArtOrderID " +
                " WHERE customerID=\'" + user.getId() + "\' and orderStatus = \'process\';";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String image = resultSet.getString("image");
                OrderApplication orderApplication = OrderApplicationController.getByID(resultSet.getInt("orderApplicationID"));
                ArtOrder order = new ArtOrder(id, status, image, user, orderApplication);
                orders.add(order);
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
        return orders;
    }

    public static ArrayList<ArtOrder> acceptedOrders(User user) {
        ArrayList<ArtOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM artOrder JOIN orders ON artOrder.id=orders.artOrderID " +
                "JOIN orderApplication ON orders.orderApplicationID = orderApplication.id " +
                "JOIN artists ON orderApplication.id = artists.orderApplicationID " +
                "JOIN customers ON artOrder.id = customers.orderID " +
                "WHERE orderStatus = 'process' AND artistID = \'" + user.getId() + "\'";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("artOrder.id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String image = resultSet.getString("image");
                OrderApplication orderApplication = OrderApplicationController.getByID(resultSet.getInt("orderApplicationID"));
                User customer = UserController.getByID(resultSet.getInt("customerID"));
                ArtOrder order = new ArtOrder(id, status, image, customer, orderApplication);
                orders.add(order);
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
        return orders;
    }

    public static ArrayList<ArtOrder> completedOrders(User user) {
        ArrayList<ArtOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM artOrder " +
                "JOIN customers ON customers.orderID = artOrder.id " +
                "JOIN orders ON artOrder.id = orders.artOrderID " +
                "WHERE customerID=\'" + user.getId() + "\' and orderStatus = \'finished\';";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("artOrder.id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String image = resultSet.getString("image");
                OrderApplication orderApplication = OrderApplicationController.getByID(resultSet.getInt("orderApplicationID"));
                ArtOrder order = new ArtOrder(id, status, image, user, orderApplication);
                orders.add(order);
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
        return orders;
    }

    public static ArrayList<ArtOrder> drawnOrders(User user) {
        ArrayList<ArtOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM artOrder JOIN orders ON artOrder.id =orders.artOrderID " +
                "JOIN orderApplication ON orders.orderApplicationID = orderApplication.id " +
                "JOIN artists ON orderApplication.id = artists.orderApplicationID " +
                "JOIN customers ON artOrder.id = customers.orderID " +
                "WHERE orderStatus = 'finished' AND artistID = \'" + user.getId() + "\'";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("artOrder.id");
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus").toUpperCase());
                String image = resultSet.getString("image");
                OrderApplication orderApplication = OrderApplicationController.getByID(resultSet.getInt("orderApplicationID"));
                User customer = UserController.getByID(resultSet.getInt("customerID"));
                ArtOrder order = new ArtOrder(id, status, image, customer, orderApplication);
                orders.add(order);
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
        return orders;
    }

    public static void remove(ArtOrder order) {
        String query = "DELETE FROM artOrder WHERE id = \'" + order.getId() + "\'";
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

    public static void accept(ArtOrder order) {
        String query = "UPDATE artOrder SET orderStatus = \'process\' WHERE id = \'" + order.getId() + "\'";
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

    public static void finish(ArtOrder order) {
        String query = "UPDATE artOrder SET orderStatus = \'finished\' WHERE id = \'" + order.getId() + "\'";
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

    public static void changeImage(ArtOrder order) {
        String query = "UPDATE artOrder SET image = \'" + order.getImage() + "\' WHERE id = \'" + order.getId() + "\'";
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

    public static void apply(int customerID, int orderApplicationID) {
        String query = "INSERT INTO artOrder (orderStatus) VALUES (\'applied\');";
        System.out.println(query);

        try {
            connection = DriverManager.getConnection(DBConnector.getUrl(), DBConnector.getDbUser(), DBConnector.getDbPassword());
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
            {
                int id = resultSet.getInt(1);
                query = "INSERT INTO customers (customerID, orderID) VALUES  ,\'" + customerID + "\',\'"+id+"\')";
                statement.executeUpdate(query);
                query = "INSERT INTO orders (orderApplicationID, artOrderID) VALUES  ,\'" + orderApplicationID + "\',\'"+id+"\')";
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
}

