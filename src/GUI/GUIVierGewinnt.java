package GUI;

import netClient.Client;
import netGame.VierGewinnt;
import util.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIVierGewinnt extends GUIPanel implements ActionListener {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private VierGewinnt gameinfo;

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
    public void setGameInfo(VierGewinnt gameinfo){ this.gameinfo = gameinfo;}
    public void setClient(Client client){ this.client = client; }
    @Override

    protected void initComponents() {

        grid = new JButton[grid_x][grid_y];
        for(int y = 0; y < grid_y; y++){

            for(int x = 0; x < grid_x; x++){

                JButton filler = new JButton();
                setDefaultButtonStyle(filler);
                filler.setMinimumSize(new Dimension(10,10));
                filler.setMaximumSize(new Dimension(10,10));
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
        //System.out.println("GridWidths :" + grid_x);
        //System.out.println("GridHeights :" + grid_y);
        //System.out.println("GridWidth :" + gbc.weightx);
        //System.out.println("GridHeight :" + gbc.weighty);
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
        System.out.println("Button Size x: " + grid[0][0].getSize().width);
        System.out.println("Button Size y: " + grid[0][0].getSize().height);
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

                if(e.getSource() == grid[x][y]){

                    if(gameinfo.getHost()){

                        gameinfo.getSp1().insertTurnCoordinate(new Location(x, y));
                        //gameinfo.spielZug(gameinfo.getSp1());
                        System.out.println("Spieler 1 x: [" + x +  "] " + "y [ " + y + "]");
                        System.out.println("Button Size x: " + grid[x][y].getSize().width);
                        System.out.println("Button Size y: " + grid[x][y].getSize().height);

                    } else if (!gameinfo.getHost()){

                        gameinfo.getSp2().insertTurnCoordinate(new Location(x, y));
                        //gameinfo.spielZug(gameinfo.getSp2());
                        System.out.println("Spieler 2 x: [" + x +  "] " + "y [ " + y + "]");
                        System.out.println("Button Size x: " + grid[x][y].getSize().width);
                        System.out.println("Button Size y: " + grid[x][y].getSize().height);
                    }
                }
            }
        }
    }
}
