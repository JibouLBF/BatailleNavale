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
    private boolean isMyTurn = false;
    private String date;
    private ArrayList<Bateau> playerBoatList;
    private ArrayList<Bateau> opponentBoatList;
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
        playerBoatList = new ArrayList<Bateau>();
        opponentBoatList = new ArrayList<Bateau>();
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

    /**
     * Renvoie le bateau du joueur placé à la position (x,y) Renvoie NULL si il
     * n'y pas de bateau à cette position
     *
     * @param x
     * @param y
     * @return Bateau boat
     */
    private Bateau getPlayerBoat(int x, int y) {
        for (int i = 0; i < playerBoatList.size(); i++) {
            if (playerBoatList.get(i).getPosX() == x && playerBoatList.get(i).getPosY() == y) {
                return playerBoatList.get(i);
            }
        }
        return null;
    }

    public void moveBoat(int x, int y, Sens s) {
        System.out.println("move " + x + " " + y + " " + s.toString());
        Bateau boat = getPlayerBoat(x, y);
        if (boat != null) {
            switch (s.getName()) {
                case "A":
                    switch (boat.getOrientation().getName()) {
                        case "N":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() + 1);
                            break;
                        case "S":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() - 1);
                            break;
                        case "E":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() + 1, boat.getPosY());
                            break;
                        case "O":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() - 1, boat.getPosY());
                            break;
                    }
                    break;
                case "R":
                    switch (boat.getOrientation().getName()) {
                        case "N":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() - 1);
                            break;
                        case "S":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() + 1);
                            break;
                        case "E":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() - 1, boat.getPosY());
                            break;
                        case "O":
                            updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() + 1, boat.getPosY());
                            break;
                    }
                    break;
            }
            updater.addMove(IdPartie, boat.getIdBateau(), s);
            notifyChanges("move added");
        } else {
            notifyChanges("invalid boat selection");
        }
    }

    public void turnBoat(int x, int y, Sens s) {
        System.out.println("turn " + x + " " + y + " " + s.toString());
        Bateau boat = getPlayerBoat(x, y);
        if (boat != null) {
            switch (s.getName()) {
                case "G":
                    switch (boat.getOrientation().getName()) {
                        case "N":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.OUEST);
                            break;
                        case "S":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.EST);
                            break;
                        case "E":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.NORD);
                            break;
                        case "O":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.SUD);
                            break;
                    }
                    break;
                case "D":
                    switch (boat.getOrientation().getName()) {
                        case "N":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.EST);
                            break;
                        case "S":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.OUEST);
                            break;
                        case "E":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.SUD);
                            break;
                        case "O":
                            updater.turnBoat(IdPartie, boat.getIdBateau(), Orientation.NORD);
                            break;
                    }
                    break;
            }
            updater.addMove(IdPartie, boat.getIdBateau(), s);
            notifyChanges("turn added");
        } else {
            notifyChanges("invalid boat selection");
        }
    }

    public void fire(int boatX, int boatY, int shotX, int shotY) {
        Bateau boat = getPlayerBoat(boatX, boatY);
        if (boat != null) {
            updater.addShot(IdPartie, boat.getIdBateau(), shotX, shotY);
            notifyChanges("shot added");
        } else {
            notifyChanges("invalid boat selection");
        }
    }

    public void nextRound() {
        notifyChanges("");
    }

    public void startGame() {

        notifyChanges("start");
        if (isPlayer2) {
            updater.changeTurn(IdPartie, opponent);
        }
        isStarted = true;
    }

    public void addBoat(int boatX, int boatY, String orientation, int size) {
        Orientation boatOrientation = Orientation.NORD;
        switch (orientation) {
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
            Logger.getLogger(GameModel.class
                    .getName()).log(Level.SEVERE, null, e);
            notifyChanges(
                    "invalid position");

        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class
                    .getName()).log(Level.SEVERE, null, ex);
            notifyChanges(
                    "SQL exception");
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public String getOpponent() {
        return opponent;
    }

    public void refresh() {
        try {
            isMyTurn = asker.isTurnOf(IdPartie, playerName);
            if (isMyTurn) {
                playerBoatList = factory.getAllBoat(IdPartie, playerName);
                opponentBoatList = factory.getAllBoat(IdPartie, opponent);
                notifyChanges("your turn");
            } else {
                notifyChanges("opponent turn");

            }
        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Bateau> getPlayerBoatList() {
        return playerBoatList;
    }

    public ArrayList<Bateau> getOpponentBoatList() {
        return opponentBoatList;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    private void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }

    public void refreshWindow() {
        notifyChanges("refreshWindow");    
    }

}
