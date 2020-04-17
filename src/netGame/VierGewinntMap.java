package netGame;

import GUI.GUIVierGewinnt;
import java.util.ArrayList;

public class VierGewinntMap extends Spielfeld {

    //Member var space
    private int turn_count;
    private GUIVierGewinnt gui;
    private int[][] grid_;
    private ArrayList<String> turn_mem;
    private int winning_col_;

    //Constructor
    public VierGewinntMap(int _x, int _y, GUIVierGewinnt gui){

        this.x_ = _x;
        this.y_ = _y;
        this.turn_count = 0;
        this.grid_ = new int[_x][_y];
        this.turn_mem = new ArrayList<>();
        this.winning_col_ = 0;
        this.gui = gui;
    }
    public VierGewinntMap(int _x, int _y){

        this.x_ = _x;
        this.y_ = _y;
        this.turn_count = 0;
        this.grid_ = new int[_x][_y];
        this.turn_mem = new ArrayList<>();
        this.winning_col_ = 0;
    }

    //Getter - Setter
    public int getTurn_Count(){return turn_count;}
    public int[][] getGrid_(){return grid_;}
    public int getGridAt(int _x, int _y){return grid_[_x][_y];}
    public int getX_(){return x_;}
    public int getY_(){return y_;}
    public int getWinning_Col_(){return winning_col_;}

    //Interface Methods
    public void addTurn(Spieler _sp, int _x, int _y){

        if(turn_mem.get(turn_count).equals(INIT) || turn_mem.get(turn_count).equals(DELETE)) {
            turn_mem.set(turn_count,_sp.getId_() + "|" + Integer.toString(_x) + "|" + Integer.toString(_y));
            turn_count++;
        }
        else
        {
            //WallsOfText.exeption_ADD_TURN();
        }
        printTurn();

    }
    public void deleteTurn(){

        if(turn_count >= 1) {
            turn_mem.set(turn_count, DELETE);
            turn_count--;
        }
        else{

            //WallsOfText.exeption_DELETE_TURN();
        }
    }
    public void printTurn(){

        System.out.println("[" + turn_mem.size() + "] - " + turn_mem.get(turn_mem.size() - 1));
    }

