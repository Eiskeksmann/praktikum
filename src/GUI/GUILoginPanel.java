package GUI;

import netClient.Client;
import util.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUILoginPanel extends GUIPanel implements ActionListener {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JTextField id, pw;
    private JButton login;

    private Client client;

    public GUILoginPanel(int width, int height) {

        super(width, height);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        initComponents();
        addComponents();
    }

    public void setClient (Client client){ this.client = client; }

    @Override
    protected void initComponents() {

        this.setBackground(COL_DARKERGREY);

        id = new JTextField(20);
        setDefaultTextFieldStyle(id);

        pw = new JTextField(20);
        setDefaultTextFieldStyle(pw);

        login = new JButton("Login");
        login.addActionListener(this);
        setDefaultButtonStyle(login);
    }

    @Override
    protected void addComponents() {

        this.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 0.33;
        gbc.anchor = GridBagConstraints.PAGE_END;
        this.add(id, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(pw, gbc);

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(login, gbc);
    }

    private Command getLoginAttempt(){
        return new Command(id.getText() + "|" + pw.getText(), "|");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == login){

            if(id.getText().equals("") || pw.getText().equals("")){

                login.setBackground(Color.RED);
                return;
            }
            try {
                client.tryCustomCommand(getLoginAttempt());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
