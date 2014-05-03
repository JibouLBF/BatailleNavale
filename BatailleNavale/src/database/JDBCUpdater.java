/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Sens;

/**
 *
 * @author jb
 */
public class JDBCUpdater implements DataBaseUpdater {

    private TheConnection theConnection;

    @Override
    public void addPlayer(String Pseudo, String Nom, String Prenom, String Email, int Numero, String Rue, String CodePostal, String Ville, String DateNaissance) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "INSERT INTO JOUEUR VALUES ('" + Pseudo + "','" + Nom + "','" + Prenom + "','" + Email + "','" + Numero + "','" + Rue + "','" + CodePostal + "','" + Ville + "','" + DateNaissance + "')";
            Statement stmt = conn.createStatement();
            int nb = stmt.executeUpdate(STMT);
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            theConnection.close();
        }
    }

    @Override
    public void addBoats(int IdPartie, String Proprietaire, int PosXB1, int PosYB1, int TailleB1, String oB1, int PosXB2, int PosYB2, int TailleB2, String oB2, int PosXB3, int PosYB3, int TailleB3, String oB3) throws SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {

            conn.setAutoCommit(false);
            String STMT = "INSERT INTO Bateau VALUES ((SELECT COUNT(*) FROM Bateau WHERE IdPartie = '" + IdPartie + "'),'" + IdPartie + "','" + TailleB1 + "','" + Proprietaire + "','" + PosXB1 + "','" + PosYB1 + "','" + oB1 + "','" + TailleB1 + "','" + PosXB1 + "','" + PosYB1 + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Bateau VALUES ((SELECT COUNT(*) FROM Bateau WHERE IdPartie = '" + IdPartie + "'),'" + IdPartie + "','" + TailleB2 + "','" + Proprietaire + "','" + PosXB2 + "','" + PosYB2 + "','" + oB2 + "','" + TailleB2 + "','" + PosXB2 + "','" + PosYB2 + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            if (0 != PosXB3 || PosYB3 != 0) {
                STMT = "INSERT INTO Bateau VALUES ((SELECT COUNT(*) FROM Bateau WHERE IdPartie = '" + IdPartie + "'),'" + IdPartie + "','" + TailleB3 + "','" + Proprietaire + "','" + PosXB3 + "','" + PosYB3 + "','" + oB3 + "','" + TailleB3 + "','" + PosXB3 + "','" + PosYB3 + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(STMT);
                stmt.close();
            }

            conn.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, e);
            conn.rollback();
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            throw new SQLException();
        } finally {
            conn.setAutoCommit(true);
            theConnection.close();

        }
    }

    @Override
    public void addMove(int IdPartie, int IdBateau, Sens s) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "SELECT COUNT(*) FROM Coup WHERE IdPartie ='" + IdPartie + "')";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int IdCoup = rset.getInt(1);
            rset.close();
            stmt.close();

            STMT = "INSERT INTO Coup VALUES ('" + IdPartie + "','" + IdCoup + "','" + IdBateau + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Deplacement VALUES ('" + IdPartie + "','" + IdCoup + "','" + s.getName() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            theConnection.close();

        }
    }

    @Override
    public void addShot(int IdPartie, int IdBateau, int x, int y) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {

            String STMT = "SELECT COUNT(*) FROM Coup WHERE IdPartie ='" + IdPartie + "')";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int IdCoup = rset.getInt(1);
            rset.close();
            stmt.close();

            STMT = "INSERT INTO Coup VALUES ('" + IdPartie + "','" + IdCoup + "','" + IdBateau + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Tir VALUES ('" + IdPartie + "','" + IdCoup + "','" + x + "','" + y + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            theConnection.close();
        }
    }

    @Override
    public void addGame(String Joueur1, String Joueur2) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        Savepoint s1 = null;
        try {
            conn.setAutoCommit(false);
            s1 = conn.setSavepoint();
            String STMT = "INSERT INTO Partie VALUES ( seqIdPartie.nextval , CURRENT_DATE ,'" + Joueur1 + "','" + Joueur2 + "', NULL)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback(s1);
            } catch (SQLException ex1) {
                Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            theConnection.close();
        }
    }

    @Override
    public void changeTurn(int IdPartie, String Pseudo) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "UPDATE Partie SET Tour = '" + Pseudo + "' WHERE IdPartie = " + IdPartie;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            theConnection.close();
        }
    }

    @Override
    public void turnBoat(int IdPartie, int IdBateau, String orientation) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "UPDATE Bateau SET Orientation = '" + orientation + "' WHERE IdPartie = " + IdPartie + " AND IdBateau =" + IdBateau;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            theConnection.close();
        }
    }

    @Override
    public void moveBoat(int IdPartie, int IdBateau, int posX, int posY) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "UPDATE Bateau SET PosX =" + posX + ", PosY =" + posY + "WHERE IdPartie = " + IdPartie + " AND IdBateau =" + IdBateau;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            theConnection.close();
        }
    }

    public JDBCUpdater() {
        this.theConnection = new TheConnection();
    }

    public static void main(String[] agrs) throws SQLException {
        JDBCUpdater updater = new JDBCUpdater();
        //updater.addBoat(0, 1, 0, "abikhatv", new Case(1, 3), Orientation.NORD, 3);
        updater.addGame("abikhatv", "abikhat");
        //updater.addBoat(1, 0, 3, "abikhatv", new Case(1, 5), Orientation.OUEST, 3);
    }

}
