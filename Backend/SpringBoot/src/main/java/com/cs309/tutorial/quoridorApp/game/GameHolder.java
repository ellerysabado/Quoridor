package com.cs309.tutorial.quoridorApp.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class GameHolder
{

    private static HashMap<Integer, Game> GAMES = new HashMap<>();

    public static Game newGame()
    {
        Game g = new Game();

        GAMES.put(g.id, g);

        return g;
    }

    public static boolean inSession(int id)
    {
        return getGame(id) != null;
    }

    public static Collection<Game> getGames()
    {
        return GAMES.values();
    }

    public static Game getGame(int id)
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
