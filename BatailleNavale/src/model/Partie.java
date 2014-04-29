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
public class Partie {

    private int iDPartie;
    private String date;
    private String Player1;
    private String Player2;
    private String winner;

    public Partie(int iDPartie, String date, String Player1, String Player2, String winner) {
        this.iDPartie = iDPartie;
        this.date = date;
        this.Player1 = Player1;
        this.Player2 = Player2;
        this.winner = winner;
    }

    public int getiDPartie() {
        return iDPartie;
    }

    public String getDate() {
        return date;
    }

    public String getPlayer1() {
        return Player1;
    }

    public String getPlayer2() {
        return Player2;
    }

    public String getWinner() {
        return winner;
    }

}
