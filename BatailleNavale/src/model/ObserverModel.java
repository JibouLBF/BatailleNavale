/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.JDBCAsker;
import database.JDBCFactory;
import database.JDBCUpdater;
import java.util.ArrayList;

/**
 *
 * @author Vincent
 */
public class ObserverModel extends GameModel {

    public ObserverModel(boolean playerGame, Game game) {//pour l'observeur
        this.playerGame = playerGame;
        this.game = game;
        this.player = game.getPlayer1();
        this.opponent = game.getPlayer2();
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
        playerBoatList = new ArrayList<Boat>();
        opponentBoatList = new ArrayList<Boat>();
    }

    @Override
    public void startGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
