package GUI;

import netClient.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMiniConfirmation extends GUIPanel implements ActionListener {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private JButton jbt_yes, jbt_no;
    private Label lbl_msg;

    private Client client;
    private String target;

    public GUIMiniConfirmation(int width, int height) {
        super(width, height);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        target = "";
        initComponents();
        addComponents();
    }

    public void setTarget(String target){ this.target = target; }
    public void setClient(Client client){ this.client = client; }
    public void setMsg(String msg){
        this.lbl_msg.setText(msg);
    }

    @Override
    protected void initComponents() {

        this.setBackground(COL_DARKERGREY);
        this.setLayout(gbl);
        lbl_msg = new Label();
        setDefaultLabelStyle(lbl_msg);

        jbt_yes = new JButton("YES");
        setDefaultButtonStyle(jbt_yes);
        jbt_yes.addActionListener(this);

        jbt_no = new JButton("NO");
        setDefaultButtonStyle(jbt_no);
        jbt_no.addActionListener(this);
    }

    @Override
    protected void addComponents() {

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,2,2);
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        this.add(lbl_msg, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(jbt_yes, gbc);


        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(jbt_no, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == jbt_yes){

            client.getGUI().miniSaysYes(target);

        } else if (e.getSource() == jbt_no){

            client.getGUI().miniSaysNo(target);
        }
    }
}
