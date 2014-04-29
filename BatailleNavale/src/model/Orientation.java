/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author harbalk
 */
public enum Orientation {

    //Objets directement construits
    NORD("N"),
    SUD("S"),
    EST("E"),
    OUEST("O");
    //    private final String environnement;
    private final String name;

    Orientation(String name) {
        this.name = name;

    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "  " + name.toUpperCase();
    }

}
