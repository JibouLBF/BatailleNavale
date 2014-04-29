/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.util.ArrayList;
import model.Partie;

/**
 *
 * @author jb
 */
public interface DataFactory {
    public ArrayList<Partie> getAllGames();
    
}
