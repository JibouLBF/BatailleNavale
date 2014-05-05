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
public class Game {

    private int gameID;
    private String date;
    private Player Player1;
    private Player Player2;
    private Player winner;

    public int getGameID() {
        return gameID;
    }

    public String getDate() {
        return date;
    }

    public Player getPlayer1() {
        return Player1;
    }

    public Player getPlayer2() {
        return Player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Game(int gameID, String date, Player Player1, Player Player2, Player winner) {
        this.gameID = gameID;
        this.date = date;
        this.Player1 = Player1;
        this.Player2 = Player2;
        this.winner = winner;
    }

    public Game(Player Player1, Player Player2) {
        this.Player1 = Player1;
        this.Player2 = Player2;
    }
    
    

}
