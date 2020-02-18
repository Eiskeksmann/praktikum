package GUI;

import javax.swing.*;
import java.awt.*;

public class GUIServerPanel extends GUIPanel {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private List log;
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

        log = new List(18);
        setDefaultListStyle(log);

        jtf = new JTextField();
        setDefaultTextFieldStyle(jtf);

        jbt = new JButton("CONFIRM");
        setDefaultButtonStyle(jbt);
    }

    @Override
    protected void addComponents() {

        super.getCenter().setLayout(gbl);

        gbc.insets = new Insets(2,2,2,2);
        gbc.gridx  = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        super.getCenter().add(log, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.1;
        gbc.ipady = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        super.getCenter().add(jtf, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.1;
        gbc.ipady = 0;
        super.getCenter().add(jbt, gbc);

        resetGridBagConstraints(gbc);
    }

    //GUI Methods
    public void addLog(String str){
        log.add(str);
    }
}
