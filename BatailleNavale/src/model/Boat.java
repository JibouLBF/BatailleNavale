/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author harbalk
 */
public class Boat {

    private int boatID;
    private Game game;
    private int size;
    private Player owner;
    private int PosX;
    private int PosY;
    private String orientation;
    private int life;
    private int PosXInit;
    private int PosYInit;
    private int nbCoupRestant;

    public int getBoatID() {
        return boatID;
    }

    public Game getGame() {
        return game;
    }

    public int getSize() {
        return size;
    }

    public Player getOwner() {
        return owner;
    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public String getOrientation() {
        return orientation;
    }

    public int getLife() {
        return life;
    }

    public int getPosXInit() {
        return PosXInit;
    }

    public int getPosYInit() {
        return PosYInit;
    }

    public void setBoatID(int boatID) {
        this.boatID = boatID;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setPosX(int PosX) {
        this.PosX = PosX;
    }

    public void setPosY(int PosY) {
        this.PosY = PosY;
    }

    public int getNbCoupRestant() {
        return nbCoupRestant;
    }

    public void decrNbCoupRestant() {
        this.nbCoupRestant--;
    }

    public void incrPosX() {
        PosX++;
    }

    public void decrPosX() {
        PosX--;
    }

    public void incrPosY() {
        PosY++;
    }

    public void decrPosY() {
        PosY--;
    }

    public void setOrientation(String o) {
        orientation = o;
    }

    public Boat(Game game, int size, Player owner, int PosX, int PosY, String orientation) {
        this.game = game;
        this.size = size;
        this.owner = owner;
        this.PosX = PosX;
        this.PosY = PosY;
        this.orientation = orientation;
        this.life = size;
        this.nbCoupRestant = size;
        this.PosXInit = PosX;
        this.PosYInit = PosY;
    }


    public Boat(Game game, int boatID, int size, Player owner, int PosX, int PosY, String orientation, int life, int PosXInit, int PosYInit, int NbCoupRestant) {
        this.boatID = boatID;
        this.game = game;
        this.size = size;
        this.owner = owner;
        this.PosX = PosX;
        this.PosY = PosY;
        this.orientation = orientation;
        this.life = life;
        this.PosXInit = PosXInit;
        this.PosYInit = PosYInit;
        this.nbCoupRestant = NbCoupRestant;
    }

    public Boat(int boatID) {
        this.boatID = boatID;
    }
    
    

}
