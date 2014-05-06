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
import model.Game;
import model.Move;
import model.Player;
import model.Sens;
import model.Shot;

/**
 *
 * @author jb
 */
public class JDBCUpdater implements DataBaseUpdater {

    private TheConnection theConnection;

    @Override
    public void addPlayer(Player player) throws SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        conn.setAutoCommit(false);
        try {
            String STMT = "INSERT INTO JOUEUR VALUES ('" + player.getPseudo() + "','" + player.getSurname() + "','" + player.getFirstname() + "','" + player.getEmail() + "','" + player.getStreetNumber() + "','" + player.getStreetName() + "','" + player.getPostcode() + "','" + player.getCity() + "','" + player.getBirthday() + "')";
            Statement stmt = conn.createStatement();
            int nb = stmt.executeUpdate(STMT);
            stmt.close();
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            throw new SQLException();
        } finally {
            conn.setAutoCommit(true);
            theConnection.close();
        }
    }

    @Override
    public void addBoats(ArrayList<Boat> boatList) throws SQLIntegrityConstraintViolationException, SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            int[] boatID = new int[boatList.size()];
            conn.setAutoCommit(false);

            for (int i = 0; i < boatList.size(); i++) {
                String STMT = "SELECT COUNT(*) FROM Bateau WHERE IdPartie = '" + boatList.get(i).getGame().getGameID() + "'";;
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(STMT);
                rset.next();
                boatID[i] = rset.getInt(1);
                rset.close();
                stmt.close();
                System.out.println("BOAT ID "+ boatID[i]);
                STMT = "INSERT INTO Bateau VALUES (" + boatID[i] + ",'" + boatList.get(i).getGame().getGameID() + "','" + boatList.get(i).getSize() + "','" + boatList.get(i).getOwner().getPseudo() + "','" + boatList.get(i).getPosX() + "','" + boatList.get(i).getPosY() + "','" + boatList.get(i).getOrientation() + "','" + boatList.get(i).getLife() + "','" + boatList.get(i).getPosXInit() + "','" + boatList.get(i).getPosYInit() + "','" + boatList.get(i).getNbCoupRestant() + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(STMT);
                stmt.close();
            }

            conn.commit();

            for (int i = 0; i < boatList.size(); i++) {
                boatList.get(i).setBoatID(boatID[i]);
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
    public void addShot(Shot shot) throws SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            conn.setAutoCommit(false);
            String STMT = "SELECT COUNT(*) FROM Coup WHERE IdPartie ='" + shot.getGame().getGameID() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int IdCoup = rset.getInt(1);
            rset.close();
            stmt.close();

            STMT = "INSERT INTO Coup VALUES ('" + shot.getGame().getGameID() + "','" + IdCoup + "','" + shot.getBoat().getBoatID() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Tir VALUES ('" + shot.getGame().getGameID() + "','" + IdCoup + "','" + shot.getPosX() + "','" + shot.getPosY() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            
            STMT = "UPDATE Bateau SET NbCoupRestant =" +(shot.getBoat().getNbCoupRestant()-1) +"WHERE IdPartie = " + shot.getGame().getGameID() + " AND IdBateau =" + shot.getBoat().getBoatID();
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            throw new SQLException();

        } finally {
            conn.setAutoCommit(true);
            theConnection.close();
        }
    }

    @Override
    public void addGame(Game game) throws SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            conn.setAutoCommit(false);
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
            conn.rollback();
            throw new SQLException();
        } finally {
            conn.setAutoCommit(true);
            theConnection.close();
        }
    }

    @Override
    public void changeTurn(Game game, Player player) throws SQLException {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            conn.setAutoCommit(false);
            String STMT = "UPDATE Partie SET Tour = '" + player.getPseudo() + "' WHERE IdPartie = " + game.getGameID();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            throw new SQLException();
        } finally {
            conn.setAutoCommit(true);
            theConnection.close();
        }
    }

    @Override
    public void turnBoat(Move move) throws SQLIntegrityConstraintViolationException, SQLException {
        String orientation = null;
        switch (move.getSens().getName()) {
            case "G":
                switch (move.getBoat().getOrientation()) {
                    case "N":
                        orientation = "O";
                        break;
                    case "S":
                        orientation = "E";
                        break;
                    case "E":
                        orientation = "N";
                        break;
                    case "O":
                        orientation = "S";
                        break;
                }
                break;
            case "D":
                switch (move.getBoat().getOrientation()) {
                    case "N":
                        orientation = "E";
                        break;
                    case "S":
                        orientation = "O";
                        break;
                    case "E":
                        orientation = "S";
                        break;
                    case "O":
                        orientation = "N";
                        break;
                }
                break;
        }
        theConnection.open();
        Connection conn = theConnection.getConn();
        conn.setAutoCommit(false);
        try {
            String STMT = "UPDATE Bateau SET Orientation = '" + orientation + "', NbCoupRestant =" +(move.getBoat().getNbCoupRestant()-1) +"WHERE IdPartie = " + move.getGame().getGameID() + " AND IdBateau =" + move.getBoat().getBoatID();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "SELECT COUNT(*) FROM Coup WHERE IdPartie ='" + move.getGame().getGameID() + "'";
            stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int IdCoup = rset.getInt(1);
            rset.close();
            stmt.close();

            STMT = "INSERT INTO Coup VALUES ('" + move.getGame().getGameID() + "','" + IdCoup + "','" + move.getBoat().getBoatID() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Deplacement VALUES ('" + move.getGame().getGameID() + "','" + IdCoup + "','" + move.getSens().getName() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            conn.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, e);
            conn.rollback();
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            throw new SQLException();
        } finally {
            conn.setAutoCommit(true);
            theConnection.close();
        }
    }

    @Override
    public void moveBoat(Move move) throws SQLIntegrityConstraintViolationException, SQLException {
        int posX = 0, posY = 0;
        switch (move.getSens().getName()) {
            case "A":
                switch (move.getBoat().getOrientation()) {
                    case "N":
                        posX = move.getBoat().getPosX();
                        posY = move.getBoat().getPosY() + 1;
                        break;
                    case "S":
                        posX = move.getBoat().getPosX();
                        posY = move.getBoat().getPosY() - 1;
                        break;
                    case "E":
                        posX = move.getBoat().getPosX() + 1;
                        posY = move.getBoat().getPosY();
                        break;
                    case "O":
                        posX = move.getBoat().getPosX() - 1;
                        posY = move.getBoat().getPosY();
                        break;
                }
                break;
            case "R":
                switch (move.getBoat().getOrientation()) {
                    case "N":
                        posX = move.getBoat().getPosX();
                        posY = move.getBoat().getPosY() - 1;
                        break;
                    case "S":
                        posX = move.getBoat().getPosX();
                        posY = move.getBoat().getPosY() + 1;
                        break;
                    case "E":
                        posX = move.getBoat().getPosX() - 1;
                        posY = move.getBoat().getPosY();
                        break;
                    case "O":
                        posX = move.getBoat().getPosX() + 1;
                        posY = move.getBoat().getPosY();
                        break;
                }
                break;
        }
        theConnection.open();
        Connection conn = theConnection.getConn();
        conn.setAutoCommit(false);
        try {
            String STMT = "UPDATE Bateau SET PosX =" + posX + ", PosY =" + posY + ", NbCoupRestant =" + (move.getBoat().getNbCoupRestant() - 1) + "WHERE IdPartie = " + move.getGame().getGameID() + " AND IdBateau =" + move.getBoat().getBoatID();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);

            STMT = "SELECT COUNT(*) FROM Coup WHERE IdPartie ='" + move.getGame().getGameID() + "'";
            stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(STMT);
            rset.next();
            int IdCoup = rset.getInt(1);
            rset.close();
            stmt.close();

            STMT = "INSERT INTO Coup VALUES ('" + move.getGame().getGameID() + "','" + IdCoup + "','" + move.getBoat().getBoatID() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Deplacement VALUES ('" + move.getGame().getGameID() + "','" + IdCoup + "','" + move.getSens().getName() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            conn.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, e);
            conn.rollback();
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            throw new SQLException();
        } finally {
            conn.setAutoCommit(true);
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
