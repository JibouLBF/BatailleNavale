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
import database.TheConnection;
import graphicinterface.GameWindow;
import graphicinterface.ObserverWindow;
import graphicinterface.PlayerWindow;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abikhatv
 */
public class MenuModel extends Observable {

    private final DataFactory factory;
    private final DataBaseAsker asker;
    private final DataBaseUpdater updater;
    private boolean isConnected = false;
    private Player player;
    private ArrayList<Game> gamesInProgress;
    private GameWindow gw;
    private GameModel gm;

    public MenuModel() {
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
    }

    public void inscription(Player player) {

        // si le joueur n'est pas déjà inscrit
        boolean ret = false;
        try {
            ret = asker.playerExist(player);
        } catch (SQLRecoverableException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            notifyChanges("Connection Exception");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
            notifyChanges("SQL Exception");
        }
        if (!ret) {
            updater.addPlayer(player);
            isConnected = false;
            notifyChanges("sign up");
        } //s'il est déjà inscrit --> message d'erreur à afficher ?
        else {
            notifyChanges("login already used");
        }

    }

    public void connection(Player player) {
        // si on est pas déjà connecté
        if (!isConnected) {
            // si le pseudo est bien inscrit --> on affiche logged
            boolean ret = false;
            try {
                ret = asker.playerExist(player);
            } catch (SQLRecoverableException ex) {
                Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("Connection Exception");
            } catch (SQLException ex) {
                Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("SQL Exception");
            }
            if (ret) {
                isConnected = true;
                this.player = player;
                notifyChanges("connected");
            } // si on est pas inscrit --> affichage d'un message 
            else {
                notifyChanges("not signed up");
            }
        } // si on est déjà connecté --> affichage d'un message 
        else {
            notifyChanges("already connected");
        }

    }

    public void play() {
        //si on est connecté on lance une partie avec un autre joueur
        if (isConnected) {
            try {
                Game game = factory.getCurrentGame(player);
                // s'il n'a pas de partie en cours on cherche un adversaire libre
                if (game == null) {
                    Player opponent = factory.findAnOpponent(player);
                    // si on a pas trouvé d'adversaire libre --> message
                    if (opponent == null) {
                        notifyChanges("no opponent");
                        return;
                    }
                    game = new Game(player, opponent);
                    updater.addGame(game);
                    //game = factory.getCurrentGame(pseudo);
                }
                // Instanciation du modèle et de la fenetre de jeu
                if (game.getPlayer1().getPseudo().equals(player.getPseudo())) {
                    gm = new GameModel(true, game, player, game.getPlayer2(), false);
                } else {
                    gm = new GameModel(true, game, player, game.getPlayer1(), true);
                }
                gw = new PlayerWindow(gm);
                if (asker.hasPlacedBoats(game, player)) {
                    gm.startGame();
                }
            } catch (SQLRecoverableException ex) {
                Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("Connection Exception");
            } catch (SQLException ex) {
                Logger.getLogger(JDBCFactory.class.getName()).log(Level.SEVERE, null, ex);
                notifyChanges("SQL Exception");
            }
        }// si on est pas connecté --> affichage d'un message 
        else {
            notifyChanges("not connected");
        }

    }

    public void observe() {
        //si on est connecté on lance la partie à observer dans une fenetre
        if (isConnected) {
            gw = new ObserverWindow();
            notifyChanges("observe");
        } //si on est pas connecté --> affichage d'un message
        else {
            notifyChanges("not connected");
        }

    }

    public void disconnect() {
        isConnected = false;
        player = null;
        notifyChanges("disconnect");
    }

    private void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }

    public ArrayList<Game> getGameInProgress() {
        gamesInProgress = factory.getAllGames();
        return gamesInProgress;
    }

    public boolean isIsConnected() {
        return isConnected;
    }

    public Player getPlayer() {
        return player;
    }

}
