/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import model.Partie;

/**
 *
 * @author jb
 */
public interface DataFactory {
    public ArrayList<Partie> getAllGames();
    
    public Partie startAGame(String Pseudo) throws SQLRecoverableException, SQLException;
}
