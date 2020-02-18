package netClient;

import util.Command;

import java.io.DataOutputStream;
import java.io.IOException;

public class Output {

    private Client client;
    private DataOutputStream dos;

    public Output(DataOutputStream dos, Client client){

        this.client = client;
        this.dos = dos;
    }

    public void setOutput(Command out) throws IOException {

        dos.writeUTF(out.getCmd());
    }
}
