package com.cs309.quoridorApp.player;

import java.util.*;
import java.util.function.Predicate;

/**
 * This class creates a random session ID for the player
 */
public class PlayerSessions
{
    public static HashMap<UUID, Player> SESSIONS = new HashMap<>();

    public static UUID newSession(Player player)
    {
        UUID id = UUID.randomUUID();

        SESSIONS.put(id, player);

        return id;
    }

    public static boolean inSession(UUID id)
    {
        return getPlayer(id) != null;
    }

    public static Collection<Player> getActivePlayers()
    {
        return SESSIONS.values();
    }

    public static Player getPlayer(UUID id)
    {
        return SESSIONS.get(id);
    }

    public static List<Player> sortSessions(Predicate<Player> pred)
    {
        List<Player> toReturn = new ArrayList<>();

        for(Player player : SESSIONS.values())
            if(pred.test(player))
                toReturn.add(player);
        return toReturn;
    }
}
