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
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AbstractMove;
import model.Boat;
import model.Player;
import model.Game;

/**
 *
 * @author jb
 */
public class JDBCFactory implements DataFactory {

    TheConnection theConnection;

    @Override
    public ArrayList<Game> getAllGames() {
        ArrayList<Game> gameList = new ArrayList<Game>();
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "SELECT * FROM Partie WHERE IdPartie NOT IN (SELECT IdPartie FROM AGagne)";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            while (rset.next()) {
                gameList.add(new Game(rset.getInt("IdPartie"), rset.getString("DateDemarrage"), new Player(rset.getString("Joueur1")), new Player(rset.getString("Joueur2")), null));
            }
            rset.close();
            stmt.close();

            STMT = "(SELECT Partie.IdPartie,Joueur1,Joueur2,DateDemarrage,Vainqueur FROM Partie,AGagne WHERE Partie.IdPartie = AGagne.IdPartie)";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(STMT);
            while (rset.next()) {
                gameList.add(new Game(rset.getInt("IdPartie"), rset.getString("DateDemarrage"), new Player(rset.getString("Joueur1")), new Player(rset.getString("Joueur2")), new Player(rset.getString("Vainqueur"))));;
            }
            rset.close();
            stmt.close();
            return gameList;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            theConnection.close();
        }

        return null;
    }

    @Override
    public Game getCurrentGame(Player player) throws SQLRecoverableException, SQLException {
        Game game = null;
        theConnection.open();
        Connection conn = theConnection.getConn();

        // On regarde si on est pas déjà dans une partie en cours
        String STMT = "SELECT * FROM Partie WHERE IdPartie NOT IN (SELECT IdPartie FROM AGagne) AND (Joueur1 = '" + player.getPseudo() + "' OR Joueur2 = '" + player.getPseudo() + "')";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(STMT);
        // Si c'est le cas on renvoie les infos de cette partie
        if (rset.next()) {
            game = new Game(rset.getInt("IdPartie"), rset.getString("DateDemarrage"), new Player(rset.getString("Joueur1")), new Player(rset.getString("Joueur2")), null);
        }
        rset.close();
        stmt.close();
        theConnection.close();
        return game;

    }

    @Override
    public Player findAnOpponent(Player player) throws SQLRecoverableException, SQLException {
        Player opponent = null;
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
            if (rset.getString("Pseudo").equals(player.getPseudo())) {
                if (rset.next()) {
                    opponent = new Player(rset.getString("Pseudo"));
                }
            } else {
                opponent = new Player(rset.getString("Pseudo"));
            }

        }
        rset.close();
        stmt.close();
        theConnection.close();
        return opponent;
    }

    @Override
    public ArrayList<Boat> getAllBoat(Game game, Player player) throws SQLRecoverableException, SQLException {
        ArrayList<Boat> boatList = new ArrayList<Boat>();
        theConnection.open();
        Connection conn = theConnection.getConn();
        String STMT = "SELECT * FROM Bateau WHERE IdPartie = '" + game.getGameID() + "' AND Proprietaire = '" + player.getPseudo() + "'";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(STMT);
        while (rset.next()) {
            boatList.add(new Boat(game, rset.getInt("IdBateau"), rset.getInt("Taille"), new Player(rset.getString("Proprietaire")), rset.getInt("PosX"), rset.getInt("PosY"), rset.getString("Orientation"), rset.getInt("Vie"), rset.getInt("PosXInit"), rset.getInt("PosYInit")));
        }
        rset.close();
        stmt.close();
        theConnection.close();
        return boatList;
    }

    @Override
    public PriorityQueue<AbstractMove> getPlayerLastMoves(Game game, Player player) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PriorityQueue<AbstractMove> getGameMoves(Game game) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public JDBCFactory() {
        this.theConnection = new TheConnection();
    }

}
