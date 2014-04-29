package database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jb
 */
public class TheConnection {
    
    private Connection conn;

    public void open() {
        try {
            System.out.println("Loading Oracle driver ...");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(ConnectionInfo.CONN_URL, ConnectionInfo.USER, ConnectionInfo.PASSWD);
            System.out.println("connected");
        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }

    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TheConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConn() {
        return conn;
    }

}
