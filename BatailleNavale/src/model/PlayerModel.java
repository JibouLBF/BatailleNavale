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
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincent
 */
public class PlayerModel extends GameModel {

    PriorityQueue<AbstractMove> opponentLastMoves;

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
        opponentLastMoves = new PriorityQueue<AbstractMove>();
    }

    public void addBoats(int posXB1, int posYB1, String orientationB1, int sizeB1, int posXB2, int posYB2, String orientationB2, int sizeB2, int posXB3, int posYB3, String orientationB3, int sizeB3) {
        try {
            playerBoatList.add(new Boat(game, sizeB1, player, posXB1, posYB1, orientationB1));
            playerBoatList.add(new Boat(game, sizeB2, player, posXB2, posYB2, orientationB2));
            if (posXB3 != 0 || posYB3 != 0) {
                playerBoatList.add(new Boat(game, sizeB2, player, posXB2, posYB2, orientationB2));
            }

            updater.addBoats(playerBoatList);
            notifyChanges("boat added");

        } catch (SQLIntegrityConstraintViolationException e) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, e);
            playerBoatList.clear();
            notifyChanges("invalid position");

        } catch (SQLException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
            playerBoatList.clear();
            notifyChanges("SQL exception");
        }
    }

    public void moveBoat(int x, int y, Sens s) {
        System.out.println("move " + x + " " + y + " " + s.toString());
        Boat boat = getPlayerBoat(x, y);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            try {
                switch (s.getName()) {
                    case "A":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() + 1, s);
                                boat.incrPosY();
                                break;
                            case "S":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() - 1, s);
                                boat.decrPosY();
                                break;
                            case "E":
                                updater.moveBoat(game, boat, boat.getPosX() + 1, boat.getPosY(), s);
                                boat.incrPosX();
                                break;
                            case "O":
                                updater.moveBoat(game, boat, boat.getPosX() - 1, boat.getPosY(), s);
                                boat.decrPosX();
                                break;
                        }
                        break;
                    case "R":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() - 1, s);
                                boat.decrPosY();
                                break;
                            case "S":
                                updater.moveBoat(game, boat, boat.getPosX(), boat.getPosY() + 1, s);
                                boat.incrPosY();
                                break;
                            case "E":
                                updater.moveBoat(game, boat, boat.getPosX() - 1, boat.getPosY(), s);
                                boat.decrPosX();
                                break;
                            case "O":
                                updater.moveBoat(game, boat, boat.getPosX() + 1, boat.getPosY(), s);
                                boat.incrPosX();
                                break;
                        }
                        break;
                }
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
        Boat boat = getPlayerBoat(x, y);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            try {
                switch (s.getName()) {
                    case "G":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.turnBoat(game, boat, "O", s);
                                boat.setOrientation("O");
                                break;
                            case "S":
                                updater.turnBoat(game, boat, "E", s);
                                boat.setOrientation("E");
                                break;
                            case "E":
                                updater.turnBoat(game, boat, "N", s);
                                boat.setOrientation("N");
                                break;
                            case "O":
                                updater.turnBoat(game, boat, "S", s);
                                boat.setOrientation("S");
                                break;
                        }
                        break;
                    case "D":
                        switch (boat.getOrientation()) {
                            case "N":
                                updater.turnBoat(game, boat, "E", s);
                                boat.setOrientation("E");
                                break;
                            case "S":
                                updater.turnBoat(game, boat, "O", s);
                                boat.setOrientation("O");
                                break;
                            case "E":
                                updater.turnBoat(game, boat, "S", s);
                                boat.setOrientation("S");
                                break;
                            case "O":
                                updater.turnBoat(game, boat, "N", s);
                                boat.setOrientation("N");
                                break;
                        }
                        break;
                }
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
        Boat boat = getPlayerBoat(boatX, boatY);
        if (boat != null && boat.getNbCoupRestant() > 0) {
            try {
                updater.addShot(game, boat, shotX, shotY);
                boat.decrNbCoupRestant();
                notifyChanges("shot added");
            } catch (SQLException ex) {
                Logger.getLogger(PlayerModel.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("SQL exception");
            }
        } else if (boat != null && boat.getNbCoupRestant() == 0) {
            notifyChanges("no more moves");
        } else {
            notifyChanges("invalid boat selection");
        }
    }

    @Override
    protected Boat getPlayerBoat(int x, int y) {
        for (int i = 0; i < playerBoatList.size(); i++) {
            if (playerBoatList.get(i).getPosX() == x && playerBoatList.get(i).getPosY() == y) {
                return playerBoatList.get(i);
            }
        }
        return null;
    }
}
