package fx.DataSet;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by rainslayerx on 1/26/17.
 */
public class DatabaseConnection {
    private static Connection conn = null;

    public static void StartConnection() throws SQLException {
        Driver myDriver = new Driver();
        DriverManager.registerDriver(myDriver);

        String Url = "jdbc:postgresql://94.212.160.40:5432/postgres?currentSchema=bigmovie";
        String USER = "user";
        String Password = "123456789";
        conn = DriverManager.getConnection(Url, USER, Password);
    }

    public static void StopConnection() throws SQLException {
        if(conn != null && !conn.isClosed())
            conn.close();
    }

    public static Connection GetConnection() {
        return conn;
    }
}
