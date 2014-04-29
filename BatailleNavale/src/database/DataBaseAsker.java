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
        public boolean PlayerExist(String Pseudo) throws SQLRecoverableException, SQLException;

}
