/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import model.Orientation;
import model.Sens;
import model.Case;

/**
 *
 * @author jb
 */
public interface DataBaseUpdater {

    public void addGame(String Joueur1, String Joueur2);
    
    public boolean addPlayer(String Pseudo, String Nom, String Prenom, String Email, int Numero, String Rue, String CodePostal, String Ville, String DateNaissance);

    public boolean addBoat(int IdPartie, int Taille, String Proprietaire, int PosX, int PosY, Orientation o, int Vie);

    public void addMove(int IdPartie, int IdCoup, int IdBateau, Sens s);

    public void addShot(int IdPartie, int IdCoup, int IdBateau, Case c);
    
    
    
}
