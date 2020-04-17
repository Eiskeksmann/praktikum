package util;

import netGame.VierGewinnt;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameInfo {

    private ArrayList<String> memory;
    private int sizex;
    private int sizey;
    private String host, join, game;
    private boolean hostturn, p2turn;
    private VierGewinnt vg;

    public GameInfo(String host, String join, String game, int sizex, int sizey){

        this.memory = new ArrayList<>();
        this.hostturn = true;
        this.p2turn = false;
        this.game = game;
        this.host = host;
        this.join = join;
        this.sizex = sizex;
        this.sizey = sizey;
        vg = new VierGewinnt(host, join, sizex, sizey);
    }

    public boolean getHostTurn(){ return hostturn; }
    public boolean getJoinTurn() { return p2turn; }
    public String getHost(){ return host; }
    public String getJoin(){ return join; }
    public String getGame(){ return game; }
    public int getSizex() { return sizex; }
    public int getSizey() { return sizey; }

    public String getInfo(){
         return host + "-" + join + "-" + game;
    }

    public void addTurn(String turn){
        memory.add(turn);
    }

    public int getValidY(String sp, int x){

        return vg.getValidY(sp, x);
    }
    public boolean winProof(int id, int x, int y){
        return vg.winProof(id, x, y);
    }
    public boolean checkTurnValidity(String sp, int x){

        return vg.checkValidTurn(sp, x);
    }
    public boolean compareInfo(String info){

        Command tester = new Command(info, "-");
        if(host.equals(tester.get(0)) &&
                join.equals(tester.get(1))) {

            return true;
        } else return false;
    }
    public static ArrayList<String> getListedGameInfo(String info){

        ArrayList<String> temp = new ArrayList<>();
        Command puffer = new Command(info, "-");
        for(int i = 0; i < puffer.getLength(); i++){
            temp.add(puffer.get(i));
        }
        return temp;
    }

    public String toggleTurn(){

       hostturn = !hostturn;
       p2turn = !p2turn;
       if(hostturn){

           return host;
       } else if (p2turn){

           return join;
       }
       return "";
    }


}
