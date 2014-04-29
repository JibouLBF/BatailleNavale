/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bataillenavale;

import graphicinterface.GameWindow;
import graphicinterface.MenuWindow;
import graphicinterface.ObserverWindow;
import graphicinterface.PlayerWindow;
import model.GameModel;
import model.MenuModel;

/**
 *
 * @author abikhatv
 */
public class BatailleNavale {
    private GameWindow gw;
    private GameModel gm;
    
    private MenuWindow mw;
    private MenuModel mm;
    
    public BatailleNavale () {
       mm = new MenuModel ();
       
       mw = new MenuWindow (mm);
    }
    
    public void createGame (){
        gm = new GameModel(true);
        gw = new PlayerWindow ();
    }
    
    public void createObserver (){
        gm = new GameModel(false);
        gw = new PlayerWindow ();
    }
    
    public static void main (String [] args){
        new BatailleNavale();
        
       // new PlayerWindow();
       // new ObserverWindow();
    }
}
