/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import model.GameModel;

/**
 *
 * @author teikitel
 */
public class LiveControler extends GameControler {
    
    public LiveControler(GameModel gm) {
        super(gm);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("but "+((JButton)(e.getSource())).getText());
        //gm.
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
}
