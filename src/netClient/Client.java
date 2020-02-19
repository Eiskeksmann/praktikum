package netClient;

import GUI.GUIClientPanel;
import GUI.GUIFrame;
import GUI.GUILoginPanel;
import netGame.Futtern;
import netGame.VierGewinnt;
import util.Command;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client{

    private GUIFrame frame, log_frame;
    private GUILoginPanel log_gui;
    private GUIClientPanel gui;
    private String id;

    private InputThread input;
    private Output output;

    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket s;
    private InetAddress ip;

    private VierGewinnt v;
    private Futtern f;

    public Client(int port) throws IOException {

            this.ip = InetAddress.getByName("localhost");
            this.s = new Socket(ip, port);
            this.dis = new DataInputStream(s.getInputStream());
            this.dos = new DataOutputStream(s.getOutputStream());

            this.input = new InputThread(dis, this);
            input.start();
            this.output = new Output(dos, this);

            this.log_frame = new GUIFrame("LOGIN", new Dimension( 250, 120));
            log_gui = (GUILoginPanel) log_frame.getContent();
            log_gui.setClient(this);
    }

    public GUIClientPanel getGUI(){ return gui; }
    public String getId(){
        return id;
    }

    public void tryCustomCommand(Command cmd) throws IOException {

        output.setOutput(cmd);
    }
    public void successfullLogin(){

        this.log_frame.close();
        this.frame = new GUIFrame("CLIENT", new Dimension(400,300));
        this.frame.setTitle(id);
        this.gui = (GUIClientPanel) frame.getContent();
        this.gui.setClient(this);
    }
    public void fetchRoutine(Command cmd){

        switch (cmd.get(0)){

            case("SERVER"):
                break;
            case("LOGIN"):
                id = cmd.get(1);
                successfullLogin();
                break;
            case("ADDCLIENT"):
                gui.addClient(cmd.get(1));
                break;
            case("REMOVECLIENT"):
                gui.removeClient(cmd.get(1));
                break;
            case("MSGINFO"):
                gui.setStatus(cmd.get(1) + "is currently texting you ... ");
                break;
            case("PREPMSG"):
                gui.setStatus("write your message to :" + cmd.get(1));
                gui.setUpMessage(cmd.get(1));
                break;
            case("DISPLAYMSG"):
                gui.addMessage(cmd.get(1), cmd.get(2));
                break;
            case("GAMEREQUEST"):
                gui.showGameRequest(cmd.get(1), cmd.get(2));
                break;
            case("PREPGAME"):
                gui.setUpGameSettingsFrame(cmd.get(1));
                break;
            case("ACCEPT"):
                gui.getGamesettings().getAccepted();
                break;
            case("DENY"):
                gui.getGamesettings().getDenied();
                break;
            case("VIERGEWINNT"):
                v = new VierGewinnt(id, cmd.get(1), Integer.parseInt(cmd.get(2)),
                        Integer.parseInt(cmd.get(3)), this);

                break;
            case("FUTTERN"):
                break;
            case("CONNECT"):
                if(cmd.get(1).equals("Viergewinnt")){

                    v = new VierGewinnt(cmd.get(2), id,  Integer.parseInt(cmd.get(3)),
                            Integer.parseInt(cmd.get(4)), this);

                } else if (cmd.get(2).equals("Futtern")){

                }
                break;
            case("LOGOUT"):
                break;
            default:
                break;
        }
    }
    public boolean reconnect(int port) throws IOException {

        this.ip = InetAddress.getByName("localhost");
        this.s = new Socket(ip, port);
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());

        this.input = new InputThread(dis, this);
        this.output = new Output(dos, this);
        return true;
    }
}
