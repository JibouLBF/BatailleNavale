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
        if (boat != null && boat.getNbCoupRestant() > 0) {
            try {
                switch (s.getName()) {
                    case "A":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() + 1);
                                boat.incrPosY();
                                break;
                            case "S":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() - 1);
                                boat.decrPosY();
                                break;
                            case "E":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() + 1, boat.getPosY());
                                boat.incrPosX();
                                break;
                            case "O":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() - 1, boat.getPosY());
                                boat.decrPosX();
                                break;
                        }
                        break;
                    case "R":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() - 1);
                                boat.decrPosY();
                                break;
                            case "S":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX(), boat.getPosY() + 1);
                                boat.incrPosY();
                                break;
                            case "E":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() - 1, boat.getPosY());
                                boat.decrPosX();
                                break;
                            case "O":
                                updater.moveBoat(IdPartie, boat.getIdBateau(), boat.getPosX() + 1, boat.getPosY());
                                boat.incrPosX();
                                break;
                        }
                        break;
                }
                updater.addMove(IdPartie, boat.getIdBateau(), s);
                boat.decrNbCoupRestant();
                notifyChanges("refreshWindow");
            } catch (SQLIntegrityConstraintViolationException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("invalid move");
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("SQLException");
            }
        } else if (boat != null && boat.getNbCoupRestant() == 0) {
            notifyChanges("no more moves");
        } else {
            notifyChanges("invalid boat selection");
        }
        
    }
    
    public void turnBoat(int x, int y, Sens s) {
        System.out.println("turn " + x + " " + y + " " + s.toString());
        Bateau boat = getPlayerBoat(x, y);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            try {
                switch (s.getName()) {
                    case "G":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "O");
                                boat.setOrientation("O");
                                break;
                            case "S":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "E");
                                boat.setOrientation("E");
                                break;
                            case "E":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "N");
                                boat.setOrientation("N");
                                break;
                            case "O":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "S");
                                boat.setOrientation("S");
                                break;
                        }
                        break;
                    case "D":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "E");
                                boat.setOrientation("E");
                                break;
                            case "S":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "O");
                                boat.setOrientation("O");
                                break;
                            case "E":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "S");
                                boat.setOrientation("S");
                                break;
                            case "O":
                                updater.turnBoat(IdPartie, boat.getIdBateau(), "N");
                                boat.setOrientation("N");
                                break;
                        }
                        break;
                }
                updater.addMove(IdPartie, boat.getIdBateau(), s);
                boat.decrNbCoupRestant();
                notifyChanges("refreshWindow");
            } catch (SQLIntegrityConstraintViolationException e) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, e);
                notifyChanges("invalid move");
            } catch (SQLException ex) {
                Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("SQLException");
            }
        } else if (boat != null && boat.getNbCoupRestant() == 0) {
            notifyChanges("no more moves");
        } else {
            notifyChanges("invalid boat selection");
        }
    }
    
    public void fire(int boatX, int boatY, int shotX, int shotY) {
        Bateau boat = getPlayerBoat(boatX, boatY);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            updater.addShot(IdPartie, boat.getIdBateau(), shotX, shotY);
            boat.decrNbCoupRestant();
            notifyChanges("shot added");
        } else if (boat != null && boat.getNbCoupRestant() == 0) {
            notifyChanges("no more moves");
        } else {
            notifyChanges("invalid boat selection");
        }
    }
    
    public void validate() {
        updater.changeTurn(IdPartie, opponent);
        notifyChanges("validate");
    }
    
    public void startGame() {
        isStarted = true;
        notifyChanges("start");
        if (isPlayer2) {
            updater.changeTurn(IdPartie, opponent);
        }
        refresh();
        try {
            playerBoatList = factory.getAllBoat(IdPartie, playerName);
        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyChanges("refreshWindow");
        
    }
    
    public void addBoats(int boatX1, int boatY1, String orientation1, int size1, int boatX2, int boatY2, String orientation2, int size2, int boatX3, int boatY3, String orientation3, int size3) throws SQLIntegrityConstraintViolationException, SQLException {
        try {
            updater.addBoats(IdPartie, playerName, boatX1, boatY1, size1, orientation1, boatX2, boatY2, size2, orientation2, boatX3, boatY3, size3, orientation3);
            playerBoatList = factory.getAllBoat(IdPartie, playerName);
            notifyChanges("boat added");
            
        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, e);
            notifyChanges("invalid position");
            throw new SQLIntegrityConstraintViolationException();
            
        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            notifyChanges("SQL exception");
            throw new SQLException();
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
