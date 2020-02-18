package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public abstract class GUIPanel extends JPanel {

    protected static final Color COL_DARKGREY = new Color(75,76,76);
    protected static final Color COL_DARKERGREY = new Color(60,60,60);
    protected static final Color COL_DARKORANGE = new Color(191, 105, 0);
    protected static final Color COL_LIGHTGRAY = new Color(220,220,220);
    protected static final Color COL_VIOLET = new Color(147,112,219);

    protected static final Font FONT_STANDARD = new Font("Calibri", Font.BOLD, 12);

    private int width;
    private int height;
    private BorderLayout border;
    private JPanel north, east, west, south, center;

    public GUIPanel(int width, int height){

        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        this.requestFocus();
        this.border = new BorderLayout();
        this.setLayout(border);
    }

    public JPanel getNorth(){
        return north;
    }
    public JPanel getEast(){
        return east;
    }
    public JPanel getWest(){
        return west;
    }
    public JPanel getSouth(){
        return south;
    }
    public JPanel getCenter(){
        return center;
    }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }

    protected abstract void initComponents();
    protected abstract void addComponents();
    protected void addBorderPanels(JPanel jp){

        north = new JPanel();
        east = new JPanel();
        west = new JPanel();
        south = new JPanel();
        center = new JPanel();

        setDefaultBorderJPanelStyle(north);
        setDefaultBorderJPanelStyle(east);
        setDefaultBorderJPanelStyle(west);
        setDefaultBorderJPanelStyle(south);
        setDefaultBorderJPanelStyle(center);

        jp.add(north, BorderLayout.NORTH);
        jp.add(east, BorderLayout.EAST);
        jp.add(west, BorderLayout.WEST);
        jp.add(south, BorderLayout.SOUTH);
        jp.add(center, BorderLayout.CENTER);
    }
    protected void setDefaultTabbedPaneStyle(JTabbedPane jtp){

        jtp.setFont(FONT_STANDARD);
        jtp.setForeground(COL_VIOLET);
        jtp.setBackground(COL_DARKERGREY);
        jtp.setTabPlacement(JTabbedPane.TOP);
    }
    protected void setDefaultButtonStyle(JButton jb){

        jb.setBackground(COL_DARKGREY);
        jb.setForeground(COL_LIGHTGRAY);
        jb.setFont(FONT_STANDARD);
    }
    protected void setDefaultTextFieldStyle(JTextField jtf) {

        jtf.setFont(FONT_STANDARD);
        jtf.setForeground(COL_DARKORANGE);
        jtf.setBackground(COL_DARKGREY);
    }
    protected void setDefaultListStyle(List l){

        l.setFont(FONT_STANDARD);
        l.setBackground(COL_DARKGREY);
        l.setForeground(COL_DARKORANGE);
    }
    protected void setDefaultLabelStyle(Label l){

        l.setBackground(COL_DARKERGREY);
        l.setForeground(COL_VIOLET);
        l.setFont(FONT_STANDARD);
        l.setAlignment(Label.CENTER);
    }
    protected void setDefaultJPanelStyle(JPanel jp){

        jp.setBackground(COL_DARKGREY);
    }
    protected  void resetGridBagConstraints(GridBagConstraints gbc){

        gbc.weighty = 0.0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipady = 0;
        gbc.ipadx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
    }
    protected void setDefaultCheckBoxStyle(JCheckBox jcb){

        jcb.setForeground(COL_VIOLET);
        jcb.setBackground(COL_DARKORANGE);
    }
    protected void setDefaultComboBoxStyle(JComboBox jcb){

        jcb.setBackground(COL_DARKERGREY);
        jcb.setForeground(COL_LIGHTGRAY);
    }
    private void setDefaultBorderJPanelStyle(JPanel jp){

        jp.setBorder(new LineBorder(COL_DARKGREY,1, true));
        jp.setBackground(COL_DARKERGREY);
    }
}
