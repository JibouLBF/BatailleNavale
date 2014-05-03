/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import model.Bateau;
import model.Joueur;
import model.Partie;
/**
 *
 * @author jb
 */
public interface DataFactory {
    /**
     * Renvoie toutes les parties qui ont été jouées
     * @return ArrayList<Partie>
     */
    public ArrayList<Partie> getAllGames();
    
    /**
     * Renvoie la partie courante du joueur "Pseudo"
     * Renvoie NULL s'il n'a pas de partie en cours
     * @param Pseudo
     * @return Partie curGame
     * @throws SQLRecoverableException
     * @throws SQLException 
     */
    public Partie getCurrentGame(String Pseudo) throws SQLRecoverableException, SQLException;
    
    /**
     * Trouve un adversaire pour le joueur "Pseudo"
     * Si on ne trouve pas d'adversaire, on renvoie null
     * Attention la partie n'est pas ajoutée dans la base de données, il faut le faire via l'updater
     * @param Pseudo
     * @return Joueur opponent
     * @throws SQLRecoverableException
     * @throws SQLException 
     */
    public Joueur findAnOpponent(String Pseudo) throws SQLRecoverableException, SQLException;
    
    public ArrayList<Bateau> getAllBoat(int IdPartie, String player) throws SQLRecoverableException, SQLException;
}
