package netServer;

import GUI.GUIFrame;
import GUI.GUIServerPanel;
import util.Command;
import util.GameInfo;
import util.Login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientHandlerThread implements Runnable{

    private String id;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Server server;
    private GUIFrame frame;
    private GUIServerPanel gui;
    private boolean isLoggedin;

    public ClientHandlerThread(int i, DataInputStream dis,
                               DataOutputStream dos, Server server){

        this.dis = dis;
        this.dos = dos;
        this.id = Integer.toString(i);
        this.isLoggedin = false;
        this.server = server;
        this.frame = server.getGUIFrame();
        this.gui = (GUIServerPanel) server.getGUI();
    }

    @Override
    public void run() {

        while (true) {

            try {

                while (!isLoggedin) {

                    boolean isValidAttempt = true;
                    String in = dis.readUTF();
                    Command cmd = new Command(in, "|");
                    //cmd.get(0) --> username
                    //cmd.get(1) --> password
                    Login attempt = new Login(cmd.get(0), cmd.get(1));
                    gui.addLog("USER: [" + attempt.getID() + "] tries to login with: " +
                            attempt.getID() + "|" + attempt.getPW());
                    for (ClientHandlerThread cht : server.getActive()) {

                        if (attempt.getID().equals(cht.id)) {

                            dos.writeUTF("LOGINFAIL|User already is logged in ...");
                            gui.addLog("USER: [" + attempt.getID() +"] login failed -> " +
                                    "User is already logged in from another location");
                            isValidAttempt = false;
                        }
                    }
                    if (isValidAttempt) {

                        for (Login l : server.getLoginList()) {

                            if (l.compareID(attempt)) {

                                if (l.comparePW(attempt)) {

                                    //Success
                                    isValidAttempt = false;
                                    this.id = attempt.getID();
                                    server.getActive().add(this);
                                    isLoggedin = true;
                                    dos.writeUTF("LOGIN|" + attempt.getID());
                                    gui.addLog("USER: [" + attempt.getID() + "] has now logged in ");
                                } else {

                                    //Wrong Password ...
                                    dos.writeUTF("LOGINFAIL|Wrong Password ...");
                                    gui.addLog("USER: [" + attempt.getID() + "] tried to log in " +
                                            "with wrong password ");
                                    isValidAttempt = false;
                                }
                                break;
                            }
                        }
                        if (isValidAttempt) {

                            //create new Account ...
                            server.getLoginList().add(attempt);
                            server.getActive().add(this);
                            isLoggedin = true;
                            this.id = attempt.getID();
                            dos.writeUTF("LOGIN|" + attempt.getID());
                            gui.addLog("USER: [" + attempt.getID() + "] has created new Account and logged in ");
                        }
                    }

                }
                for (ClientHandlerThread cht : server.getActive()) {

                        if (cht.id.equals(id)) {
                            dos.writeUTF("ADDCLIENT|" + cht.id);
                            gui.addClient(id);
                        } else if (!cht.id.equals(id)) {
                            cht.dos.writeUTF("ADDCLIENT|" + id);
                            dos.writeUTF("ADDCLIENT|" + cht.id);
                        }
                }
                while (isLoggedin) {

                    String in = "";
                    try {
                        in = dis.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Command cmd = new Command(in, "|");

                    switch (cmd.get(0)) {

                        case ("PREPMSG"):
                            //cmd.get(1) --> target

                            dos.writeUTF("PREPMSG|" + cmd.get(1));
                            for (ClientHandlerThread cht : server.getActive()) {

                                if (cht.id.equals(cmd.get(1))) {
                                    cht.dos.writeUTF("MSGINFO|" + id);
                                }
                            }
                            break;
                        case ("PREPGAME"):
                            dos.writeUTF("PREPGAME|" + cmd.get(1));
                            break;
                        case ("SENDREQUEST"):
                            for (ClientHandlerThread cht : server.getActive()) {

                                if (cht.id.equals(cmd.get(1)))
                                    cht.dos.writeUTF("GAMEREQUEST|" + id + "|" + cmd.get(2));
                            }
                            break;
                        case ("ACCEPT"):
                            for (ClientHandlerThread cht : server.getActive()) {

                                if (cht.id.equals(cmd.get(1))) {

                                    for (GameInfo gi : server.getGames()) {
                                        if (gi.compareInfo(cht.id + "-" + id)) {

                                            cht.dos.writeUTF("DENY|" + id + "you are already playing");
                                            gui.addLog("USER: [" + id + "] is already ingame -> GameRequestDenied");
                                        }
                                    }

                                    cht.dos.writeUTF("ACCEPT|" + id + " has accepted");
                                    gui.addLog("USER: [" + id + "] has accepted to play" +
                                            " vs. USER: [" + cht.id + "]");
                                    break;
                                }
                            }
                            break;
                        case("DENY"):
                            for(ClientHandlerThread cht : server.getActive()){

                                if(cht.id.equals(cmd.get(1))){

                                    cht.dos.writeUTF("DENY|" + id + " has denied");
                                    gui.addLog("USER: [" + id + "] has denied the game request");
                                }
                            }
                            break;
                        case("HOST"):
                            //1 --> gametype
                            //2 --> size_x
                            //3 --> size_y
                            //4 --> target
                            GameInfo gameinfo = new GameInfo(id, cmd.get(4), cmd.get(1),
                                    Integer.parseInt(cmd.get(2)), Integer.parseInt(cmd.get(3)));
                            server.getGames().add(gameinfo);
                            gui.addGame(gameinfo.getInfo());

                            if(cmd.get(1).equals("Viergewinnt")){

                                dos.writeUTF("VIERGEWINNT|" + cmd.get(4) + "|" + cmd.get(2) + "|" + cmd.get(3));

                            } else if (cmd.get(1).equals("Futtern")){

                                dos.writeUTF("FUTTERN|" + cmd.get(4) + "|" + cmd.get(2) + "|" + cmd.get(3));
                            }
                            for(ClientHandlerThread cht : server.getActive()){

                                if(cht.id.equals(cmd.get(4)))
                                    cht.dos.writeUTF("CONNECT|" + cmd.get(1) + "|" + id + "|"
                                            + cmd.get(2) + "|" + cmd.get(3));
                                gui.addLog("USER: [" +  id + "] created a Game for - USER: [" + cmd.get(4) + "]" +
                                "is joining ...");

                            }
                        case("TURN"):
                            //cmd
                            //1 --> hostname
                            //2 --> joinname
                            //3 --> x
                            //4 --> y
                            for (GameInfo gi : server.getGames()){

                                if(gi.getHost().equals(cmd.get(1)) && gi.getJoin().equals(cmd.get(2))){

                                    if(cmd.get(1).equals(id)){
                                        //Check if turn is valid:
                                        if(gi.getHostTurn()){

                                            if(gi.checkTurnValidity("HOST", Integer.parseInt(cmd.get(3)))){

                                                //host made a valid turn
                                                int validy = gi.getValidY("HOST", Integer.parseInt(cmd.get(3)));
                                                dos.writeUTF("DISPLAYMYTURN|" + cmd.get(3) + "|" + validy);
                                                gui.addLog("USER: [" + id +"] set his stone at: [" + cmd.get(3) + "] " +
                                                        "[" + validy + "]");

                                                for(ClientHandlerThread cht : server.getActive()){
                                                    if(cht.id.equals(cmd.get(2))){
                                                        cht.dos.writeUTF("DISPLAYENEMYTURN|" + cmd.get(3) + "|" + validy);
                                                        if(gi.winProof(1, Integer.parseInt(cmd.get(3)), validy)){
                                                            dos.writeUTF("WIN| quit" );
                                                            cht.dos.writeUTF("LOOSE| quit");
                                                            gui.removeGame(gi.getInfo());
                                                            gui.addLog("USER: [ " + id + "] " +
                                                                    "won vs USER: [" + cht.id + "]");
                                                            server.getGames().remove(gi);
                                                        }
                                                        break;
                                                    }
                                                }
                                                gi.toggleTurn();
                                                break;
                                            } else {
                                                //host made a invalid turn
                                                dos.writeUTF("STATUS|you made an invalid turn ... try again");
                                                break;
                                            }
                                        }

                                    } else if(cmd.get(2).equals(id)){
                                        //Check if turn is valid:
                                        if(gi.getJoinTurn()){

                                            if(gi.checkTurnValidity("JOIN", Integer.parseInt(cmd.get(3)))){

                                                int validy = gi.getValidY("JOIN", Integer.parseInt(cmd.get(3)));
                                                //join made a valid turn
                                                dos.writeUTF("DISPLAYMYTURN|" + cmd.get(3) + "|" + validy);
                                                gui.addLog("USER: [" + id +"] set his stone at: [" + cmd.get(3) + "] " +
                                                        "[" + validy + "]");
                                                for(ClientHandlerThread cht : server.getActive()){
                                                    if(cht.id.equals(cmd.get(1))){
                                                        cht.dos.writeUTF("DISPLAYENEMYTURN|" + cmd.get(3) + "|" + validy);
                                                        if(gi.winProof(2, Integer.parseInt(cmd.get(3)), validy)){
                                                            dos.writeUTF("WIN| quit" );
                                                            cht.dos.writeUTF("LOOSE| quit");
                                                            gui.addLog("USER: [ " + id + "] " +
                                                                    "won vs USER: [" + cht.id + "]");
                                                            gui.removeGame(gi.getInfo());
                                                            server.getGames().remove(gi);
                                                        }
                                                        break;
                                                    }
                                                }
                                                gi.toggleTurn();
                                                break;
                                            } else {
                                                //join made a invalid turn
                                                dos.writeUTF("STATUS|you made an invalid turn ... try again");
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            break;
                        case("MSG"):
                            for(ClientHandlerThread cht : server.getActive()){

                                if(cht.id.equals(cmd.get(1)) || cmd.get(1).equals("ALL")){
                                    cht.dos.writeUTF("DISPLAYMSG|" + id + "|" + cmd.get(2));
                                    dos.writeUTF("DISPLAYMSG|" + cmd.get(1) + "|" + cmd.get(2));
                                }
                            }
                            break;
                    }

                }
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }
}
