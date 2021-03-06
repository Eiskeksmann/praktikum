package GUI;

import netClient.Client;
import util.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class GUIMiniSelection extends GUIPanel implements MouseListener {

    private Client client;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private String target;
    private JPanel jpan;
    private Label lbl_1, lbl_2, lbl_3, lbl_4;

    public GUIMiniSelection(int width, int height) {
        super(width, height);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        initComponents();
        addComponents();
    }

    public void setClient(Client client){ this.client = client; }
    public void setTarget(String target){ this.target = target; }
    public String getTarget(){ return target; }

    @Override
    protected void initComponents() {

        jpan = new JPanel();
        setDefaultJPanelStyle(jpan);

        lbl_1 = new Label("select ...");
        setDefaultLabelStyle(lbl_1);
        lbl_1.setForeground(COL_DARKORANGE);
        lbl_1.addMouseListener(this);

        lbl_2 = new Label("... send Message");
        lbl_2.setForeground(COL_LIGHTGRAY);
        lbl_2.addMouseListener(this);

        lbl_3 = new Label("... send Game Request");
        lbl_3.setForeground(COL_LIGHTGRAY);
        lbl_3.addMouseListener(this);

        lbl_4 = new Label("... send BroadcastMessage");
        lbl_4.setForeground(COL_LIGHTGRAY);
        lbl_4.addMouseListener(this);
    }

    @Override
    protected void addComponents() {

        jpan.setLayout(gbl);
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        jpan.add(lbl_1, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        jpan.add(lbl_2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        jpan.add(lbl_3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        jpan.add(lbl_4, gbc);

        resetGridBagConstraints(gbc);

        this.setLayout(new BorderLayout());
        this.add(jpan, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        try {

            if (e.getSource() == lbl_2) {

                //TODO --> initiate Message towards "target"
                if(client.getId().equals(target)){

                    client.getGUI().setStatus("Thats you ... xD");
                    client.getGUI().closeMiniFrame();

                }
                else {

                    client.tryCustomCommand(new Command("PREPMSG|" + target, "|"));
                    client.getGUI().closeMiniFrame();
                }


            } else if (e.getSource() == lbl_3) {

                //TODO --> initiate Game Request towards "target"
                if(client.getId().equals(target)){

                    client.getGUI().setStatus("Thats you ... xD");
                    client.getGUI().closeMiniFrame();
                }
                else {

                    client.tryCustomCommand(new Command("PREPGAME|" + target, "|"));
                    client.getGUI().closeMiniFrame();
                }
            }
            else if (e.getSource() == lbl_4){

                //TODO --> initiate Message towards "target"
                if(client.getId().equals(target)){

                    client.getGUI().setStatus("Thats you ... xD");
                    client.getGUI().closeMiniFrame();

                }
                else {

                    client.tryCustomCommand(new Command("PREPMSG|" + "ALL", "|"));
                    client.getGUI().closeMiniFrame();
                }
            }
        }
        catch (IOException ex){

            ex.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if(e.getSource() == lbl_2){

            lbl_2.setBackground(COL_DARKORANGE);
        }
        else if (e.getSource() == lbl_3){

            lbl_3.setBackground(COL_DARKORANGE);
        } else if (e.getSource() == lbl_4){

            lbl_4.setBackground(COL_DARKORANGE);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if(e.getSource() == lbl_2){

            lbl_2.setBackground(COL_DARKGREY);
        }
        else if (e.getSource() == lbl_3){

            lbl_3.setBackground(COL_DARKGREY);
        }
        else if (e.getSource() == lbl_4){

            lbl_4.setBackground(COL_DARKGREY);
        }

    }
}
