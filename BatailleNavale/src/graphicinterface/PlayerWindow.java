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
import java.util.Observable;
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
    private JTextField posXBoat1;
    private JTextField posYBoat1;
    private JTextField posXBoat2;
    private JTextField posYBoat2;
    private JTextField posXBoat3;
    private JTextField posYBoat3;
    

    public PlayerWindow() {
        super("Player Window");
        gm = new GameModel(true);
        gc = new LiveControler(gm);
        this.gm.addObserver(this);
        
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
                    playerGrid[i][j].addMouseListener(gc);
                    player.add(playerGrid[i][j]);
                    
                    opponentGrid[i][j] = new JButton(i + "," + j);
                    opponentGrid[i][j].setSize(38, 58);
                    opponentGrid[i][j].setBackground(new Color(255,255,255,0));
                    opponentGrid[i][j].addMouseListener(gc);
                    opponent.add(opponentGrid[i][j]);
            }
        }
        consoleField = new JTextArea("Ici sera console\n");
        console.add(consoleField);
        consoleField.append("petit père\n");
        consoleField.append("grand père\n");
        consoleField.setEditable(false);
        
        playerC.setLayout(new BorderLayout());
        posXBoat1 = new JTextField ("0");
        posYBoat1 = new JTextField ("0");
        posXBoat2 = new JTextField ("0");
        posYBoat2 = new JTextField ("0");
        posXBoat3 = new JTextField ("0");
        posYBoat3 = new JTextField ("0");
        
        playerC.add(new JLabel("Choose your boat"),BorderLayout.NORTH);
        JPanel boat = new JPanel(new GridLayout(3,3));
        boat.add(new JLabel("Bateau 1")); boat.add(posXBoat1); boat.add(posYBoat1);
        boat.add(new JLabel("Bateau 2")); boat.add(posXBoat2); boat.add(posYBoat2);
        boat.add(new JLabel("Bateau 3")); boat.add(posXBoat3); boat.add(posYBoat3);
        playerC.add (boat, BorderLayout.CENTER);
        JButton start = new JButton ("Start");
        playerC.add (start, BorderLayout.SOUTH);
        start.addMouseListener(gc);
    /*    
        move.addMouseListener(gc);
        shoot.addMouseListener(gc);
        turn.addMouseListener(gc);*/
        addToFrame();
        
    }
    
    public void startGame (){
        playerC  = new JPanel(new GridLayout(4,1));
        move = new JButton ("Move");
        shoot = new JButton ("Shoot");
        turn = new JButton ("Turn");
        playerC.add(new JLabel("player controler"));
        playerC.add(move);
        playerC.add(turn);
        playerC.add(shoot);
        
        move.addMouseListener(gc);
        shoot.addMouseListener(gc);
        turn.addMouseListener(gc);
        
        mainPanelLeft.remove(1);
        mainPanelLeft.add(playerC, 1);
        addToFrame();
    }

    public JTextField getPosXBoat1() {
        return posXBoat1;
    }

    public JTextField getPosYBoat1() {
        return posYBoat1;
    }

    public JTextField getPosXBoat2() {
        return posXBoat2;
    }

    public JTextField getPosYBoat2() {
        return posYBoat2;
    }

    public JTextField getPosXBoat3() {
        return posXBoat3;
    }

    public JTextField getPosYBoat3() {
        return posYBoat3;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String) (arg)) {
            case "start":
                startGame();
                break;
        }
    }
    
    
    public static void main(String[] agrs) {
        GameWindow g = new PlayerWindow();

    }
}
