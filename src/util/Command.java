package util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Command {

    private ArrayList<String> param;
    private String cmd;
    private int length;

    public Command(String cmd, String seperator){

        this.length = 1;
        this.cmd = cmd;
        this.param = new ArrayList<>();
        char[] chain = cmd.toCharArray();
        for(char c : chain){
            if(c == seperator.charAt(0)){
                length++;
            }
        }
        StringTokenizer tok = new StringTokenizer(cmd, seperator);
        for(int i = 0; i < length; i++){

            param.add(tok.nextToken());
        }
    }

    public int getLength(){ return length; }
    public String get(int i){
        return param.get(i);
    }
    public ArrayList<String> getP(){
        return param;
    }
    public String getCmd() { return cmd; }

    @Override
    public String toString(){
        return cmd;
    }
}
