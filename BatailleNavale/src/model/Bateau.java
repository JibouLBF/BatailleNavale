/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author harbalk
 */
public class Bateau {

    private int IdBateau;
    private int IdPartie;
    private int Taille;
    private String Proprietaire;
    private int PosX;
    private int PosY;
    private Orientation orientation;
    private int Vie;
    private int PosXInit;
    private int PosYInit;

    public int getIdBateau() {
        return IdBateau;
    }

    public int getIdPartie() {
        return IdPartie;
    }

    public int getTaille() {
        return Taille;
    }

    public String getProprietaire() {
        return Proprietaire;
    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getVie() {
        return Vie;
    }

    public int getPosXInit() {
        return PosXInit;
    }

    public int getPosYInit() {
        return PosYInit;
    }

    public void setPosX(int PosX) {
        this.PosX = PosX;
    }

    public void setPosY(int PosY) {
        this.PosY = PosY;
    }

    public void setVie(int Vie) {
        this.Vie = Vie;
    }

    public Bateau(int IdBateau, int IdPartie, int Taille, String Proprietaire, int PosX, int PosY, Orientation orientation, int Vie, int PosXInit, int PosYInit) {
        this.IdBateau = IdBateau;
        this.IdPartie = IdPartie;
        this.Taille = Taille;
        this.Proprietaire = Proprietaire;
        this.PosX = PosX;
        this.PosY = PosY;
        this.orientation = orientation;
        this.Vie = Vie;
        this.PosXInit = PosXInit;
        this.PosYInit = PosYInit;
    }

}
