/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import model.Sens;

/**
 *
 * @author jb
 */
public interface DataBaseUpdater {
    
    /**
     * Ajoute une partie entre les Joueur1 et Joueur2 à la table Partie (Tour est mis à NULL par défaut)
     * @param Joueur1
     * @param Joueur2 
     */
    public void addGame(String Joueur1, String Joueur2);
    
    /**
     * Ajoute un joueur à la table Joueur
     * @param Pseudo
     * @param Nom
     * @param Prenom
     * @param Email
     * @param Numero
     * @param Rue
     * @param CodePostal
     * @param Ville
     * @param DateNaissance 
     */
    public void addPlayer(String Pseudo, String Nom, String Prenom, String Email, int Numero, String Rue, String CodePostal, String Ville, String DateNaissance);

    
    public void addBoats(int IdPartie, String Proprietaire, int PosXB1, int PosYB1, int TailleB1, String oB1, int PosXB2, int PosYB2, int TailleB2, String oB2, int PosXB3, int PosYB3, int TailleB3, String oB3) throws SQLRecoverableException, SQLException;

    /**
     * Ajoute un coup à la table Coup ainsi qu'un déplacement à la table Deplacement
     * @param IdPartie
     * @param IdBateau
     * @param s 
     */
    public void addMove(int IdPartie, int IdBateau, Sens s);

    /**
     * Ajoute un coup à la table Coup ainsi qu'un tir à la table Tir
     * @param IdPartie
     * @param IdBateau
     * @param x
     * @param y 
     */
    public void addShot(int IdPartie, int IdBateau, int x, int y);
    
    /**
     * Met à jour l'attribut Tour de la partie "IdPartie" avec la valeur "Pseudo"
     * @param IdPartie
     * @param Pseudo 
     */
    public void changeTurn(int IdPartie, String Pseudo);
    
    /**
     * Met à jour l'attribut Orientation du bateau "IdBateau"
     * @param IdPartie
     * @param IdBateau
     * @param o 
     */
    public void turnBoat(int IdPartie, int IdBateau, String orientation);
    
    /**
     * Met à jour les attributs PosX et PosY du bateau "IdBateau"
     * @param IdPartie
     * @param IdBateau
     * @param posX
     * @param posY 
     */
    public void moveBoat(int IdPartie, int IdBateau, int posX, int posY);
    
    
    
}
