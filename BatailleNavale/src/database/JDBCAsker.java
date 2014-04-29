/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean PlayerExist(String Pseudo) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "SELECT * FROM Joueur WHERE Pseudo =" + "'" + Pseudo + "'";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            boolean exist = rset.next();
            rset.close();
            stmt.close();
            theConnection.close();
            if(exist)
                System.out.println(Pseudo +" existe dans la BD");
            else
                System.out.println(Pseudo +" n'existe pas dans la BD");
            return exist;

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;

    }

    public JDBCAsker() {
        this.theConnection = new TheConnection();
    }

    public static void main(String[] agrs) {
        
        JDBCAsker asker = new JDBCAsker();
        if (asker.PlayerExist("abikhatv")) {
            System.out.println("abikhatv existe !!!");
        } else {
            System.out.println("abikhatv existe pas :(");
        }
    }
}
