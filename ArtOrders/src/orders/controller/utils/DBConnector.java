package orders.controller.utils;

public class DBConnector {

    private static final String url = "jdbc:mysql://localhost:3306/orders?useSSL=false";
    private static final String dbUser = "root";
    private static final String dbPassword = "root";

    public static String getUrl() {
        return url;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

}
