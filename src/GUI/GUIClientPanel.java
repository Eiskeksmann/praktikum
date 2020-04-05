package GUI;

import netClient.Client;
import util.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class GUIClientPanel extends GUIPanel implements MouseListener, KeyListener {

    private Client client;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private GUIFrame miniframe, gameselectionframe, gameframe, miniconfirmationframe;
    private GUIMiniSelection miniselection;
    private GUIChatPanel chat;
    private GUIGameSettings gamesettings;
    private GUIGamePanel game;
    private GUIMiniConfirmation miniconfirmation;
    private String opponent;

    private Label lbl_clients, lbl_north;
    private JTextField jtx_north;
    private JTabbedPane jtp_chat;
    private List lst_clients;

    public GUIClientPanel(int width, int height) {
        super(width, height);
        addBorderPanels(this);
        addMouseListener(this);
        addKeyListener(this);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        initComponents();
        addComponents();
    }

    public GUIGameSettings getGamesettings(){
        return gamesettings;
    }

    public void closeGameSettings(){

        if(gamesettings  != null){
            gameselectionframe.close();
            gameselectionframe = null;
        }
    }
    public void closeMiniFrame(){

        if(miniframe != null){
            miniframe.close();
            miniframe = null;
        }
    }
    public void closeMiniConfirmation(){
        if(miniconfirmationframe != null){
            miniconfirmationframe.close();
            miniconfirmationframe = null;
        }
    }
    public void setClient(Client client){ this.client = client; }
    public void setStatus(String status){

        jtx_north.setText(status);
    }
    public void addClient(String client){
        lst_clients.add(client);
    }
    public void removeClient(String client){
        lst_clients.remove(client);
    }
    public void setUpMessage(String target){

        if(chat == null) {

            chat = new GUIChatPanel(jtp_chat.getWidth() - 10, jtp_chat.getHeight() - 10);
            chat.addChat(target);
            jtp_chat.addTab(target, chat.getPanel(chat.getIndex(target)));
            chat.getTextField(chat.getIndex(target)).setText("Type your message here ...");
            chat.setClient(client);

        }
        else {

            setStatus("Please swap to the: " + target + " Tab ... ");
        }
    }
    public void addMessage(String target, String msg){

        if(chat == null) {

            chat = new GUIChatPanel(jtp_chat.getWidth(), jtp_chat.getHeight());
            chat.addChat(target);
            jtp_chat.addTab(target, chat.getPanel(chat.getIndex(target)));
            chat.getTextField(chat.getIndex(target)).setText("Type your answear here ...");
            chat.setClient(client);
            chat.getLog(chat.getIndex(target)).add(msg);

        } else if (!chat.exists(target)){

            chat.addChat(target);
            jtp_chat.addTab(target, chat.getPanel(chat.getIndex(target)));
            chat.setClient(client);
            chat.getLog(chat.getIndex(target)).add(msg);

        } else if (chat.exists(target)) {

            chat.getLog(chat.getIndex(target)).add(msg);
        }
    }
    public void showGameRequest(String versus, String gametype){

        miniconfirmationframe = new GUIFrame("MINICONFIRM", new Dimension(240,70));
        miniconfirmation = (GUIMiniConfirmation) miniconfirmationframe.getContent();
        miniconfirmation.setTarget(versus);
        miniconfirmation.setMsg("Want to play " + gametype + " against : " + versus);
        miniconfirmation.setGameMode(gametype);
        miniconfirmation.setClient(client);
    }
    public void setUpGameSettingsFrame(String versus){

        gameselectionframe = new GUIFrame("SETTINGS", new Dimension(280,120));
        gamesettings = (GUIGameSettings) gameselectionframe.getContent();
        gamesettings.setClient(client);
        gamesettings.setTarget(versus);
    }

    @Override
    protected void initComponents() {

        lbl_clients = new Label("Clients");
        setDefaultLabelStyle(lbl_clients);

        lst_clients = new List();
        setDefaultListStyle(lst_clients);
        lst_clients.addMouseListener(this);

        lbl_north = new Label("Status");
        setDefaultLabelStyle(lbl_north);

        jtx_north = new JTextField(30);
        setDefaultTextFieldStyle(jtx_north);
        jtx_north.setEditable(false);

        jtp_chat = new JTabbedPane();
        setDefaultTabbedPaneStyle(jtp_chat);
    }

    @Override
    protected void addComponents() {

        super.getWest().setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        super.getWest().add(lbl_clients, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.VERTICAL;
        super.getWest().add(lst_clients, gbc);
        resetGridBagConstraints(gbc);

        super.getNorth().setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        super.getNorth().add(lbl_north, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        super.getNorth().add(jtx_north, gbc);
        resetGridBagConstraints(gbc);

        super.getCenter().setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        super.getCenter().add(jtp_chat, gbc);
        resetGridBagConstraints(gbc);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void miniSaysYes(String target, String gametype){

        try{

            client.tryCustomCommand(new Command("ACCEPT|" + target + "|" + gametype, "|"));
            setStatus("ACCEPTED");
            closeMiniConfirmation();
        }
        catch(IOException e){

            e.printStackTrace();
        }
    }
    public void miniSaysNo(String target){

        try{

            client.tryCustomCommand(new Command("DENY|" + target, "|"));
            setStatus("DENIED");
            closeMiniConfirmation();
        }
        catch(IOException e){

            e.printStackTrace();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource() == lst_clients && lst_clients.getSelectedItem() != null){

            miniframe = new GUIFrame(lst_clients.getSelectedItem(), new Dimension(240,70));
            miniselection = (GUIMiniSelection) miniframe.getContent();
            miniselection.setTarget(lst_clients.getSelectedItem());
            miniselection.setClient(client);

        } else {

            closeMiniFrame();
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
