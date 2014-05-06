/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import model.Boat;
import model.Game;
import model.Move;
import model.Player;
import model.Sens;
import model.Shot;

/**
 *
 * @author jb
 */
public interface DataBaseUpdater {

    public void addGame(Game game) throws SQLException;

    public void addPlayer(Player player) throws SQLException;

    public void addBoats(ArrayList<Boat> boatList) throws SQLIntegrityConstraintViolationException, SQLException;

    public void addShot(Shot shot) throws SQLException;

    public void changeTurn(Game game, Player player) throws SQLException;

    public void turnBoat(Move move) throws SQLIntegrityConstraintViolationException, SQLException;

    public void moveBoat(Move move) throws SQLIntegrityConstraintViolationException, SQLException;

}
