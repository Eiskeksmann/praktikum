package netServer;

import GUI.GUIFrame;
import GUI.GUIPanel;
import GUI.GUIServerPanel;
import util.Login;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server {

    private GUIFrame frame;
    private GUIServerPanel gui;
    private Vector<ClientHandlerThread> active;
    private int count;
    private ArrayList<Login> ll;
    private ServerSocket ss;
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning;

    public Server(int port) throws IOException {

        active = new Vector<>();
        ll = new ArrayList();
        ll.add(new Login("admin","admin"));
        ll.add(new Login("test","test"));
        ss = new ServerSocket(port);
        isRunning = true;
        frame = new GUIFrame("SERVER",new Dimension(400, 300));
        gui = (GUIServerPanel) frame.getContent();
        fetchRoutine();
    }

    public GUIFrame getGUIFrame(){
        return frame;
    }
    public GUIPanel getGUI() { return gui; }
    public Vector<ClientHandlerThread> getActive(){ return active; }
    public ArrayList<Login> getLoginList(){ return ll; }

    private void fetchRoutine() throws IOException {

        while(isRunning){

            s = ss.accept();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            gui.addLog("New Client : [" + count + "] has connected ... ");
            ClientHandlerThread ch = new ClientHandlerThread(count, dis, dos, this);
            Thread t = new Thread(ch);
            t.start();
            count++;
        }
    }


}
