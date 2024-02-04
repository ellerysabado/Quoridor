package com.cs309.quoridorApp.repository;

import com.cs309.quoridorApp.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequestMapping("/api")
public interface PlayerRepository extends JpaRepository<Player, String>{

    public default List<Player> findAll(List<String> usernames)
    {
        List<Player> toReturn = new ArrayList<>();
        for(String un : usernames)
            toReturn.add(getOne(un));
        return toReturn;
    }

    //TODO: add to this as we make player connect to more things
    public default void superDelete(Player player)
    {
        for(Player friend: player.getFriends())
        {
            friend.getFriends().removeIf(p ->
                    {return p.getUsername().equals(player.getUsername());}
            );
            save(friend);
        }

        for(Player request: player.getFriendRequests())
        {
            request.getRequestedFriends().removeIf(p ->
                    {return p.getUsername().equals(player.getUsername());}
            );
            save(request);
        }

        for(Player requestedFriend: player.getRequestedFriends())
        {
            requestedFriend.getFriendRequests().removeIf(p ->
                    {return p.getUsername().equals(player.getUsername());}
            );
            save(requestedFriend);
        }

        for(Player blocked: player.getBlockedPlayers())
        {
            blocked.getBlockedBy().removeIf(p ->
                    {return p.getUsername().equals(player.getUsername());}
            );
            save(blocked);
        }

        for(Player blockedBy: player.getBlockedBy())
        {
            blockedBy.getBlockedPlayers().removeIf(p ->
                    {return p.getUsername().equals(player.getUsername());}
            );
            save(blockedBy);
        }

        for(Player blockedBy: player.getBlockedBy())
        {
            blockedBy.getBlockedPlayers().removeIf(p ->
                    {return p.getUsername().equals(player.getUsername());}
            );
            save(blockedBy);
        }

        delete(player);
    }
}