    //Methods VierGewinnt
    public void setGrid(Spieler _sp, int _column){

        if(_column <= 0 || _column > y_){
            _sp.setTurn_(false);
            return;
        }
        if(grid_[0][_column] != EMPTY){
            _sp.setTurn_(false);
            return;
        }
        int set = 0;
        switch(_sp.getId_()){

            case("ID_SP1"):
                set = ID_SP1;
                break;

            case("ID_SP2"):
                set = ID_SP2;
                break;

            case("ID_BOT"):
                set = ID_BOT;
                break;
        }
        for(int i = x_; i >= 0; i--) {
            if(grid_[i][_column] == EMPTY){
                grid_[i][_column] = set;
                _sp.setLast_x_(i);
                _sp.setLast_y_(_column);
                gui.setTurn(i, _column, set);
                if(winProof(set, i, _column)){
                    System.out.println("DER GEWINNER IST: " + _sp.getName_());
                    return;
                }
                _sp.setTurn_(true);
                break;}
        }
    }
    public int setServerGrid(Spieler _sp, int _column){

        int set = 0;
        switch(_sp.getId_()){

            case("ID_SP1"):
                set = ID_SP1;
                break;

            case("ID_SP2"):
                set = ID_SP2;
                break;

            case("ID_BOT"):
                set = ID_BOT;
                break;
        }
        for(int i = y_ - 1; i >= 0; i--) {
            if(grid_[_column][i] == EMPTY){
                grid_[_column][i] = set;
                return i;
            }
        }
        return 0;
    }
    public boolean pretendSetGrid(Spieler _sp, int _column){

        if(grid_[_column][0] != EMPTY){
            _sp.setTurn_(false);
            return false;
        }
        int set = 0;
        switch(_sp.getId_()){

            case("ID_SP1"):
                set = ID_SP1;
                break;

            case("ID_SP2"):
                set = ID_SP2;
                break;

            case("ID_BOT"):
                set = ID_BOT;
                break;
        }
        for(int i = y_ - 1; i >= 0; i--) {
            if(grid_[_column][i] == EMPTY) return true;
        }
        return false;
    }
    public boolean winProof(int _set, int _x, int _y){

        //Horizontale Prüfung
        if(_y + 3 <= y_ - 1){
            //Checking right, right, right
            if(grid_[_x][_y+1] == _set && grid_[_x][_y+2] == _set && grid_[_x][_y+3] == _set){return true;}

        }
        if(_y + 2 <= y_ - 1 && _y - 1 >= 0){
            //Checking left, right, right
            if(grid_[_x][_y-1] == _set && grid_[_x][_y+1] == _set && grid_[_x][_y+2] == _set){return true;}
        }
        if(_y + 1 <= y_ - 1 && _y - 2 >= 0){
            //Checking left, left, right
            if(grid_[_x][_y-2] == _set && grid_[_x][_y-1] == _set && grid_[_x][_y+1] == _set){return true;}
        }
        if(_y - 3 >= 0){
            //Checking left, left, left
            if(grid_[_x][_y-3] == _set && grid_[_x][_y-2] == _set && grid_[_x][_y-1] == _set){return true;}
        }

        //Vertikale Prüfung
        if(_x + 3 <= x_ - 1){
            //Checking  down, down, down
            if(grid_[_x+1][_y] == _set && grid_[_x+2][_y] == _set && grid_[_x+3][_y] == _set){return true;}

        }
        if(_x + 2 <= x_ - 1 && _x - 1 >= 0){
            //Checking down, down, up
            if(grid_[_x-1][_y] == _set && grid_[_x+1][_y] == _set && grid_[_x+2][_y] == _set){return true;}
        }
        if(_x + 1 <= x_ - 1 && _x - 2 >= 0){
            //Checking down, up, up
            if(grid_[_x-2][_y] == _set && grid_[_x-1][_y] == _set && grid_[_x+1][_y] == _set){return true;}
        }
        if(_x - 3 >= 0){
            //Checking up, up, up
            if(grid_[_x-3][_y] == _set && grid_[_x-2][_y] == _set && grid_[_x-1][_y] == _set){return true;}
        }

        //Diagonale Prüfung von left up - right down
        if(_x - 3 >= 0 && _y - 3 >= 0){
            //Checking left up, left up, left up
            if(grid_[_x-1][_y-1] == _set && grid_[_x-2][_y-2] == _set && grid_[_x-3][_y-3] == _set){return true;}
        }
        if(_x - 2 >= 0 && _y - 2 >= 0 && _x + 1 <= x_ - 1 && _y + 1 <= y_ - 1){
            //Checking left up, left up, right down
            if(grid_[_x-1][_y-1] == _set && grid_[_x-2][_y-2] == _set && grid_[_x+1][_y+1] == _set){return true;}
        }
        if(_x - 1 >= 0 && _y - 1 >= 0 && _x + 2 <= x_ - 1 && _y + 2 <= y_ - 1){
            //Checking left up, right down, right down
            if(grid_[_x-1][_y-1] == _set && grid_[_x+1][_y+1] == _set && grid_[_x+2][_y+2] == _set){return true;}
        }
        if( _x + 3 <= x_ - 1 && _y + 3 <= y_ - 1){
            //Checking right down, right down, right down
            if(grid_[_x+1][_y+1] == _set && grid_[_x+2][_y+2] == _set && grid_[_x+3][_y+3] == _set){return true;}
        }

        //Diagonale Prüfung von left down - right up
        if(_x - 3 >= 0 && _y + 3 <= y_ - 1){
            //Checking right up, right up, right up
            if(grid_[_x-1][_y+1] == _set && grid_[_x-2][_y+2] == _set && grid_[_x-3][_y+3] == _set){return true;}
        }
        if(_x - 2 >= 0 && _y + 2 <= y_ - 1 && _x + 1 <= x_ - 1 && _y - 1 >= 0){
            //Checking right up, right up, left down
            if(grid_[_x-1][_y+1] == _set && grid_[_x-2][_y+2] == _set && grid_[_x+1][_y-1] == _set){return true;}
        }
        if(_x - 1 >= 0 && _y + 1 <= y_ - 1 && _x + 2 <= x_ - 1 && _y - 2 >= 0){
            //Checking right up, left down, left down
            if(grid_[_x-1][_y+1] == _set && grid_[_x+2][_y-2] == _set && grid_[_x+1][_y-1] == _set){return true;}
        }
        if(_x + 3 <= x_ - 1 && _y - 3 >= 0){
            //Checking right up, left down, left down
            if(grid_[_x+1][_y-1] == _set && grid_[_x+2][_y-2] == _set && grid_[_x+3][_y-3] == _set){return true;}
        }
        return false;

    }

    @Override
    public void initMap() {

        for(int i = 0; i < x_; i++){

            for(int j = 0; j < y_; j++){

                grid_[i][j] = EMPTY;
            }
        }
    }
}

