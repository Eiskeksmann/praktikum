package GUI;

import netClient.Client;
import netGame.VierGewinnt;
import util.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUIVierGewinnt extends GUIPanel implements ActionListener {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private VierGewinnt gamestate;

    private Client client;
    private JButton[][] grid;

    private int grid_x;
    private int grid_y;

    public GUIVierGewinnt(int width, int height) {

        super(width, height);
        addBorderPanels(this);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
    }

    public void setGrid_x(int grid_x){ this.grid_x = grid_x; }
    public void setGrid_y(int grid_y){ this.grid_y = grid_y; }
    public void init(){

        initComponents();
        addComponents();
    }
    public void setGameState(VierGewinnt gamestate){ this.gamestate = gamestate;}
    public void setClient(Client client){ this.client = client; }
    @Override

    protected void initComponents() {

        grid = new JButton[grid_x][grid_y];
        for(int y = 0; y < grid_y; y++){

            for(int x = 0; x < grid_x; x++){

                JButton filler = new JButton();
                setDefaultButtonStyle(filler);
                filler.setMinimumSize(new Dimension(40,40));
                filler.setMaximumSize(new Dimension(40,40));
                filler.addActionListener(this);
                grid[x][y] = filler;
            }
        }
    }

    @Override
    protected void addComponents() {

        super.getCenter().setLayout(gbl);
        gbc.weightx = (1.0 / grid_x);
        gbc.weighty = (1.0 / grid_y);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 30;
        gbc.ipadx = 30;
        gbc.insets = new Insets(2,2,2,2);

        for(int y = 0; y < grid_y; y++){

            for(int x = 0; x < grid_x; x++){

                gbc.gridx = x;
                gbc.gridy = y;
                super.getCenter().add(grid[x][y], gbc);
            }
        }
        resetGridBagConstraints(gbc);
    }

    public void setTurn(int x, int y, int set){

        if(set == 1){

            grid[x][y].setBackground(COL_DARKORANGE);

        } else if (set == 2 || set == 3){

            grid[x][y].setBackground(COL_VIOLET);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int x = 0; x < grid_x; x++){

            for(int y = 0; y < grid_y; y++){

                if(e.getSource() == grid[x][y]) {


                    try {
                        client.tryCustomCommand(new Command("TURN|" + gamestate.getSp1().getName_() + "|" +
                                gamestate.getSp2().getName_() + "|" + x + "|" + y, "|"));
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            }
        }
    }
}
