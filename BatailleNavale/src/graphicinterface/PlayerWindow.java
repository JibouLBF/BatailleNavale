/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicinterface;

import controler.LiveControler;
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
    private String[] comboText = {"N", "S", "E", "O"};
    private String[] turning = {"Gauche", "Droite"};
    private String[] moving = {"Avancer", "Reculer"};
    private JComboBox boatOrientation1 = new JComboBox(comboText);
    private JComboBox boatOrientation2 = new JComboBox(comboText);
    private JComboBox boatOrientation3 = new JComboBox(comboText);
    private JComboBox boatTurning = new JComboBox(turning);
    private JComboBox boatMoving = new JComboBox(moving);
    
    public PlayerWindow(GameModel gm) {

        super("Player Window");
        this.gm = gm;
        gc = new LiveControler(gm, this);
        this.gm.addObserver(this);
        
        mainPanelLeft = new JPanel(new GridLayout(2, 1, 30, 10));
        JPanel highPan = new JPanel(new BorderLayout());
        mainPanelLeft.add(highPan);
        
        highPan.add(turnLabel, BorderLayout.NORTH);
        highPan.add(console, BorderLayout.CENTER);
        mainPanelLeft.add(playerC);

        playerGrid = new JButton[10][10];
        opponentGrid = new JButton[10][10];
        for (int j = 9; j >= 0; j--) {
            for (int i = 0; i < 10; i++) {
                playerGrid[i][j] = new ButtonGrid(i+1, j+1);
                playerGrid[i][j].setBackground(Color.RED);
                playerGrid[i][j].setName("" + (i + 1) + ", " + (j + 1));
                playerGrid[i][j].addMouseListener(gc);
                player.add(playerGrid[i][j]);

                opponentGrid[i][j] = new ButtonGrid(i+1, j+1);
                opponentGrid[i][j].setBackground(Color.BLUE);
                opponentGrid[i][j].setName("" + (i + 1) + ", " + (j + 1));
                opponentGrid[i][j].addMouseListener(gc);
                opponent.add(opponentGrid[i][j]);
            }
        }
        consoleField = new JTextArea("Ici sera console\n");
        console.add(consoleField);
        consoleField.append("there are no informations");
        consoleField.setEditable(false);

        playerC.setLayout(new BorderLayout());
        posXBoat1 = new JTextField("0");
        posYBoat1 = new JTextField("0");
        posXBoat2 = new JTextField("0");
        posYBoat2 = new JTextField("0");
        posXBoat3 = new JTextField("0");
        posYBoat3 = new JTextField("0");

        playerC.add(new JLabel("Choose your boat", JLabel.CENTER), BorderLayout.NORTH);
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
        JOptionPane.showMessageDialog(this, "You are going to play against " + gm.getOpponent(), "Info", JOptionPane.INFORMATION_MESSAGE);
        
    }

    public void startGame() {
        playerC = new JPanel(new GridLayout(5, 1));
        move = new JButton("Move");
        shoot = new JButton("Shoot");
        turn = new JButton("Turn");
        playerC.add(new JLabel("Player Controler", JLabel.CENTER));

        Border borderMove = BorderFactory.createTitledBorder("Moving");
        JPanel moveP = new JPanel(new GridLayout(1, 2));
        moveP.setBorder(borderMove);
        moveP.add(move);
        moveP.add(boatMoving);
        playerC.add(moveP);

        Border borderTurn = BorderFactory.createTitledBorder("Turning");
        JPanel turnP = new JPanel(new GridLayout(1, 2));
        turnP.setBorder(borderTurn);
        turnP.add(turn);
        turnP.add(boatTurning);
        playerC.add(turnP);

        Border borderShoot = BorderFactory.createTitledBorder("Shooting");
        JPanel shootP = new JPanel(new GridLayout(1, 1));
        shootP.setBorder(borderShoot);
        shootP.add(shoot);
        playerC.add(shootP);

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

    public JComboBox getBoatTurning() {
        return boatTurning;
    }

    public JComboBox getBoatMoving() {
        return boatMoving;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String) (arg)) {
            case "start":
                startGame();
                break;
            case "your turn":
                JOptionPane.showMessageDialog(this, "It's your turn, you can play", "Info", JOptionPane.INFORMATION_MESSAGE);
                drawBoat();
                turnLabel.setBackground(Color.GREEN);
                turnLabel.setText("it is your turn");
                break;
            case "opponent turn":
                JOptionPane.showMessageDialog(this, "Your opponent hasn't played yet.\n Please wait your turn.", "Info", JOptionPane.INFORMATION_MESSAGE);
                turnLabel.setBackground(Color.RED);
                turnLabel.setText("it is not your turn");
                break;
            case "refreshWindow" :
                drawBoat();
                break;
        }
    }

    public static void main(String[] agrs) {
        GameModel gm = new GameModel(true, 2, "", "test1", "test2", true);
        GameWindow g = new PlayerWindow(gm);

    }

    @Override
    public void drawBoat() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                opponentGrid[i][j].setBackground(Color.BLUE);
                playerGrid[i][j].setBackground(Color.RED);
            }
        }
        consoleField.setText("Life Boat\n");
        for (int i = 0; i < gm.getPlayerBoatList().size(); i++) {
            opponentGrid[gm.getPlayerBoatList().get(i).getPosX()-1][gm.getPlayerBoatList().get(i).getPosY()-1].setBackground(Color.ORANGE);
            consoleField.append("Boat " + (i + 1) + " : " + gm.getPlayerBoatList().get(i).getVie()+" "+ gm.getPlayerBoatList().get(i).coupRestant() + "\n");
            switch (gm.getPlayerBoatList().get(i).getOrientation()) {
                case "N":
                    for (int j = 1; j < gm.getPlayerBoatList().get(i).getTaille(); j++) {
                        opponentGrid[gm.getPlayerBoatList().get(i).getPosX()-1][gm.getPlayerBoatList().get(i).getPosY()-1+j].setBackground(Color.BLACK);
                    }
                    break;
                case "S":
                    for (int j = 1; j < gm.getPlayerBoatList().get(i).getTaille(); j++) {
                        opponentGrid[gm.getPlayerBoatList().get(i).getPosX() -1][gm.getPlayerBoatList().get(i).getPosY()-1-j].setBackground(Color.BLACK);
                    }
                    break;
                case "E":
                    for (int j = 1; j < gm.getPlayerBoatList().get(i).getTaille(); j++) {
                        opponentGrid[gm.getPlayerBoatList().get(i).getPosX()-1+j][gm.getPlayerBoatList().get(i).getPosY() -1].setBackground(Color.BLACK);
                    }
                    break;
                case "O":
                    for (int j = 1; j < gm.getPlayerBoatList().get(i).getTaille(); j++) {
                        opponentGrid[gm.getPlayerBoatList().get(i).getPosX()-1-j][gm.getPlayerBoatList().get(i).getPosY() - 1].setBackground(Color.BLACK);
                    }
                    break;
            }
        }

    }

    public JButton[][] getPlayerGrid() {
        return playerGrid;
    }

    public JButton[][] getOpponentGrid() {
        return opponentGrid;
    }

}
