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

/**
 *
 * @author abikhatv
 */
public class MenuModel extends Observable {

    private final DataFactory factory;
    private final DataBaseAsker asker;
    private final DataBaseUpdater updater;
    private boolean isConnected = false;
    private String pseudo;
    private ArrayList<Partie> gamesInProgress;

    public MenuModel() {
        factory = new JDBCFactory();
        asker = new JDBCAsker();
        updater = new JDBCUpdater();
    }

    public void inscription(String Pseudo, String Nom, String Prenom, String Email, int Numero, String Rue, String CodePostal, String Ville, String DateNaissance) {
        System.out.println(Pseudo + " " + Nom + " " + Prenom + " " + Email + " " + Numero + " " + Rue + " " + CodePostal + " " + Ville + " " + DateNaissance);

        // si le joueur n'est pas déjà inscrit
        if (!asker.PlayerExist(Pseudo)) {
            updater.addPlayer(Pseudo, Nom, Prenom, Email, Numero, Rue, CodePostal, Ville, DateNaissance);
            isConnected = false;
            notifyChanges("sign up");
        } //s'il est déjà inscrit --> message d'erreur à afficher ?
        else {
            notifyChanges("login already used");
        }

    }

    public void connection(String Pseudo) {
        // si on est pas déjà connecté
        if (!isConnected) {
            // si le pseudo est bien inscrit --> on affiche logged
            if (asker.PlayerExist(Pseudo)) {
                isConnected = true;
                pseudo = Pseudo;
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
            GameModel game = new GameModel(true);
            notifyChanges("play");
        } // si on est pas connecté --> affichage d'un message
        else {
            notifyChanges("not connected");
        }

    }

    public void observe() {
        //si on est connecté on lance la partie à observer dans une fenetre
        if (isConnected) {
            GameModel game = new GameModel(false);
            notifyChanges("observe");
        } //si on est pas connecté --> affichage d'un message
        else {
            notifyChanges("not connected");
        }

    }

    public void disconnect() {
        isConnected = false;
        pseudo = null;
        notifyChanges("disconnect");
    }

    private void notifyChanges(String s) { //PATTERN OBSERVER
        // TODO Auto-generated method stub
        //System.err.println("notification...");
        setChanged();
        notifyObservers(s);
    }

    public ArrayList<Partie> getGameInProgress() {
        gamesInProgress = factory.getAllGames();
        return gamesInProgress;
    }

    public boolean isIsConnected() {
        return isConnected;
    }

    public String getPseudo() {
        return pseudo;
    }

}
