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

    private Sens s;

    public Sens getS() {
        return s;
    }

    public Move(Game game, int moveID, Boat boat, Sens s) {
        super(game, moveID, boat);
        this.s = s;
    }

}
