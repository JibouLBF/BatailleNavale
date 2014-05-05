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
public abstract class GameModel extends Observable {

    protected DataFactory factory;
    protected DataBaseAsker asker;
    protected DataBaseUpdater updater;
    protected boolean playerGame;
    protected Game game;
    protected Player player;
    protected Player opponent;
    protected boolean isStarted = false;
    protected boolean isMyTurn = false;
    protected ArrayList<Boat> playerBoatList;
    protected ArrayList<Boat> opponentBoatList;
    protected boolean isPlayer2;

    /*  public GameModel(boolean playerGame, Game game, Player player, Player opponent, boolean isPlayer2) {
     this.playerGame = playerGame;
     this.player = player;
     this.opponent = opponent;
     this.game = game;
     this.isPlayer2 = isPlayer2;
     factory = new JDBCFactory();
     asker = new JDBCAsker();
     updater = new JDBCUpdater();
     playerBoatList = new ArrayList<Boat>();
     opponentBoatList = new ArrayList<Boat>();
     }
    
     public GameModel(boolean playerGame, Game game) {//pour l'observeur
     this.playerGame = playerGame;
     this.game = game;
     this.player = game.getPlayer1();
     this.opponent = game.getPlayer2();
     factory = new JDBCFactory();
     asker = new JDBCAsker();
     updater = new JDBCUpdater();
     playerBoatList = new ArrayList<Boat>();
     opponentBoatList = new ArrayList<Boat>();
     }*/
    // POUR QUE CA COMPILE EN ATTENDANT LES MODIFS
   /* public GameModel(boolean b) {
     playerGame = b;
     factory = new JDBCFactory();
     asker = new JDBCAsker();
     updater = new JDBCUpdater();
     }*/

    /*public boolean isPlayerGame() {
     return playerGame;
     }*/
    /**
     * Renvoie le bateau du joueur placé à la position (x,y) Renvoie NULL si il
     * n'y pas de bateau à cette position
     *
     * @param x
     * @param y
     * @return Bateau boat
     */
    protected abstract Boat getPlayerBoat(Player player, int x, int y);
    public abstract void moveBoat(Player player, int x, int y, Sens s);
    public abstract void turnBoat(Player player, int x, int y, Sens s);
    public abstract void fire(Player player, int boatX, int boatY, int shotX, int shotY);

    public void refresh() {
        try {
            isMyTurn = asker.isTurnOf(game, player);
            if (isMyTurn) {
                playerBoatList = factory.getAllBoat(game, player);
                opponentBoatList = factory.getAllBoat(game, opponent);
                notifyChanges("your turn");
            } else {
                notifyChanges("opponent turn");

            }
        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            notifyChanges("SQL exception");

        }
    }

    public ArrayList<Boat> getPlayerBoatList() {
        return playerBoatList;
    }

    public ArrayList<Boat> getOpponentBoatList() {
        return opponentBoatList;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    protected void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }
    
    public abstract void startGame();

    public void refreshWindow() {
        notifyChanges("refreshWindow");
    }

}
