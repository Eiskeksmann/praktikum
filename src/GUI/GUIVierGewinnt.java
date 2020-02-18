package GUI;

import netClient.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIVierGewinnt extends GUIPanel implements ActionListener {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private Client client;
    private JButton[][] grid;

    private int grid_x;
    private int grid_y;

    public GUIVierGewinnt(int width, int height) {
        super(width, height);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        grid_x = width / 30;
        grid_y = width / 30;
        grid = new JButton[grid_x][grid_y];
        initComponents();
        addComponents();
    }

    public void setClient(Client client){ this.client = client; }
    @Override

    protected void initComponents() {

        for(int x = 0; x < grid_x; x++){

            for(int y = 0; y < grid_y; y++){

                JButton filler = new JButton();
                setDefaultButtonStyle(filler);
                filler.addActionListener(this);
                grid[x][y] = filler;
            }
        }
        this.setLayout(gbl);
    }

    @Override
    protected void addComponents() {

        gbc.weightx = this.getWidth() / grid_x;
        gbc.weighty = this.getWidth() / grid_y;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 30;
        gbc.ipadx = 30;
        gbc.insets = new Insets(2,2,2,2);

        for(int x = 0; x < grid_x; x++){

            for(int y = 0; y < grid_y; y++){

                gbc.gridx = x;
                gbc.gridy = y;
                this.add(grid[x][y], gbc);
            }
        }
    }

    public void setTurn(int x, int y, String playerid){

        if(playerid.equals("ID_SP1")){

            grid[x][y].setBackground(COL_DARKORANGE);

        } else if (playerid.equals("ID_SP2")){

            grid[x][y].setBackground(COL_VIOLET);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int x = 0; x < grid_x; x++){

            for(int y = 0; y < grid_y; y++){

                if(e.getSource() == grid[x][y]){

                    //TODO X - Y übergeben
                }
            }
        }
    }
}
