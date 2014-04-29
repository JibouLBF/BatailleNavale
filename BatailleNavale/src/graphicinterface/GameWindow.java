/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.GameModel;

/**
 *
 * @author abikhatv
 */
public abstract class GameWindow extends JFrame {

    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    protected JPanel mainPanelLeft, mainPanelRight;
    protected JPanel player, opponent, playerC, console;
    protected JSplitPane js;
    protected GameModel gm;
    
    public GameWindow(String windowName, GameModel gm) {
        super(windowName);
        this.gm = gm;
        setSize(WIDTH, HEIGHT);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      //  mainPanelLeft = new JPanel(new GridLayout(2,1, 30, 10));
        mainPanelRight = new JPanel(new GridLayout(2,1, 30, 10));

        player = new JPanel(new GridLayout(10, 10));
        player.setBackground(Color.blue);
        
        opponent = new JPanel(new GridLayout(10, 10));
        opponent.setBackground(Color.red);

        playerC = new JPanel(new GridLayout(1, 1));
        playerC.setBackground(Color.green);

        console = new JPanel(new GridLayout(1, 1));
        console.setBackground(Color.CYAN);

     //   mainPanelLeft.add(console);
     //   mainPanelLeft.add(playerC);
        mainPanelRight.add(player);
        mainPanelRight.add(opponent);

        
    }
    
    public void addToFrame(){
        js = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanelLeft, mainPanelRight){
		    private final int location = 200;//largeur du JFrameMenu
		    {
		        setDividerLocation( location );
		    }
		    @Override
		    public int getDividerLocation() {
		        return location ;
		    }
		    @Override
		    public int getLastDividerLocation() {
		        return location ;
		    }
		};
        
        this.add(js);
        setVisible(true);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public static void main(String[] agrs) {
       // GameWindow g1 = new ObserverWindow();
       // GameWindow g2 = new PlayerWindow();
       // new MenuWindow();
    }
}
