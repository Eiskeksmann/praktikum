package netGame;

import java.awt.*;

interface Protokollierbar {

    int EMPTY = 0;
    int ID_SP1 = 1;
    int ID_SP2 = 2;
    int ID_BOT = 3;
    int ANCHOR = 4;
    String DELETE = "DELETE";
    String INIT = "INIT";

    void addTurn(Spieler _sp, int _x, int _y);
    void deleteTurn();
    void printTurn();

}

public abstract class Spielfeld implements Protokollierbar {

    protected int x_;
    protected int y_;

    public abstract void initMap();

}

