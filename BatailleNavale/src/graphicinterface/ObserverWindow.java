/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicinterface;

import controler.LiveControler;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
        super("Observer Window", new GameModel (false));
        mainPanelLeft = new JPanel(new GridLayout(3,1, 30, 10));
        control = new JPanel (new GridLayout (3,1));
        control.add(begin);
        control.add(next);
        control.add(prec);
        mainPanelLeft.add(console);
        mainPanelLeft.add(playerC);
        mainPanelLeft.add(control);
        
        begin.addMouseListener(new LiveControler(new GameModel(true)));
        next.addMouseListener(new LiveControler(new GameModel(true)));
        prec.addMouseListener(new LiveControler(new GameModel(true)));
        
        gridJ1 = new JLabel [10][10];
        gridJ2 = new JLabel [10][10];
        consoleJ1 = new JTextArea("console J1");
        consoleJ2 = new JTextArea("console J2");
        
        for (int i = 0; i< 10; i++){
            for (int j = 0; j<10; j++){
                gridJ1[i][j] = new JLabel (""+i+","+j);
                gridJ2[i][j] = new JLabel (""+i+","+j);
                gridJ1[i][j].setSize(38,58);
                gridJ2[i][j].setSize(38,58);
                
                player.add(gridJ1[i][j], BorderLayout.CENTER);
                opponent.add(gridJ2[i][j], BorderLayout.CENTER);
            }
        }
        consoleJ1 = new JTextArea("Console J1");
        consoleJ2 = new JTextArea("Console J2");
        consoleJ1.setEditable(false);
        consoleJ2.setEditable(false);
        
        playerC.add(consoleJ1);
        console.add(consoleJ2);
        
        addToFrame();
    }
    
    public static void main (String args []){
        GameWindow gw = new ObserverWindow();
    }
    
}
