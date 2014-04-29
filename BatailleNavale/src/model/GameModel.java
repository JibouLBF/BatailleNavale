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

    public GameModel(boolean b, int IdPartie, String playerName) {
        this.playerGame = b;
        this.IdPartie = IdPartie;
        this.playerName = playerName;
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
    
    public void startGame (int posX1, int posY1, int posX2, int posY2,
            int posX3, int posY3){
        //ajout des bateau
        notifyChanges("start");
    }

    public boolean isStarted (){
        if (IdPartie != -1)
            return true;
        return false;
    }
    
    private void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }
}
