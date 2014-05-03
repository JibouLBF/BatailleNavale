/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;

/**
 *
 * @author jb
 */
public interface DataBaseAsker {

    /**
     * Interroge la BD et renvoie True si le joueur "Pseudo" existe
     * et False sinon
     *
     * @param Pseudo
     * @return Boolean
     * @throws SQLRecoverableException
     * @throws SQLException
     */
    public boolean playerExist(String Pseudo) throws SQLRecoverableException, SQLException;

    /**
     * Interroge la BD et renvoie True si c'est effectivement au tour du joueur "Pseudo" de jouer et False sinon
     * @param IdPartie
     * @param Pseudo
     * @return boolean
     * @throws SQLRecoverableException
     * @throws SQLException
     */
    public boolean isTurnOf(int IdPartie, String Pseudo) throws SQLRecoverableException, SQLException;

}
