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
public class Move extends AbstractMove {

    private Sens sens;

    public Sens getSens() {
        return sens;
    }

    public Move(Game game, int moveID, Boat boat, Sens sens) {
        super(game, moveID, boat);
        this.sens = sens;
    }

    public Move(Game game, Boat boat,Sens sens) {
        super(game, boat);
        this.sens = sens;
    }

    @Override
    public String toString(){
        return "Boat " + this.boat.getBoatID() + " move "+sens.getName();
    }
}
