package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static Connection con;
    public static Connection getConnection(){
        try {
            DBconnection.con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/projectpass", 
            "root", 
            null);
            System.out.println("Connection Stablished Sccessfully ...");
            return con;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
        public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
