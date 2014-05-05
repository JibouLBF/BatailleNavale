/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import model.Player;
import model.Game;

/**
 *
 * @author jb
 */
public interface DataBaseAsker {

    public boolean playerExist(Player player) throws SQLRecoverableException, SQLException;

    public boolean isTurnOf(Game game, Player player) throws SQLRecoverableException, SQLException;

    public boolean hasPlacedBoats(Game game, Player player) throws SQLRecoverableException, SQLException;

}
