
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection() {
        try {
            String connStr = DBPropertyUtil.getPropertyString("C:\\Users\\ELCOT\\Workspace\\Ecom\\src\\db.properties");
            return DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            return null;
        }
    }
}
