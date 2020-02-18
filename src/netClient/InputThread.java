package netClient;

import util.Command;

import java.io.DataInputStream;
import java.io.IOException;

public class InputThread extends Thread implements Runnable{

    private Client client;
    private DataInputStream dis;
    private boolean isActive;
    private Command input;

    public InputThread(DataInputStream dis, Client client){

        this.client = client;
        this.dis = dis;
        this.isActive = true;
    }

    @Override
    public void run() {

        while(isActive){

            try {

                input = new Command(dis.readUTF(), "|");
                client.fetchRoutine(input);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
