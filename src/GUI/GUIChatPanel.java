package GUI;

import netClient.Client;
import util.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUIChatPanel extends GUIPanel implements ActionListener, KeyListener, MouseListener {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private ArrayList<JPanel> panels;
    private ArrayList<JTextField> inputs;
    private ArrayList<String> tabs;
    private ArrayList<List> logs;
    private ArrayList<JButton> btns;

    private Client client;

    public GUIChatPanel(int width, int height) {
        super(width, height);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        inputs = new ArrayList<>();
        panels = new ArrayList<>();
        tabs = new ArrayList<>();
        logs = new ArrayList<>();
        btns = new ArrayList<>();
    }

    public void setClient(Client client){  this.client = client; }
    public void addChat(String target){

        JPanel panel = new JPanel();
        setDefaultJPanelStyle(panel);
        panel.setLayout(gbl);

        List log = new List(10);
        setDefaultListStyle(log);
        log.setForeground(COL_LIGHTGRAY);

        JTextField input = new JTextField(20);
        setDefaultTextFieldStyle(input);
        input.addKeyListener(this);

        JButton jbt = new JButton("send");
        setDefaultButtonStyle(jbt);
        jbt.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.insets = new Insets(2,2,2,2);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(log, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        gbc.ipady = 6;
        panel.add(input, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.1;
        gbc.ipady = 0;
        panel.add(jbt, gbc);

        tabs.add(target);
        panels.add(panel);
        logs.add(log);
        btns.add(jbt);
        inputs.add(input);
        resetGridBagConstraints(gbc);
    }

    public boolean exists(String target){
        if(tabs.contains(target)) return true;
        return false;
    }
    public int getIndex(String target){
        return tabs.indexOf(target);
    }
    public List getLog(int i){
        return logs.get(i);
    }
    public JPanel getPanel(int i){ return panels.get(i); }
    public JTextField getTextField(int i){ return inputs.get(i); }
    @Override
    protected void initComponents() {

    }

    @Override
    protected void addComponents() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            for (JButton btn : btns) {

                if (e.getSource() == btn) {

                    client.tryCustomCommand(new Command("MSG|"+ tabs.get(btns.indexOf(btn)) +
                            "|" + inputs.get(btns.indexOf(btn)).getText(), "|"));
                    inputs.get(btns.indexOf(btn)).setText("");
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        try {
            for (JTextField jtf : inputs) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    client.tryCustomCommand(new Command("MSG|" + tabs.get(inputs.indexOf(jtf)) +
                            "|" + inputs.get(inputs.indexOf(jtf)).getText(), "|"));
                    inputs.get(inputs.indexOf(jtf)).setText("");
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
