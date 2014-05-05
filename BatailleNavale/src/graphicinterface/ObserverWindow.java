/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import javax.swing.*;
import model.GameModel;


/**
 *
 * @author abikhatv
 */
public class ObserverWindow extends GameWindow {
    private JLabel[][] gridJ1, gridJ2;
    private JTextArea consoleJ1, consoleJ2;
    private JPanel control;
    JButton begin = new JButton("Begin");
    JButton next = new JButton("Next");
    JButton prec = new JButton("Prec");
    
    public ObserverWindow (){
        super("Observer Window");
        gm = new GameModel(false);
        //gc = new LiveControler(gm);
        mainPanelLeft = new JPanel(new GridLayout(3,1, 30, 10));
        control = new JPanel (new GridLayout (4,1));
        control.add(begin);
        control.add(next);
        control.add(prec);
        control.add(refresh);
        mainPanelLeft.add(console);
        mainPanelLeft.add(playerC);
        mainPanelLeft.add(control);
        
  /*     begin.addMouseListener(new ObserverControler(new GameModel(true)));
        next.addMouseListener(new ObserverControler(new GameModel(true)));
        prec.addMouseListener(new ObserverControler(new GameModel(true)));*/
        
        gridJ1 = new JLabel [10][10];
        gridJ2 = new JLabel [10][10];
        consoleJ1 = new JTextArea("console J1");
        consoleJ2 = new JTextArea("console J2");
        
        for (int i = 0; i< 10; i++){
            for (int j = 0; j<10; j++){
                gridJ1[i][j] = new JLabel (""+i+","+j, JLabel.CENTER);
                gridJ2[i][j] = new JLabel (""+i+","+j, JLabel.CENTER);
                gridJ1[i][j].setOpaque(true);
                gridJ2[i][j].setOpaque(true);
                
                gridJ1[i][j].setBackground(Color.RED);
                gridJ2[i][j].setBackground(Color.BLUE);
                
                player.add(gridJ1[i][j]);
                opponent.add(gridJ2[i][j]);
            }
        }
        consoleJ1 = new JTextArea("There are no informations");
        consoleJ2 = new JTextArea("There are no informations");
        consoleJ1.setEditable(false);
        consoleJ2.setEditable(false);
        
        playerC.add(consoleJ1);
        console.add(consoleJ2);
        
        addToFrame();
    }
    
    public static void main (String args []){
        GameWindow gw = new ObserverWindow();
    }

    @Override
    public void update(Observable o, Object arg) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawBoat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
