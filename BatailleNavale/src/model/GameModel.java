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
import java.util.Observable;
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

    public GameModel(boolean playerGame, int IdPartie, String date, String playerName, String opponent) {
        this.playerGame = playerGame;
        this.IdPartie = IdPartie;
        this.playerName = playerName;
        this.opponent = opponent;
        this.date = date;
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
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

    public void moveBoat(Joueur j, Bateau b, Sens s) {
        notifyChanges("");
    }

    public void fire(Joueur j, Bateau b, Case c) {
        notifyChanges("");
    }

    public void placeBoat(Joueur j, Bateau b, Case c, Orientation o) {

        notifyChanges("");
    }

    public void chooseNumberEscort(Joueur j, int n) {
        notifyChanges("");
    }

    public void nextRound() {
        notifyChanges("");
    }
    
    public void startGame(int boatX1, int boatY1, String boatOrientation1, 
            int boatX2, int boatY2, String boatOrientation2, 
            int boatX3, int boatY3, String boatOrientation3) {
         notifyChanges("start");
         isStarted = true;
    }

    public boolean isStarted (){
        return isStarted;
    }
    
    private void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }

    


}
