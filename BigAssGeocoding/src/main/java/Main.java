import java.sql.*;
import java.math.*;
import org.postgresql.Driver;

public class Main {
    public static void main(String[] args){
        try {
            Driver myDriver = new Driver();
            DriverManager.registerDriver(myDriver);

            String Url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bigmovie";
            String USER = "user";
            String Password = "123456789";
            Connection conn = DriverManager.getConnection(Url, USER, Password);
            GetGeocode geoCode = new GetGeocode(conn);
            geoCode.run();
            conn.close();
        }
        catch (Exception ex)
        {
            System.out.println("Something went wrong.\n" + ex);
            System.exit(1);
        }
    }
}
