/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jb
 */
public class Shot extends AbstractMove {

    private int posX;
    private int posY;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Shot(Game game, int moveID, Boat boat, int posX, int posY) {
        super(game, moveID, boat);
        this.posX = posX;
        this.posY = posY;
    }

    public Shot(Game game, Boat boat, int posX, int posY) {
        super(game, boat);
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public String toString() {
        return "Boat " + this.boat.getBoatID() + " shot (" + posX + "," + posY + ")";
    }

}
