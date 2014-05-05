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
import model.Player;
import model.Game;
import model.Sens;

/**
 *
 * @author jb
 */
public interface DataBaseUpdater {

    public void addGame(Game game);

    public void addPlayer(Player player);

    public void addBoats(Game game, Player owner, ArrayList<Boat>boatList) throws SQLIntegrityConstraintViolationException, SQLException;

    public void addMove(Game game, Boat boat, Sens s);

    public void addShot(Game game, Boat boat, int x, int y);

    public void changeTurn(Game game, Player player);

    public void turnBoat(Game game, Boat boat,String orientation) throws SQLIntegrityConstraintViolationException, SQLException;

    public void moveBoat(Game game, Boat boat, int posX, int posY) throws SQLIntegrityConstraintViolationException, SQLException;

}
