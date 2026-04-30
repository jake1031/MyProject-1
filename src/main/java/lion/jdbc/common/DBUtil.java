package lion.jdbc.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static Connection getConnection() throws Exception {
        Connection conn = null;

        String url = "jdbc:mysql://localhost:3306/liondb";
        String user = "carami";
        String password = "lion1234";
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
