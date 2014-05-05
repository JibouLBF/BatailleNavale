/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Boat;
import model.Player;
import model.Game;
import model.Sens;

/**
 *
 * @author jb
 */
public class JDBCUpdater implements DataBaseUpdater {

    private TheConnection theConnection;

    @Override
    public void addPlayer(Player player) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "INSERT INTO JOUEUR VALUES ('" + player.getPseudo() + "','" + player.getSurname() + "','" + player.getFirstname() + "','" + player.getEmail() + "','" + player.getStreetNumber() + "','" + player.getStreetName() + "','" + player.getPostcode() + "','" + player.getCity() + "','" + player.getBirthday() + "')";
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
    public void addBoats(Game game, Player owner, ArrayList<Boat> boatList) throws SQLIntegrityConstraintViolationException, SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            int[] boatID = new int[boatList.size()];
            conn.setAutoCommit(false);

            for (int i = 0; i < boatList.size(); i++) {
                String STMT = "SELECT COUNT(*) FROM Bateau WHERE IdPartie = '" + game.getGameID() + "'";
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(STMT);
                rset.next();
                boatID[i] = rset.getInt(1);
                rset.close();
                stmt.close();

                STMT = "INSERT INTO Bateau VALUES (" + boatID[i] + ",'" + game.getGameID() + "','" + boatList.get(i).getSize() + "','" + owner.getPseudo() + "','" + boatList.get(i).getPosX() + "','" + boatList.get(i).getPosY() + "','" + boatList.get(i).getOrientation() + "','" + boatList.get(i).getSize() + "','" + boatList.get(i).getPosX() + "','" + boatList.get(i).getPosY() + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(STMT);
                stmt.close();
            }

            conn.commit();

            for (int i = 0; i < boatList.size(); i++) {
                boatList.get(i).setBoatID(boatID[i]);
                boatList.get(i).setLife(i);
            }

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
    public void addMove(Game game, Boat boat, Sens s) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "SELECT COUNT(*) FROM Coup WHERE IdPartie ='" + game.getGameID() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int IdCoup = rset.getInt(1);
            rset.close();
            stmt.close();

            STMT = "INSERT INTO Coup VALUES ('" + game.getGameID() + "','" + IdCoup + "','" + boat.getBoatID() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Deplacement VALUES ('" + game.getGameID() + "','" + IdCoup + "','" + s.getName() + "')";
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
    public void addShot(Game game, Boat boat, int x, int y) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {

            String STMT = "SELECT COUNT(*) FROM Coup WHERE IdPartie ='" + game.getGameID() + "')";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int IdCoup = rset.getInt(1);
            rset.close();
            stmt.close();

            STMT = "INSERT INTO Coup VALUES ('" + game.getGameID() + "','" + IdCoup + "','" + boat.getBoatID() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Tir VALUES ('" + game.getGameID() + "','" + IdCoup + "','" + x + "','" + y + "')";
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
    public void addGame(Game game) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        Savepoint s1 = null;
        try {
            conn.setAutoCommit(false);
            s1 = conn.setSavepoint();
            String STMT = "SELECT seqIdPartie.nextval from dual";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int gameID = rset.getInt(1);
            stmt.close();

            STMT = "SELECT current_date from dual";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(STMT);
            rset.next();
            String date = rset.getString(1);
            stmt.close();
            STMT = "INSERT INTO Partie VALUES ( " + gameID + ",CURRENT_DATE,'" + game.getPlayer1().getPseudo() + "','" + game.getPlayer2().getPseudo() + "', NULL)";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            conn.commit();
            game.setGameID(gameID);
            game.setDate(date);

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
    public void changeTurn(Game game, Player player) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "UPDATE Partie SET Tour = '" + player.getPseudo() + "' WHERE IdPartie = " + game.getGameID();
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
    public void turnBoat(Game game, Boat boat, String orientation) throws SQLIntegrityConstraintViolationException, SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        conn.setAutoCommit(false);
        try {
            String STMT = "UPDATE Bateau SET Orientation = '" + orientation + "' WHERE IdPartie = " + game.getGameID() + " AND IdBateau =" + boat.getBoatID();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            conn.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, e);
            conn.rollback();
            throw new SQLIntegrityConstraintViolationException();

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
    public void moveBoat(Game game, Boat boat, int posX, int posY) throws SQLIntegrityConstraintViolationException, SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        conn.setAutoCommit(false);
        try {
            String STMT = "UPDATE Bateau SET PosX =" + posX + ", PosY =" + posY + "WHERE IdPartie = " + game.getGameID() + " AND IdBateau =" + boat.getBoatID();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            conn.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, e);
            conn.rollback();
            throw new SQLIntegrityConstraintViolationException();
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
        //updater.addBoat(1, 0, 3, "abikhatv", new Case(1, 5), Orientation.OUEST, 3);
    }

}
