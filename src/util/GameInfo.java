package util;

public class GameInfo {

    private String host, join, game;
    private boolean hostturn, p2turn;

    public GameInfo(String host, String join, String game){

        hostturn = true;
        p2turn = false;
        this.game = game;
        this.host = host;
        this.join = join;
    }

    public boolean getHostTurn(){ return hostturn; }
    public boolean getP2Turn() { return p2turn; }

    public String getInfo(){
         return host + "-" + join + "-" + game;
    }

    public boolean compareInfo(String info){

        Command tester = new Command(info, "-");
        if(host.equals(tester.get(0)) &&
                join.equals(tester.get(1))) {

            return true;
        } else return false;
    }

    public void toggleTurn(){

       hostturn = !hostturn;
       p2turn = !p2turn;
    }


}
