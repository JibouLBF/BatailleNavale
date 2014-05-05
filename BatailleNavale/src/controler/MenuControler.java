/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import graphicinterface.MenuWindow;
import graphicinterface.ObserverWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import model.Player;

/**
 *
 * @author Vincent
 */
public class MenuControler implements ActionListener {

    private MenuWindow mw;

    public MenuControler(MenuWindow mw) {
        this.mw = mw;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        System.out.println("button " + ((JButton) ae.getSource()).getText());
        switch (((JButton) ae.getSource()).getText()) {
            case "Start Game":
                mw.getMenuModel().play();
                break;
            case "Sign Up":
                mw.getMenuModel().inscription(new Player(mw.getPseudo().getText(), mw.getLastName().getText(), mw.getFirstName().getText(),
                        mw.getEmail().getText(), Integer.parseInt(mw.getNumero().getText()), mw.getStreet().getText(), mw.getCodePostal().getText(), mw.getVille().getText(), mw.getBirthday().getText()));
                break;
            case "Sign in":
                mw.getMenuModel().connection(new Player(mw.getPseudoSignIn().getText()));
                break;
            case "Observer":
                //iD de la partie à observer à passer en paramètre peut être ???
                mw.getMenuModel().observe();
                break;
            case "Disconnect":
                mw.getMenuModel().disconnect();
                break;
        }

    }

}
