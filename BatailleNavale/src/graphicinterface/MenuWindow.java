/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicinterface;

import controler.MenuControler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import model.MenuModel;
import model.Partie;

/**
 *
 * @author abikhatv
 */
public class MenuWindow extends JFrame implements Observer {

    private final int WIDTH = 800;
    private final int HEIGHT = 800;

    JSplitPane js;
    JPanel left;
    JPanel right;

    JTable tableGame;
    Object[][] data;

    MenuModel mm;

    JLabel stateConnection;

    JTextField pseudo;
    JTextField firstName;
    JTextField lastName;
    JTextField birthday;
    JTextField email;
    JTextField numero;
    JTextField street;
    JTextField codePostal;
    JTextField ville;

    JTextField pseudoSignIn;

    // table des parties à droite
    DefaultTableModel dm;

    public MenuWindow(MenuModel mm) {
        super("Welcome");
        this.mm = mm;
        this.mm.addObserver(this);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        left = new JPanel(new GridLayout(3, 1));
        right = new JPanel(new BorderLayout());

        js = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right) {
            private final int location = 300;//largeur du JFrameMenu

            {
                setDividerLocation(location);
            }

            @Override
            public int getDividerLocation() {
                return location;
            }

            @Override
            public int getLastDividerLocation() {
                return location;
            }
        };

        createLeftPanel();
        createRightPanel();

        this.add(js);

