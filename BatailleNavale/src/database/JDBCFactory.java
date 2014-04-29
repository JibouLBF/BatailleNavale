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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Partie;

/**
 *
 * @author jb
 */
public class JDBCFactory implements DataFactory {

    TheConnection theConnection;

    @Override
    public ArrayList<Partie> getAllGames() {
        ArrayList<Partie> gameList = new ArrayList<Partie> ();
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "SELECT * FROM Partie WHERE IdPartie NOT IN (SELECT IdPartie FROM AGagne)";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            while(rset.next()) {
               gameList.add(new Partie(rset.getInt("IdPartie"), rset.getString("DateDemarrage"), rset.getString("Joueur1"), rset.getString("Joueur2"), null));
            }
            rset.close();
            stmt.close();
            
            STMT = "(SELECT Partie.IdPartie,Joueur1,Joueur2,DateDemarrage,Vainqueur FROM Partie,AGagne WHERE Partie.IdPartie = AGagne.IdPartie)";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(STMT);
            while(rset.next()) {
               gameList.add(new Partie(rset.getInt("IdPartie"), rset.getString("DateDemarrage"), rset.getString("Joueur1"), rset.getString("Joueur2"), rset.getString("Vainqueur")));
            }
            rset.close();
            stmt.close();
            theConnection.close();
            return gameList;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public JDBCFactory() {
        this.theConnection = new TheConnection();
    }

}
