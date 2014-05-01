/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import graphicinterface.GameWindow;
import graphicinterface.PlayerWindow;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.GameModel;

/**
 *
 * @author teikitel
 */
public class LiveControler extends GameControler {
    
    public LiveControler(GameModel gm, PlayerWindow pw) {
        this.gm=gm;
        this.pw=pw;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("but "+((JButton)(e.getSource())).getText());
        switch (((JButton)(e.getSource())).getText()){
            case "Start" :
                try{
                    gm.startGame(Integer.parseInt(pw.getPosXBoat1().getText()), Integer.parseInt(pw.getPosYBoat1().getText()), pw.getBoatOrientation1().getSelectedItem().toString(),
                        Integer.parseInt(pw.getPosXBoat2().getText()), Integer.parseInt(pw.getPosYBoat2().getText()), pw.getBoatOrientation2().getSelectedItem().toString(),
                        Integer.parseInt(pw.getPosXBoat3().getText()), Integer.parseInt(pw.getPosYBoat3().getText()), pw.getBoatOrientation3().getSelectedItem().toString()); 
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(pw, "Error to read position number", "Parsing Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Turn" :
                break;
            case "Move" :
                break;
            case "Shoot" :
                break ;
            default :
                if(gm.isStarted())
                    ((JButton)(e.getSource())).setBackground(Color.GREEN);
        }
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
        switch (((JButton)(e.getSource())).getText()){
            case "Start" :
                break;
            case "Turn" :
                break;
            case "Move" :
                break;
            case "Shoot" :
                break ;
            default :
                if(gm.isStarted())
                    ((JButton)(e.getSource())).setText(((JButton)(e.getSource())).getName());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) { 
        switch (((JButton)(e.getSource())).getText()){
            case "Start" :
                break;
            case "Turn" :
                break;
            case "Move" :
                break;
            case "Shoot" :
                break ;
            default :
                if(gm.isStarted())
                    ((JButton)(e.getSource())).setText("");
        }
    }
}
