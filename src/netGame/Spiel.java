package netGame;

import java.awt.*;

public abstract class Spiel {

    Spieler sp1_, sp2_;
    Spielfeld sf_;

    public abstract void spielZug(Spieler _sp);
    public abstract void durchGang();
}
