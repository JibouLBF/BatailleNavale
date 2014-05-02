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
import java.util.ArrayList;
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
    private ArrayList<Bateau> boatList;
    private boolean isPlayer2;

    public GameModel(boolean playerGame, int IdPartie, String date, String playerName, String opponent, boolean isPlayer2) {
        this.playerGame = playerGame;
        this.IdPartie = IdPartie;
        this.playerName = playerName;
        this.opponent = opponent;
        this.date = date;
        this.isPlayer2= isPlayer2;
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
        boatList = new ArrayList<Bateau> ();
        boatList.add(new Bateau(1, IdPartie, 3, playerName, 7, 7, Orientation.NORD, 3, 1,1));
        boatList.add(new Bateau(2, IdPartie, 3, playerName, 2, 7, Orientation.EST, 3, 1,8));    
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

    public String getOpponent() {
        return opponent;
    }
    
    public void refresh (){
        notifyChanges("boat");
    }
    
    public ArrayList<Bateau> getBoatList (){
        //appel à factory
        //boatList = factory.getAllBoat(IdPartie);
        return boatList;
    }
    
    
    private void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }

    


}
