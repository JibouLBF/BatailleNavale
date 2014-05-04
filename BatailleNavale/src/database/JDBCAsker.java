/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jb
 */
public class JDBCAsker implements DataBaseAsker {

    TheConnection theConnection;

    @Override
    public boolean playerExist(String Pseudo) throws SQLRecoverableException, SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();

        String STMT = "SELECT * FROM Joueur WHERE Pseudo =" + "'" + Pseudo + "'";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(STMT);
        boolean exist = rset.next();
        rset.close();
        stmt.close();
        theConnection.close();
        if (exist) {
            System.out.println(Pseudo + " existe dans la BD");
        } else {
            System.out.println(Pseudo + " n'existe pas dans la BD");
        }
        return exist;

    }

    @Override
    public boolean isTurnOf(int IdPartie, String Pseudo) throws SQLRecoverableException, SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();

        String STMT = "SELECT Tour FROM Partie WHERE IdPartie =" + "'" + IdPartie + "'";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(STMT);
        rset.next();
        String playerName = rset.getString("Tour");
        rset.close();
        stmt.close();
        theConnection.close();
        if (playerName == null) {
            return false;
        } else {
            return playerName.equals(Pseudo);
        }
    }

    public boolean hasPlacedBoats(int IdPartie, String Pseudo) throws SQLRecoverableException, SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();

        String STMT = "SELECT * FROM Bateau WHERE IdPartie = '"+IdPartie +"' AND Proprietaire ='" + Pseudo + "'";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(STMT);
        boolean placed = rset.next();
        rset.close();
        stmt.close();
        theConnection.close();
        if (placed) {
            System.out.println(Pseudo + " a déjà placé ses bateaux");
        } else {
            System.out.println(Pseudo + " n'a pas encore placé ses bateaux");
        }
        return placed;

    }

    public JDBCAsker() {
        this.theConnection = new TheConnection();
    }

    public static void main(String[] agrs) {

        /*   JDBCAsker asker = new JDBCAsker();
         if (asker.PlayerExist("abikhatv")) {
         System.out.println("abikhatv existe !!!");
         } else {
         System.out.println("abikhatv existe pas :(");
         }*/
    }

}
