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

    public GameModel(boolean b) {
        this.playerGame = b;
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
    }

    public boolean isPlayerGame() {
        return playerGame;
    }

    public void moveBoat(Joueur j, Bateau b, Sens s) {
        notifyChanges();
    }

    public void fire(Joueur j, Bateau b, Case c) {
        notifyChanges();
    }

    public void placeBoat(Joueur j, Bateau b, Case c, Orientation o) {

        notifyChanges();
    }

    public void chooseNumberEscort(Joueur j, int n) {
        notifyChanges();
    }

    public void nextRound() {
        notifyChanges();
    }

    private void notifyChanges() { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers();
    }
}
