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
    
    /**
     * Récupère les drivers JDBC et ouvre la connexion avec la base
     */
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

    /**
     * Ferme la connexion avec la base
     */
    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TheConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Renvoie l'intance de la connexion avec la base
     * @return Connection DBconnection
     */
    public Connection getConn() {
        return conn;
    }

}
