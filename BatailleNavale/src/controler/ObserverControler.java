/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Vincent
 */
public class ObserverControler extends GameControler {

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton but = (JButton) e.getSource();
        switch (but.getName()) {
            case "Live":
                System.out.println("live");
                break;
            case "Init":
                System.out.println("init");
                break;
            case "Next":
                System.out.println("next");
                break;
            case "Prec":
                System.out.println("prec");
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
