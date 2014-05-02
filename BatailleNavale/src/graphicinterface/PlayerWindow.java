/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicinterface;

import controler.LiveControler;
import controler.MenuControler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import javax.swing.*;
import javax.swing.border.Border;
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
    private String[] comboText = {"N", "S", "E", "W"};
    private JComboBox boatOrientation1 = new JComboBox(comboText);
    private JComboBox boatOrientation2 = new JComboBox(comboText);
    private JComboBox boatOrientation3 = new JComboBox(comboText);

    public PlayerWindow(GameModel gm) {

        super("Player Window");
        this.gm = gm;
        gc = new LiveControler(gm, this);
        this.gm.addObserver(this);

        mainPanelLeft = new JPanel(new GridLayout(2, 1, 30, 10));
        mainPanelLeft.add(console);
        mainPanelLeft.add(playerC);

        playerGrid = new JButton[10][10];
        opponentGrid = new JButton[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                playerGrid[i][j] = new JButton();
                playerGrid[i][j].setBackground(Color.RED);
                playerGrid[i][j].setName("" + (i + 1) + ", " + (j + 1));
                playerGrid[i][j].addMouseListener(gc);
                player.add(playerGrid[i][j]);

                opponentGrid[i][j] = new JButton();
                opponentGrid[i][j].setBackground(Color.BLUE);
                opponentGrid[i][j].setName("" + (i + 1) + ", " + (j + 1));
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
        posXBoat1 = new JTextField("0");
        posYBoat1 = new JTextField("0");
        posXBoat2 = new JTextField("0");
        posYBoat2 = new JTextField("0");
        posXBoat3 = new JTextField("0");
        posYBoat3 = new JTextField("0");

        playerC.add(new JLabel("Choose your boat"), BorderLayout.NORTH);
        JPanel boat = new JPanel(new GridLayout(3, 1));

        Border borderBoat1 = BorderFactory.createTitledBorder("Destroyeur boat");
        JPanel JBoat1 = new JPanel(new GridLayout(1, 3));
        JBoat1.setBorder(borderBoat1);
        JBoat1.add(posXBoat1);
        JBoat1.add(posYBoat1);
        JBoat1.add(boatOrientation1);
        boat.add(JBoat1);

        Border borderBoat2 = BorderFactory.createTitledBorder("Escorter boat 1");
        JPanel JBoat2 = new JPanel(new GridLayout(1, 3));
        JBoat2.setBorder(borderBoat2);
        JBoat2.add(posXBoat2);
        JBoat2.add(posYBoat2);
        JBoat2.add(boatOrientation2);
        boat.add(JBoat2);

        Border borderBoat3 = BorderFactory.createTitledBorder("Escorter boat 2");
        JPanel JBoat3 = new JPanel(new GridLayout(1, 3));
        JBoat3.setBorder(borderBoat3);
        JBoat3.add(posXBoat3);
        JBoat3.add(posYBoat3);
        JBoat3.add(boatOrientation3);
        boat.add(JBoat3);

        playerC.add(boat, BorderLayout.CENTER);

        JButton start = new JButton("Start");
        playerC.add(start, BorderLayout.SOUTH);
        start.addMouseListener(gc);

        addToFrame();
        JOptionPane.showMessageDialog(this, "You are going to play against "+gm.getOpponent(), "Info", JOptionPane.INFORMATION_MESSAGE);


    }

    public void startGame (){
        playerC  = new JPanel(new GridLayout(5,1));
        move = new JButton ("Move");
        shoot = new JButton ("Shoot");
        turn = new JButton ("Turn");
        playerC.add(new JLabel("player controler"));
        playerC.add(move);
        playerC.add(turn);
        playerC.add(shoot);
        playerC.add(refresh);
        
        move.addMouseListener(gc);
        shoot.addMouseListener(gc);
        turn.addMouseListener(gc);
        refresh.addMouseListener(gc);
        
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

    public JComboBox getBoatOrientation1() {
        return boatOrientation1;
    }

    public JComboBox getBoatOrientation2() {
        return boatOrientation2;
    }

    public JComboBox getBoatOrientation3() {
        return boatOrientation3;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String) (arg)) {
            case "start":
                startGame();
                break;
            case "boat":
                drawBoat();
                break;

        }
    }

    public static void main(String[] agrs) {
        GameModel gm = new GameModel(true);
        GameWindow g = new PlayerWindow(gm);

    }

    @Override
    public void drawBoat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
