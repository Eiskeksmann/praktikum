package GUI;

import javax.swing.*;
import java.awt.*;

public class GUIServerPanel extends GUIPanel {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private Label lbl_cients, lbl_log, lbl_games;
    private List lst_log, lst_clients ,lst_games;
    private JTextField jtf;
    private JButton jbt;

    public GUIServerPanel(int width, int height) {
        super(width, height);
        addBorderPanels(this);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        initComponents();
        addComponents();
    }

    @Override
    protected void initComponents() {

        lst_log = new List(18);
        setDefaultListStyle(lst_log);
        lbl_log = new Label("ServerLog");
        setDefaultLabelStyle(lbl_log);


        lst_clients = new List(18);
        setDefaultListStyle(lst_clients);
        lbl_cients = new Label("Clients");
        setDefaultLabelStyle(lbl_cients);

        lst_games = new List(18);
        setDefaultListStyle(lst_games);
        lbl_games = new Label("Games");
        setDefaultLabelStyle(lbl_games);

        jtf = new JTextField();
        setDefaultTextFieldStyle(jtf);

        jbt = new JButton("CONFIRM");
        setDefaultButtonStyle(jbt);
    }

    @Override
    protected void addComponents() {

        layoutWest();
        layoutCenter();
        layoutEast();
    }

    private void layoutWest(){

        super.getWest().setLayout(gbl);
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx  = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        super.getWest().add(lbl_cients, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.VERTICAL;
        super.getWest().add(lst_clients, gbc);
        resetGridBagConstraints(gbc);

    }
    private void layoutCenter(){

        super.getCenter().setLayout(gbl);
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx  = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        super.getCenter().add(lbl_log ,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        super.getCenter().add(lst_log, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.8;
        gbc.weighty = 0.1;
        gbc.ipady = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        super.getCenter().add(jtf, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 0.1;
        gbc.ipady = 0;
        super.getCenter().add(jbt, gbc);

        resetGridBagConstraints(gbc);
    }
    private void layoutEast(){

        super.getEast().setLayout(gbl);
        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx  = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        super.getEast().add(lbl_games, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.VERTICAL;
        super.getEast().add(lst_games, gbc);
        resetGridBagConstraints(gbc);
    }

    //GUI Methods
    public void addLog(String str){
        lst_log.add(str);
    }
    public void addGame(String str) {lst_games.add(str); }
    public void addClient(String str) { lst_clients.add(str); }

    public void removeLog(String str){ lst_log.remove(str);}
    public void removeGame(String str) {lst_games.remove(str);}
    public void removeClient(String str) { lst_clients.remove(str);}
}
