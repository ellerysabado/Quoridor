package com.cs309.quoridorApp.game;

import java.util.*;
import java.util.function.Predicate;

public class GameHolder
{

    private static HashMap<String, Game> GAMES = new HashMap<>();

    public static Game newGame()
    {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String newId = "AAAAAA";
        while(newId.equals("AAAAAA"))
        {
            newId = "";
            for(int i = 0; i < 6; i++)
            {
                int temp = (int) (Math.random() * abc.length());
                newId += abc.substring(temp, temp+1);
            }
            if(GAMES.containsKey(newId))
                newId = "AAAAAA";
        }

        Game g = new Game(newId);

        GAMES.put(g.id, g);

        return g;
    }

    public static void removeGame(Game game)
    {
        GAMES.remove(game.id, game);
    }

    public static boolean inSession(String id)
    {
        return getGame(id) != null;
    }

    public static Collection<Game> getGames()
    {
        return GAMES.values();
    }

    public static Game getGame(String id)
    {
        return GAMES.get(id);
    }

    public static List<Game> sortGames(Predicate<Game> pred)
    {
        List<Game> toReturn = new ArrayList<>();

        for(Game game : GAMES.values())
            if(pred.test(game))
                toReturn.add(game);
        return toReturn;
    }
}
