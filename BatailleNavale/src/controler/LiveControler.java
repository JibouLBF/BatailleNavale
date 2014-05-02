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
import model.Sens;

/**
 *
 * @author teikitel
 */
public class LiveControler extends GameControler {
    private int posXInit;
    private int posYInit;
    private int posX;
    private int posY;
    
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
                //gm.moveBoat(posXInit, posYInit, Sens.DROITE);
                break;
            case "Move" :
                gm.moveBoat(posXInit, posYInit, Sens.AVANCER);
                break;
            case "Shoot" :
                break ;
            case "Refresh" :
                gm.refresh();
                break ;
            default :
                if(gm.isStarted()){
                    ((JButton)(e.getSource())).setBackground(Color.GREEN);
                    switch(((JButton)(e.getSource())).getParent().getName()){
                        case "opponent" :
                            posXInit = ((JButton)(e.getSource())).getParent().getX();
                            posYInit = ((JButton)(e.getSource())).getParent().getX();
                            break;
                        case "player" :
                            posX = ((JButton)(e.getSource())).getParent().getX();
                            posY = ((JButton)(e.getSource())).getParent().getX();
                            break;
                    }
                }
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
            case "Refresh" :
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
            case "Refresh" :
                break ;
            default :
                if(gm.isStarted())
                    ((JButton)(e.getSource())).setText("");
        }
    }
}
