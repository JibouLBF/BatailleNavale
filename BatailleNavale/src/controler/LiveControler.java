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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.GameModel;
import model.Sens;

/**
 *
 * @author teikitel
 */
public class LiveControler extends GameControler {

    private ButtonGrid selectedButtonPlayer;
    private ButtonGrid selectedButtonOpponent;

    public LiveControler(GameModel gm, PlayerWindow pw) {
        this.gm = gm;
        this.pw = pw;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JButton but = ((JButton) (e.getSource()));
        if (but.isEnabled()) {
            switch (but.getText()) {
                case "Start":
                    try {
                        gm.addBoats(Integer.parseInt(pw.getPosXBoat1().getText()), Integer.parseInt(pw.getPosYBoat1().getText()), pw.getBoatOrientation1().getSelectedItem().toString(), 3, Integer.parseInt(pw.getPosXBoat2().getText()), Integer.parseInt(pw.getPosYBoat2().getText()), pw.getBoatOrientation2().getSelectedItem().toString(), 2, Integer.parseInt(pw.getPosXBoat3().getText()), Integer.parseInt(pw.getPosYBoat3().getText()), pw.getBoatOrientation3().getSelectedItem().toString(), 2);
                        gm.startGame();
                        gm.refreshWindow();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(pw, "Error to read position number", "Parsing Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        Logger.getLogger(LiveControler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "Turn":
                    if (selectedButtonPlayer != null) {
                        if (pw.getBoatTurning().getSelectedItem().toString() == "Droite") {
                            gm.turnBoat(selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.DROITE);
                        } else if (pw.getBoatTurning().getSelectedItem().toString() == "Gauche") {
                            gm.turnBoat(selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.GAUCHE);
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
                            gm.moveBoat(selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.AVANCER);
                        } else if (pw.getBoatMoving().getSelectedItem().toString() == "Reculer") {
                            gm.moveBoat(selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), Sens.RECULER);
                        } else {
                            JOptionPane.showMessageDialog(pw, "Select a sens of moving", "error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(pw, "Select a boat in the down grid", "error", JOptionPane.ERROR_MESSAGE);
                    }

                    break;
                case "Shoot":
                    if (selectedButtonOpponent != null && selectedButtonPlayer != null) {
                        gm.fire(selectedButtonPlayer.getPosX(), selectedButtonPlayer.getPosY(), selectedButtonOpponent.getPosX(), selectedButtonOpponent.getPosY());
                    } else {
                        JOptionPane.showMessageDialog(pw, "Select a case in the up grid", "error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Refresh":
                    gm.refresh();
                    break;
                case "Validate":
                    gm.validate();
                    break;
                default:
                    if (gm.isStarted()) {
                        gm.refreshWindow();
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
                if (gm.isStarted()) {
                    ButtonGrid tmpButtonGrid = ((ButtonGrid) (tmp));
                    tmpButtonGrid.setText(tmpButtonGrid.getPosX() + ", " + tmpButtonGrid.getPosY());
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
                if (gm.isStarted()) {
                    ((JButton) (e.getSource())).setText("");
                }
        }
    }
}
