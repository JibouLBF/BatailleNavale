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
public enum Sens {

    //Objets directement construits
    AVANCER("A"),
    RECULER("R"),
    GAUCHE("G"),
    DROITE("D");
    //    private final String environnement;
    private final String name;

    Sens(String name) {
        this.name = name;

    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "  " + name.toUpperCase();
    }
}
