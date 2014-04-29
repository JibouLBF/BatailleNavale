/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicinterface;

import controler.LiveControler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.*;
import model.GameModel;

/**
 *
 * @author teikitel
 */
public class PlayerWindow extends GameWindow {

    private JButton[][] playerGrid, opponentGrid;
    private JTextArea consoleField;
    private JButton shoot;
    private JButton move;
    private JButton turn;
    

    public PlayerWindow(GameModel gm) {
        super("Player Window", gm);
        mainPanelLeft = new JPanel(new GridLayout(2,1, 30, 10));
        mainPanelLeft.add(console);
        mainPanelLeft.add(playerC);
        
        playerGrid = new JButton[10][10];
        opponentGrid = new JButton[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                    playerGrid[i][j] = new JButton(i + "," + j);
                    playerGrid[i][j].setSize(38, 58);
                    playerGrid[i][j].setBackground(new Color(255,255,255,0));
                    //playerGrid[i][j].setIcon(new ImageIcon("ressources/image_grid/playerCell.jpg"));
                    playerGrid[i][j].addMouseListener(new LiveControler(new GameModel(true)));
                    player.add(playerGrid[i][j]);
                    
                    opponentGrid[i][j] = new JButton(i + "," + j);
                    opponentGrid[i][j].setSize(38, 58);
                    opponentGrid[i][j].setBackground(new Color(255,255,255,0));
                    opponentGrid[i][j].addMouseListener(new LiveControler(new GameModel(true)));
                    opponent.add(opponentGrid[i][j]);
            }
        }
        consoleField = new JTextArea("Ici sera console\n");
        console.add(consoleField);
        consoleField.append("petit père\n");
        consoleField.append("grand père\n");
        consoleField.setEditable(false);
        
        playerC.setLayout(new GridLayout(4, 1));
        move = new JButton ("Move");
        shoot = new JButton ("Shoot");
        turn = new JButton ("Turn");
        playerC.add(new JLabel("player controler"));
        playerC.add(move);
        playerC.add(turn);
        playerC.add(shoot);
        
        addToFrame();
    }

    public static void main(String[] agrs) {
        //GameWindow g = new PlayerWindow();
    }
}