        this.setVisible(true);
        this.invalidate();
        this.validate();
        this.repaint();

    }

    public void createLeftPanel() {
        //BUTTON TO START GAME
        Border borderConnection = BorderFactory.createTitledBorder("Connection");
        JPanel connection = new JPanel(new GridLayout(2, 1));
        connection.setBorder(borderConnection);
        stateConnection = new JLabel("Unlogged");
        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(new MenuControler(this));
        connection.add(stateConnection);
        connection.add(startGame);
        left.add(connection);

        //SIGN UP FORMULARY
        Border borderSignUp = BorderFactory.createTitledBorder("Sign up");
        JPanel signUp = new JPanel(new GridLayout(10, 2));
        signUp.setBorder(borderSignUp);

        pseudo = new JTextField();
        firstName = new JTextField();
        lastName = new JTextField();
        birthday = new JTextField();
        email = new JTextField();
        numero = new JTextField();
        street = new JTextField();
        codePostal = new JTextField();
        ville = new JTextField();
        
        JLabel pseudoLabel = new JLabel("Pseudo");
        JLabel firstNameLabel = new JLabel("First Name");
        JLabel lastNameLabel = new JLabel("Last Name");
        JLabel birthdayLabel = new JLabel("Birthday");
        JLabel emailLabel = new JLabel("Email");
        JLabel numeroLabel = new JLabel("Numero");
        JLabel streetLabel = new JLabel("Street");
        JLabel codePostalLabel = new JLabel("Code Postal");
        JLabel villeLabel = new JLabel("Ville");

        JButton bSignUp = new JButton("Sign Up");
        bSignUp.addActionListener(new MenuControler(this));
        signUp.add(pseudoLabel); signUp.add(pseudo);
        signUp.add(firstNameLabel); signUp.add(firstName);
        signUp.add(lastNameLabel); signUp.add(lastName);
        signUp.add(birthdayLabel); signUp.add(birthday);
        signUp.add(emailLabel); signUp.add(email);
        signUp.add(numeroLabel); signUp.add(numero);
        signUp.add(streetLabel); signUp.add(street);
        signUp.add(codePostalLabel); signUp.add(codePostal);
        signUp.add(villeLabel); signUp.add(ville);
        signUp.add(new JLabel()); signUp.add(bSignUp);
        left.add(signUp);

        //SIGN IN
        Border borderSignIn = BorderFactory.createTitledBorder("Sign in");
        JPanel signIn = new JPanel(new GridLayout(3, 1));
        signIn.setBorder(borderSignIn);
        pseudoSignIn = new JTextField("Pseudo");
        JButton bSignin = new JButton("Sign in");
        bSignin.addActionListener(new MenuControler(this));
        JButton bDisconnect = new JButton("Disconnect");
        bDisconnect.addActionListener(new MenuControler(this));
        signIn.add(pseudoSignIn);
        signIn.add(bSignin);
        signIn.add(bDisconnect);

        left.add(signIn);
    }

    public void createRightPanel() {
        dm = new DefaultTableModel();

        dm.setColumnIdentifiers(new Object[]{"IdPartie", "Pseudo player 1", "Pseudo player 1", "Date", "Winner", "Replay", "Live"});

        /*dm.setDataVector(new Object[][]{{"1","JB", "KEKE", "01/01/01", "", "Observe"},
         {"2","Tekitel", "bibi", "01/01/01", "", "Observe"}}, new Object[]{"IdPartie","Pseudo player 1", "Pseudo player 1", "Date", "Winner", ""});
         */
        tableGame = new JTable(dm);
        tableGame.getColumn("Replay").setCellRenderer(new ButtonRenderer());
        tableGame.getColumn("Replay").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        tableGame.getColumn("Live").setCellRenderer(new ButtonRenderer());
        tableGame.getColumn("Live").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        right.add(tableGame.getTableHeader(), BorderLayout.NORTH);
        right.add(tableGame, BorderLayout.CENTER);

    }

    public MenuModel getMenuModel() {
        return this.mm;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public MenuModel getMm() {
        return mm;
    }

    public JTextField getPseudo() {
        return pseudo;
    }

    public JTextField getFirstName() {
        return firstName;
    }

    public JTextField getLastName() {
        return lastName;
    }

    public JTextField getBirthday() {
        return birthday;
    }

    public JTextField getEmail() {
        return email;
    }

    public JTextField getNumero() {
        return numero;
    }

    public JTextField getStreet() {
        return street;
    }

    public JTextField getCodePostal() {
        return codePostal;
    }

    public JTextField getVille() {
        return ville;
    }

    public JTextField getPseudoSignIn() {
        return pseudoSignIn;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String) (arg)) {
            case "sign up":
                JOptionPane.showMessageDialog(this, "Sign up successful", "Sign up", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "login already used":
                JOptionPane.showMessageDialog(this, "Login already used. \n Please select another one", "Sign up", JOptionPane.ERROR_MESSAGE);
                break;
            case "connected":
                JOptionPane.showMessageDialog(this, "Connection successful", "Connection", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "not signed up":
                JOptionPane.showMessageDialog(this, "Connection failed : invalid login. \n Please retype it or sign up", "Connection", JOptionPane.ERROR_MESSAGE);
                break;
            case "already connected":
                JOptionPane.showMessageDialog(this, "You are already connected", "Connection", JOptionPane.ERROR_MESSAGE);
                break;
            case "play":
                JOptionPane.showMessageDialog(this, "You are going to play against another player", "Play", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "not connected":
                JOptionPane.showMessageDialog(this, "You are not connected. \n Please sign in to play or observe", "Play/Observe", JOptionPane.ERROR_MESSAGE);
                break;
            case "observe":
                JOptionPane.showMessageDialog(this, "You are going to watch a game", "Observe", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "disconnect":
                JOptionPane.showMessageDialog(this, "You are now unlogged", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "SQL Exception":
                JOptionPane.showMessageDialog(this, "SQL problem. Sorry", "SQL Exception", JOptionPane.ERROR_MESSAGE);
                break;
            case "Connection Exception":
                JOptionPane.showMessageDialog(this, "Database connection problem. Sorry", "Connection Exception", JOptionPane.ERROR_MESSAGE);
                break;
        }
        System.out.println("obervé!!");
        if (mm.isIsConnected()) {
            // changement du texte logged
            stateConnection.setText("Logged as " + mm.getPseudo());

            // mise à jour de la table des parties
            dm = new DefaultTableModel();
            dm.setColumnIdentifiers(new Object[]{"IdPartie", "Pseudo player 1", "Pseudo player 1", "Date", "Winner", "Replay", "Live"});
            ArrayList<Partie> listGame = mm.getGameInProgress();
            if (!listGame.isEmpty()) {
                for (Partie curGame : listGame) {
                    dm.addRow(new Object[]{curGame.getiDPartie(), curGame.getPlayer1(), curGame.getPlayer2(), curGame.getDate(), curGame.getWinner(), "Replay", "Live"});

                }
            }

            tableGame.setModel(dm);
            tableGame.getColumn("Replay").setCellRenderer(new ButtonRenderer());
            tableGame.getColumn("Replay").setCellEditor(new ButtonEditor(new JCheckBox(), this));
            tableGame.getColumn("Live").setCellRenderer(new ButtonRenderer());
            tableGame.getColumn("Live").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        } // si pas connecté on met Unlogged et efface la table partie
        else {
            stateConnection.setText("Unlogged");
            dm = new DefaultTableModel();
            dm.setColumnIdentifiers(new Object[]{"IdPartie", "Pseudo player 1", "Pseudo player 1", "Date", "Winner", "Replay", "Live"});
            tableGame.setModel(dm);
        }

    }

    public static void main(String args[]) {
        //MenuWindow mw = new MenuWindow();
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;

    private String label;

    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox, MenuWindow mw) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new MenuControler(mw));
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    //fenetre popup
    public Object getCellEditorValue() {
        if (isPushed) {
            // 
            // 
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
            // System.out.println(label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
