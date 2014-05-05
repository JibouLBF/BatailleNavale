/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.JDBCAsker;
import database.JDBCFactory;
import database.JDBCUpdater;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincent
 */
public class PlayerModel extends GameModel {

    public PlayerModel(boolean playerGame, Game game, Player player, Player opponent, boolean isPlayer2) {
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

    public void addBoats(ArrayList<Boat> boatList) {
        try {
            updater.addBoats(game, player, boatList);
            playerBoatList = boatList;
            notifyChanges("boat added");

        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, e);
            notifyChanges("invalid position");

        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            notifyChanges("SQL exception");
        }
    }

    public void moveBoat(Player player, int x, int y, Sens s) {
        System.out.println("move " + x + " " + y + " " + s.toString());
        Boat boat = getPlayerBoat(player, x, y);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            try {
                switch (s.getName()) {
                    case "A":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() + 1);
                                boat.incrPosY();
                                break;
                            case "S":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() - 1);
                                boat.decrPosY();
                                break;
                            case "E":
                                updater.moveBoat(game, boat, boat.getPosX() + 1, boat.getPosY());
                                boat.incrPosX();
                                break;
                            case "O":
                                updater.moveBoat(game, boat, boat.getPosX() - 1, boat.getPosY());
                                boat.decrPosX();
                                break;
                        }
                        break;
                    case "R":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() - 1);
                                boat.decrPosY();
                                break;
                            case "S":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() + 1);
                                boat.incrPosY();
                                break;
                            case "E":
                                updater.moveBoat(game, boat, boat.getPosX() - 1, boat.getPosY());
                                boat.decrPosX();
                                break;
                            case "O":
                                updater.moveBoat(game, boat, boat.getPosX() + 1, boat.getPosY());
                                boat.incrPosX();
                                break;
                        }
                        break;
                }
                updater.addMove(game, boat, s);
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

    public void turnBoat(Player player, int x, int y, Sens s) {
        System.out.println("turn " + x + " " + y + " " + s.toString());
        Boat boat = getPlayerBoat(player, x, y);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            try {
                switch (s.getName()) {
                    case "G":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.turnBoat(game, boat, "O");
                                boat.setOrientation("O");
                                break;
                            case "S":
                                updater.turnBoat(game, boat, "E");
                                boat.setOrientation("E");
                                break;
                            case "E":
                                updater.turnBoat(game, boat, "N");
                                boat.setOrientation("N");
                                break;
                            case "O":
                                updater.turnBoat(game, boat, "S");
                                boat.setOrientation("S");
                                break;
                        }
                        break;
                    case "D":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.turnBoat(game, boat, "E");
                                boat.setOrientation("E");
                                break;
                            case "S":
                                updater.turnBoat(game, boat, "O");
                                boat.setOrientation("O");
                                break;
                            case "E":
                                updater.turnBoat(game, boat, "S");
                                boat.setOrientation("S");
                                break;
                            case "O":
                                updater.turnBoat(game, boat, "N");
                                boat.setOrientation("N");
                                break;
                        }
                        break;
                }
                updater.addMove(game, boat, s);
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

    public void fire(Player player, int boatX, int boatY, int shotX, int shotY) {
        Boat boat = getPlayerBoat(player, boatX, boatY);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            updater.addShot(game, boat, shotX, shotY);
            boat.decrNbCoupRestant();
            notifyChanges("shot added");
        } else if (boat != null && boat.getNbCoupRestant() == 0) {
            notifyChanges("no more moves");
        } else {
            notifyChanges("invalid boat selection");
        }
    }

    @Override
    protected Boat getPlayerBoat(Player player, int x, int y) {
        for (int i = 0; i < playerBoatList.size(); i++) {
            if (playerBoatList.get(i).getPosX() == x && playerBoatList.get(i).getPosY() == y) {
                return playerBoatList.get(i);
            }
        }
        return null;
    }
    
    public void validate() {
        updater.changeTurn(game, opponent);
        notifyChanges("validate");
    }

    public void startGame() {
        isStarted = true;
        notifyChanges("start");
        if (isPlayer2) {
            updater.changeTurn(game, opponent);
        }
        refresh();
        try {
            playerBoatList = factory.getAllBoat(game, player);
        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyChanges("refreshWindow");

    }


    public boolean isStarted() {
        return isStarted;
    }

    public Player getOpponent() {
        return opponent;
    }
}
