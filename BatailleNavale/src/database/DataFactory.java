/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import model.AbstractMove;
import model.Boat;
import model.Game;
import model.Player;

/**
 *
 * @author jb
 */
public interface DataFactory {

    public ArrayList<Game> getAllGames();

    public Game getCurrentGame(Player player) throws SQLRecoverableException, SQLException;

    public Player findAnOpponent(Player player) throws SQLRecoverableException, SQLException;

    public ArrayList<Boat> getAllBoat(Game game, Player player) throws SQLRecoverableException, SQLException;
    
    public PriorityQueue<AbstractMove> getPlayerLastMoves(Game game, Player player) throws SQLException;
    
    public PriorityQueue<AbstractMove> getGameMoves(Game game) throws SQLException;
}
