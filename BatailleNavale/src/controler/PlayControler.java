/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import graphicinterface.ButtonGrid;
import graphicinterface.GameWindow;
import graphicinterface.PlayerWindow;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Boat;
import model.GameModel;
import model.PlayerModel;
import model.Sens;

/**
 *
 * @author teikitel
 */
public class PlayControler extends GameControler {

    private ButtonGrid selectedButtonPlayer;
    private ButtonGrid selectedButtonOpponent;
    private PlayerWindow pw;

    public PlayControler(PlayerWindow pw) {
        this.pw = pw;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton but = ((JButton) (e.getSource()));
        if (but.isEnabled()) {
            switch (but.getText()) {
                case "Start":
                    try {
                        ArrayList<Boat> boatList = new ArrayList<Boat>();
                        boatList.add(new Boat(Integer.parseInt(pw.getPosXBoat1().getText()), Integer.parseInt(pw.getPosYBoat1().getText()), pw.getBoatOrientation1().getSelectedItem().toString(), 3));
                        boatList.add(new Boat(Integer.parseInt(pw.getPosXBoat2().getText()), Integer.parseInt(pw.getPosYBoat2().getText()), pw.getBoatOrientation2().getSelectedItem().toString(), 2));
                        if (Integer.parseInt(pw.getPosXBoat3().getText()) != 0 || Integer.parseInt(pw.getPosYBoat3().getText()) != 0) {
                            boatList.add(new Boat(Integer.parseInt(pw.getPosXBoat3().getText()), Integer.parseInt(pw.getPosYBoat3().getText()), pw.getBoatOrientation3().getSelectedItem().toString(), 2));
                        }
                        pw.getGameModel().addBoats(boatList);
                        pw.getGameModel().startGame();
                        pw.getGameModel().refreshWindow();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(pw, "Error to read position number", "Parsing Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Turn":
                    if (selectedButtonPlayer != null) {
                        if (pw.getBoatTurning().getSelectedItem().toString() == "Droite") {
                            pw.getGameModel().turnBoat(null, selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.DROITE);
                        } else if (pw.getBoatTurning().getSelectedItem().toString() == "Gauche") {
                            pw.getGameModel().turnBoat(null, selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.GAUCHE);
                        } else {
                            JOptionPane.showMessageDialog(pw, "Select a sens of moving", "error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(pw, "Select a boat in the down grid", "error", JOptionPane.ERROR_MESSAGE);
                    }

                    break;
                case "Move":
                    if (selectedButtonPlayer != null) {
                        if (pw.getBoatMoving().getSelectedItem().toString() == "Avancer") {
                            pw.getGameModel().moveBoat(null, selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.AVANCER);
                        } else if (pw.getBoatMoving().getSelectedItem().toString() == "Reculer") {
                            pw.getGameModel().moveBoat(null, selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.RECULER);
                        } else {
                            JOptionPane.showMessageDialog(pw, "Select a sens of moving", "error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(pw, "Select a boat in the down grid", "error", JOptionPane.ERROR_MESSAGE);
                    }

                    break;
                case "Shoot":
                    if (selectedButtonOpponent != null && selectedButtonPlayer != null) {
                        pw.getGameModel().fire(null, selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), selectedButtonOpponent.getPosX(), selectedButtonOpponent.getPosY());
                    } else {
                        JOptionPane.showMessageDialog(pw, "Select a case in the up grid", "error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Refresh":
                    pw.getGameModel().refresh();
                    break;
                case "Validate":
                    pw.getGameModel().validate();
                    break;
                default:
                    if (pw.getGameModel().isStarted()) {
                        pw.getGameModel().refreshWindow();
                        but.setBackground(Color.GREEN);
                        switch (but.getParent().getName()) {
                            case "player":
                                if (selectedButtonOpponent != null) {
                                    selectedButtonOpponent.setBackground(Color.green);
                                }
                                selectedButtonPlayer = (ButtonGrid) but;
                                break;
                            case "opponent":
                                if (selectedButtonPlayer != null) {
                                    selectedButtonPlayer.setBackground(Color.green);
                                }
                                selectedButtonOpponent = (ButtonGrid) but;
                                break;
                        }
                    }
                    break;
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
        JButton tmp = ((JButton) (e.getSource()));
        switch (tmp.getText()) {
            case "Start":
                break;
            case "Turn":
                break;
            case "Move":
                break;
            case "Shoot":
                break;
            case "Refresh":
                break;
            case "Validate":
                break;
            default:
                if (pw.getGameModel().isStarted()) {
                    ButtonGrid tmpButtonGrid = ((ButtonGrid) (tmp));
                    tmpButtonGrid.setText(String.valueOf((char) (tmpButtonGrid.getPosX() + 64)) + "" + tmpButtonGrid.getPosY());
                }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        switch (((JButton) (e.getSource())).getText()) {
            case "Start":
                break;
            case "Turn":
                break;
            case "Move":
                break;
            case "Shoot":
                break;
            case "Refresh":
                break;
            case "Validate":
                break;
            default:
                if (pw.getGameModel().isStarted()) {
                    ((JButton) (e.getSource())).setText("");
                }
        }
    }
}
