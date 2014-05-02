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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Bateau;
import model.Joueur;
import model.Orientation;
import model.Partie;

/**
 *
 * @author jb
 */
public class JDBCFactory implements DataFactory {

    TheConnection theConnection;

    @Override
    public ArrayList<Partie> getAllGames() {
        ArrayList<Partie> gameList = new ArrayList<Partie>();
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "SELECT * FROM Partie WHERE IdPartie NOT IN (SELECT IdPartie FROM AGagne)";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            while (rset.next()) {
                gameList.add(new Partie(rset.getInt("IdPartie"), rset.getString("DateDemarrage"), rset.getString("Joueur1"), rset.getString("Joueur2"), null));
            }
            rset.close();
            stmt.close();

            STMT = "(SELECT Partie.IdPartie,Joueur1,Joueur2,DateDemarrage,Vainqueur FROM Partie,AGagne WHERE Partie.IdPartie = AGagne.IdPartie)";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(STMT);
            while (rset.next()) {
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

    @Override
    public Partie getCurrentGame(String Pseudo) throws SQLRecoverableException, SQLException {
        Partie game = null;
        theConnection.open();
        Connection conn = theConnection.getConn();

        // On regarde si on est pas déjà dans une partie en cours
        String STMT = "SELECT * FROM Partie WHERE IdPartie NOT IN (SELECT IdPartie FROM AGagne) AND (Joueur1 = '" + Pseudo + "' OR Joueur2 = '" + Pseudo + "')";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(STMT);
        // Si c'est le cas on renvoie les infos de cette partie
        if (rset.next()) {
            game = new Partie(rset.getInt("IdPartie"), rset.getString("DateDemarrage"), rset.getString("Joueur1"), rset.getString("Joueur2"), null);
        }
        rset.close();
        stmt.close();
        theConnection.close();
        return game;
    }

    @Override
    public Joueur findAnOpponent(String Pseudo) throws SQLRecoverableException, SQLException {
        Joueur opponent = null;
        theConnection.open();
        Connection conn = theConnection.getConn();

        // On regarde si on est pas déjà dans une partie en cours
        String STMT = "SELECT Pseudo, COUNT(*) AS CNT\n"
                + "FROM ((SELECT Pseudo\n"
                + "			FROM Joueur\n"
                + "			WHERE Pseudo NOT IN(SELECT Joueur1 \n"
                + "								FROM Partie\n"
                + "								WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))\n"
                + "  			AND Pseudo NOT IN(SELECT Joueur2 \n"
                + "								FROM Partie\n"
                + "								WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))\n"
                + "  		)\n"
                + "		LEFT OUTER JOIN\n"
                + "		(SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie) ON Joueur = Pseudo)\n"
                + "HAVING COUNT(*) = \n"
                + "				(SELECT MIN(COUNT(*))\n"
                + "				 FROM ((SELECT Pseudo\n"
                + "						FROM Joueur\n"
                + "						WHERE Pseudo NOT IN(SELECT Joueur1 \n"
                + "											FROM Partie\n"
                + "											WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))\n"
                + "  						  AND Pseudo NOT IN(SELECT Joueur2 \n"
                + "										  	FROM Partie\n"
                + "											WHERE IdPartie NOT IN(SELECT IdPartie FROM AGagne))\n"
                + "  						)\n"
                + "						LEFT OUTER JOIN\n"
                + "						(SELECT IdPartie,Joueur1 AS Joueur FROM Partie UNION SELECT IdPartie,Joueur2 AS Joueur FROM Partie) ON Joueur = Pseudo)\n"
                + "				GROUP BY Pseudo)\n"
                + "GROUP BY Pseudo";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(STMT);
        // Si on a trouvé un adversaire
        if (rset.next()) {
            // Si c'est nous on va voir la suite
            if (rset.getString("Pseudo").equals(Pseudo)) {
                if (rset.next()) {
                    opponent = new Joueur(rset.getString("Pseudo"));
                }
            } else {
                opponent = new Joueur(rset.getString("Pseudo"));
            }

        }
        rset.close();
        stmt.close();
        theConnection.close();
        return opponent;
    }

    @Override
    public ArrayList<Bateau> getAllBoat(int IdPartie) throws SQLRecoverableException, SQLException{
        ArrayList<Bateau> boatList = new ArrayList<Bateau>();
        theConnection.open();
        Connection conn = theConnection.getConn();
            String STMT = "SELECT * FROM Bateau WHERE IdPartie = '" +IdPartie +"')";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            while (rset.next()) {
                boatList.add(new Bateau(rset.getInt("IdBateau"), IdPartie, rset.getInt("Taille"), rset.getString("Proprietaire"),rset.getInt("PosX"), rset.getInt("PosY"), Orientation.valueOf(rset.getString("Orientation")), rset.getInt("Vie"), rset.getInt("PosXInit"), rset.getInt("PosYInit")));
            }
            rset.close();
            stmt.close();
            theConnection.close();
            return boatList;
    }

    public JDBCFactory() {
        this.theConnection = new TheConnection();
    }

}
