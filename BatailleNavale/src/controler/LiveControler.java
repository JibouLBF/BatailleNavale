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
import model.Orientation;
import model.Sens;

/**
 *
 * @author teikitel
 */
public class LiveControler extends GameControler {

    private int posXInit = -1;
    private int posYInit = -1;
    private int posX = -1;
    private int posY = -1;

    public LiveControler(GameModel gm, PlayerWindow pw) {
        this.gm = gm;
        this.pw = pw;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("but "+((JButton)(e.getSource())).getText());
        JButton but = ((JButton) (e.getSource()));
        switch (but.getText()) {
            case "Start":
                try {
                    gm.addBoat(Integer.parseInt(pw.getPosXBoat1().getText()), Integer.parseInt(pw.getPosYBoat1().getText()), pw.getBoatOrientation1().getSelectedItem().toString(), 3);
                    gm.addBoat(Integer.parseInt(pw.getPosXBoat2().getText()), Integer.parseInt(pw.getPosYBoat2().getText()), pw.getBoatOrientation1().getSelectedItem().toString(), 2);
                    if (0 < Integer.parseInt(pw.getPosXBoat3().getText()) && Integer.parseInt(pw.getPosXBoat3().getText()) <= 10
                            && 0 < Integer.parseInt(pw.getPosYBoat3().getText()) && Integer.parseInt(pw.getPosYBoat3().getText()) <= 10) {
                        gm.addBoat(Integer.parseInt(pw.getPosXBoat3().getText()), Integer.parseInt(pw.getPosYBoat3().getText()), pw.getBoatOrientation1().getSelectedItem().toString(), 2);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(pw, "Error to read position number", "Parsing Error", JOptionPane.ERROR_MESSAGE);
                }
                gm.startGame();
                gm.refresh();
                break;
            case "Turn":
                if (posXInit != -1 && posYInit != -1) {
                    if (pw.getBoatTurning().getSelectedItem().toString() == "Droite") {
                        gm.turnBoat(posXInit-1, posYInit-1, Sens.DROITE);
                    } else if (pw.getBoatTurning().getSelectedItem().toString() == "Gauche") {
                        gm.turnBoat(posXInit-1, posYInit-1, Sens.GAUCHE);
                    } else {
                        JOptionPane.showMessageDialog(pw, "Select a sens of moving", "error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(pw, "Select a boat in the down grid", "error", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case "Move":
                if (posXInit != -1 && posYInit != -1) {
                    if (pw.getBoatMoving().getSelectedItem().toString() == "Avancer") {
                        gm.moveBoat(posXInit-1, posYInit-1, Sens.AVANCER);
                    } else if (pw.getBoatMoving().getSelectedItem().toString() == "Reculer") {
                        gm.moveBoat(posXInit-1, posYInit-1, Sens.RECULER);
                    } else {
                        JOptionPane.showMessageDialog(pw, "Select a sens of moving", "error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(pw, "Select a boat in the down grid", "error", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case "Shoot":
                if (posXInit != -1 && posYInit != -1 && posX != -1 && posY != -1) {
                    gm.fire(posXInit-1, posYInit-1, posX-1, posY-1);
                } else {
                    JOptionPane.showMessageDialog(pw, "Select a case in the up grid", "error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Refresh":
                gm.refresh();
                break;
            default:
                if (gm.isStarted()) {
                    System.out.println("pos " + ((but.getY()) / but.getHeight()) + " " + but.getY() + " " + but.getHeight());
                    gm.refreshWindow();
                    but.setBackground(Color.GREEN);
                    switch (but.getParent().getName()) {
                        case "opponent":
                            if (posX != -1 && posY != -1) {
                                pw.getPlayerGrid()[posX - 1][posY - 1].setBackground(Color.green);
                            }
                            posXInit = ((but.getY()) / but.getHeight()) + 1;
                            posYInit = ((but.getX() - 4) / but.getWidth()) + 1;
                            break;
                        case "player":
                            if (posXInit != -1 && posXInit != -1) {
                                pw.getOpponentGrid()[posXInit - 1][posYInit - 1].setBackground(Color.green);
                            }
                            posX = ((but.getY()) / but.getHeight()) + 1;
                            posY = ((but.getX() - 4) / but.getWidth()) + 1;
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
            default:
                if (gm.isStarted()) {
                    ((JButton) (e.getSource())).setText(((JButton) (e.getSource())).getName());
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
            default:
                if (gm.isStarted()) {
                    ((JButton) (e.getSource())).setText("");
                }
        }
    }
}
