package GUI;

import netClient.Client;
import util.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class GUIGameSettings extends GUIPanel implements ActionListener {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private Vector<Integer> vsize_x;
    private Vector<Integer> vsize_y;
    private Vector<String> vgametype;

    private JCheckBox cb_single, cb_multi;
    private JComboBox jcb_gametype, jcb_sizex, jcb_sizey;
    private JButton confirm;
    private Client client;
    private String target, gametype, size_x, size_y, sm;
    private Command gamestart;

    public GUIGameSettings(int width, int height) {
        super(width, height);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        vgametype = new Vector<>();
        vsize_y = new Vector<>();
        vsize_x = new Vector<>();
        initComponents();
        addComponents();
    }

    public void setClient(Client client){ this.client = client; }
    public void setTarget(String target){ this.target = target; }
    public Command getGamestart(){ return gamestart; }

    @Override
    protected void initComponents() {

        this.setBackground(COL_DARKERGREY);
        this.setLayout(gbl);

        for(int i = 1; i < 31; i++){
            vsize_x.add(i);
            vsize_y.add(i);
        }

        vgametype.add("Viergewinnt");
        vgametype.add("Futtern");

        cb_single = new JCheckBox("SINGLE");
        setDefaultCheckBoxStyle(cb_single);
        cb_single.setSelected(false);
        cb_single.addActionListener(this);

        cb_multi = new JCheckBox("MULTI");
        setDefaultCheckBoxStyle(cb_multi);
        cb_multi.setSelected(true);
        cb_multi.addActionListener(this);

        jcb_gametype = new JComboBox(vgametype);
        setDefaultComboBoxStyle(jcb_gametype);

        jcb_sizex = new JComboBox(vsize_x);
        setDefaultComboBoxStyle(jcb_sizex);

        jcb_sizey = new JComboBox(vsize_y);
        setDefaultComboBoxStyle(jcb_sizey);

        confirm = new JButton("confirm");
        setDefaultButtonStyle(confirm);
        confirm.addActionListener(this);
    }

    @Override
    protected void addComponents() {

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,2,2);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weightx = 0.5;
        gbc.weighty = 0.33;
        this.add(jcb_gametype, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        this.add(jcb_sizex, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(jcb_sizey, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(cb_multi, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(cb_single, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.gridwidth  = 3;
        this.add(confirm, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == confirm) {

                gametype = jcb_gametype.getSelectedItem().toString();
                size_x = jcb_sizex.getSelectedItem().toString();
                size_y  = jcb_sizey.getSelectedItem().toString();

                if(cb_single.isSelected()){

                    sm = "single";
                } else if (cb_multi.isSelected()){

                    sm = "multi";
                }
                client.tryCustomCommand(new Command("SENDREQUEST|" + target + "|" + gametype, "|"));
            }
        }
        catch (IOException ex) {

            ex.printStackTrace();
        }

        if(e.getSource() == cb_single){

            cb_multi.setSelected(false);
        }
        if(e.getSource() == cb_multi){

            cb_single.setSelected(false);
        }
    }

    public void getAccepted(){

        try{

            if(sm.equals("multi")) {

                gamestart = new Command("HOST|" + gametype + "|" +
                        size_x + "|" + size_y + "|" + target, "|");

            } else if(sm.equals("single")) {

                gamestart = new Command("HOST|" + gametype + "|" +
                        size_x + "|" + size_y + "|" + "BOT", "|");
            }
            client.tryCustomCommand(gamestart);
            client.getGUI().closeGameSettings();
        }
        catch (IOException e){

            e.printStackTrace();
        }
    }
    public void getDenied(){

        client.getGUI().closeGameSettings();
    }
}
