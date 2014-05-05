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
public abstract class AbstractMove implements Comparable<AbstractMove>{

    private Game game;
    private int moveID;
    private Boat boat;

    public Game getGame() {
        return game;
    }

    public int getMoveID() {
        return moveID;
    }

    public Boat getBoat() {
        return boat;
    }
    
    @Override
    public int compareTo(AbstractMove other){
        if(other.getMoveID() == this.moveID)
            return 0;
        else if(other.getMoveID() < this.moveID)
            return 1;
        else
            return -1;
    }

    public AbstractMove(Game game, int moveID, Boat boat) {
        this.game = game;
        this.moveID = moveID;
        this.boat = boat;
    }

}
