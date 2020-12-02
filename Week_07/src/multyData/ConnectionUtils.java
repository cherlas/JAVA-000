package multyData;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class ConnectionUtils {
    public static final String URL = "jdbc:mysql://localhost:3306/local_test?useUnicode=true&characterEncoding=UTF-8";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            log.error("Driver not found.");
        } catch (SQLException throwables) {
            log.error("connection error");
        }
        return connection;
    }

    public static void closeConnection(Connection connection, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
