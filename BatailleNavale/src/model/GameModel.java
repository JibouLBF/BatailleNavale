/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.DataBaseAsker;
import database.DataBaseUpdater;
import database.DataFactory;
import database.JDBCAsker;
import database.JDBCFactory;
import database.JDBCUpdater;
import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author harbalk
 */
public class GameModel extends Observable {
    
    private final DataFactory factory;
    private final DataBaseAsker asker;
    private final DataBaseUpdater updater;
    private boolean playerGame;
    private String playerName;
    private String opponent;
    private int IdPartie = -1;
    private boolean isStarted = false;
    private String date;
    private ArrayList<Bateau> boatList;
    private boolean isPlayer2;
    
    public GameModel(boolean playerGame, int IdPartie, String date, String playerName, String opponent, boolean isPlayer2) {
        this.playerGame = playerGame;
        this.IdPartie = IdPartie;
        this.playerName = playerName;
        this.opponent = opponent;
        this.date = date;
        this.isPlayer2 = isPlayer2;
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
        boatList = new ArrayList<Bateau>();
       // boatList.add(new Bateau(1, IdPartie, 3, playerName, 7, 7, Orientation.NORD, 3, 1, 1));
       // boatList.add(new Bateau(2, IdPartie, 3, playerName, 2, 7, Orientation.EST, 3, 1, 8));
    }

    // POUR QUE CA COMPILE EN ATTENDANT LES MODIFS
    public GameModel(boolean b) {
        playerGame = b;
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
    }
    
    public boolean isPlayerGame() {
        return playerGame;
    }

    public void moveBoat(int x, int y, Sens s) {
        System.out.println("move "+x+" "+y+" "+s.toString());
        notifyChanges("");
    }
    
    public void turnBoat(int x, int y, Sens s) {
        System.out.println("turn "+x+" "+y+" "+s.toString());
        notifyChanges("");
    }
    
    public void fire(int boatX, int boatY, int x, int y) {
        int i = 0;
        while (i < boatList.size()) {
            if (boatList.get(i).getPosX() == boatX && boatList.get(i).getPosY() == boatY && boatList.get(i).getProprietaire().equals(playerName)) {
                updater.addShot(IdPartie,boatList.get(i).getIdBateau(), x, y);
                notifyChanges("shot added");
                return;
            }
        }
        notifyChanges("invalid boat selection");
    }
    
    public void chooseNumberEscort(Joueur j, int n) {
        notifyChanges("");
    }
    
    public void nextRound() {
        notifyChanges("");
    }
    
    public void startGame() {
        
        notifyChanges("start");
        isStarted = true;
    }
    
    public void addBoat(int boatX, int boatY, String orientation, int size) {
        Orientation boatOrientation = Orientation.NORD;
        switch (orientation){
            case "N":
                boatOrientation = Orientation.NORD;
                break;
            case "S":
                boatOrientation = Orientation.SUD;
                break;
            case "E":
                boatOrientation = Orientation.EST;
                break;
            case "O":
                boatOrientation = Orientation.OUEST;
                break;
        }
        
        try {
            updater.addBoat(IdPartie, size, playerName, boatX, boatY, boatOrientation, size);
            notifyChanges("boat added");
        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, e);
            notifyChanges("invalid position");
            
        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            notifyChanges("SQL exception");
        }
    }
    
    public boolean isStarted() {
        return isStarted;
    }
    
    public String getOpponent() {
        return opponent;
    }
    
    public void refresh() {
        notifyChanges("boat");
    }
    
    public ArrayList<Bateau> getBoatList() {
        try {
            //appel à factory
            boatList = factory.getAllBoat(IdPartie);
            
        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return boatList;
    }
    
    private void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }
    
}
