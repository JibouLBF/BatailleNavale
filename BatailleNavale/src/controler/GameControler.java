/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.event.MouseListener;
import model.GameModel;

/**
 *
 * @author abikhatv
 */
public abstract class GameControler implements MouseListener {
    
    protected GameModel gm;
    
    public GameControler(GameModel gm) {
        this.gm = gm;
    }

}