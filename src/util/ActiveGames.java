package util;

import java.util.ArrayList;

public class ActiveGames <S extends String, L extends Login>{

    private ArrayList<S> games;
    private ArrayList<L> players;

    public ActiveGames(){

        games = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void addActiveGame(S game, L login){

        games.add(game);
        players.add(login);
    }

    public boolean isPlaying(L l){

        if(haveActiveGame(l).size() > 0){

            return true;
        }
        return false;
    }

    public ArrayList<S> haveActiveGame(L login){

        ArrayList<S> ret = new ArrayList<>();
        for(Login l : players){

            if(l.getID().equals(login.getID()) && l.getPW().equals(login.getPW())){

                ret.add(games.get(players.indexOf(l)));

            } else if (l.getPW().equals(login.getID()) && l.getID().equals(login.getPW())){

                ret.add(games.get(players.indexOf(l)));
            }
        }
        return ret;
    }


}
