/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Case;
import model.Orientation;
import model.Sens;

/**
 *
 * @author jb
 */
public class JDBCUpdater implements DataBaseUpdater {

    private TheConnection theConnection;

    @Override
    public boolean addPlayer(String Pseudo, String Nom, String Prenom, String Email, int Numero, String Rue, String CodePostal, String Ville, String DateNaissance) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "INSERT INTO JOUEUR VALUES ('" + Pseudo + "','" + Nom + "','" + Prenom + "','" + Email + "','" + Numero + "','" + Rue + "','" + CodePostal + "','" + Ville + "','" + DateNaissance + "')";
            Statement stmt = conn.createStatement();
            int nb = stmt.executeUpdate(STMT);
            stmt.close();
            theConnection.close();

            return nb == 1;

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean addBoat(int IdBateau, int IdPartie, int Taille, String Proprietaire, Case c, Orientation o, int Vie) {
        try {
            theConnection.open();
            Connection conn = theConnection.getConn();
            String STMT = "INSERT INTO Bateau VALUES ('" + IdBateau + "','" + IdPartie + "','" + Taille + "','" + Proprietaire + "','" + c.getX() + "','" + c.getY() + "','" + o.getName() + "','" + Vie + "','" + c.getX() + "','" + c.getY() + "')";
            Statement stmt = conn.createStatement();
            int nb = stmt.executeUpdate(STMT);
            stmt.close();
            theConnection.close();

            return nb == 1;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void addMove(int IdPartie, int IdCoup, int IdBateau, Sens s) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "INSERT INTO Coup VALUES ('" + IdPartie + "','" + IdCoup + "','" + IdBateau + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();

            STMT = "INSERT INTO Deplacement VALUES ('" + IdPartie + "','" + IdCoup + "','" + s.getName() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            theConnection.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addShot(int IdPartie, int IdCoup, int IdBateau, Case c) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {
            String STMT = "INSERT INTO Coup VALUES ('" + IdPartie + "','" + IdCoup + "','" + IdBateau + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            STMT = "INSERT INTO Tir VALUES ('" + IdPartie + "','" + IdCoup + "','" + c.getX() + "','" + c.getY() + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            theConnection.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addGame(String Joueur1, String Joueur2) {
        theConnection.open();
        Connection conn = theConnection.getConn();
        try {

            String STMT = "INSERT INTO Partie VALUES ( seqIdPartie.nextval , CURRENT_DATE ,'" + Joueur1 + "','" + Joueur2 + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(STMT);
            stmt.close();
            theConnection.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
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
